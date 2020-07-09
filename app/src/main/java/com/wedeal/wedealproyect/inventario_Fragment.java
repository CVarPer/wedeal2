package com.wedeal.wedealproyect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class inventario_Fragment extends Fragment{
    Context mContext;

    FloatingActionButton fab;

    private GridView gridView;
    CustomAdapter_GridView_Productos customAdapterGridViewProductos;
    private List<modelo_producto> info_productos = new ArrayList<>();

    ImageView imageView;

    String nombre;
    int foto_producto;
    modelo_producto modelo;


    private static int RESULT_LOAD_IMAGE = 10;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_inventario, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = requireView().findViewById(R.id.grid_view_image_text);



        SharedPreferences pref = mContext.getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        assert Negocio != null;
        databaseReference.child(Negocio).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Productos de " + Negocio)) {
                    databaseReference.child(Negocio).child("Productos de " + Negocio).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {


                                String codigo = objSnapshot.child("Código").getValue(String.class);
                                String precio = objSnapshot.child("Precio").getValue(String.class);
                                String stock = objSnapshot.child("Stock").getValue(String.class);
                                String nombree = objSnapshot.child("Nombre").getValue(String.class);
                                String fechaprod = objSnapshot.child("Fecha").getValue(String.class);

                                modelo = new modelo_producto();
                                modelo.setCodigo(codigo);
                                modelo.setNombre(nombree);
                                modelo.setPrecio(precio);
                                modelo.setStock(stock);
                                modelo.setFecha(fechaprod);

                                if(objSnapshot.child("Imagen").exists()){
                                    String urlImagen = objSnapshot.child("Imagen").getValue(String.class);
                                    Uri imagen = Uri.parse(urlImagen);
                                    modelo.setFotoProd(imagen);
                                }

                                info_productos.add(modelo);

                                customAdapterGridViewProductos = new CustomAdapter_GridView_Productos(requireActivity(), foto_producto, info_productos);
                                gridView.setAdapter(customAdapterGridViewProductos);

                                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int i, long id) {
                                        nombre = info_productos.get(i).getNombre();
                                        gridView.getSelectedItemPosition();
                                        imageView = requireView().findViewById(R.id.foto_producto);
                                        final CharSequence[] opciones = {"Elegir de galería", "Cancelar"};
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Elige una opción");
                                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                                            @SuppressLint("IntentReset")
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (opciones[i] == "Elegir de galería") {
                                                    if(EasyPermissions.hasPermissions(mContext, galleryPermissions)){

                                                        Intent intent = new Intent(Intent.ACTION_PICK,
                                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                        intent.setType("image/*");
                                                        intent.putExtra("Nombre",nombre);
                                                        startActivityForResult(Intent.createChooser(intent, "Selecciona app de imágenes"), RESULT_LOAD_IMAGE);

                                                        ;
                                                    }
                                                    else{
                                                        EasyPermissions.requestPermissions((Activity) mContext, "Access for storage",
                                                                101, galleryPermissions);
                                                    }
                                                } else if (opciones[i] == "Cancelar") {
                                                    dialogInterface.dismiss();
                                                }
                                            }
                                        });
                                        builder.show();


                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }




        });
        fab = requireView().findViewById(R.id.fab_inventario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), crear_nuevo_producto.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            final Uri selectedImage = data.getData();

            ContentResolver cr = mContext.getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

            SharedPreferences pref = mContext.getSharedPreferences("Registro", 0);
            final String Negocio = pref.getString("Negocio", "");

            if (selectedImage != null) {
                //displaying a progress dialog while upload is going on
                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("Subiendo a Firebase");
                progressDialog.show();

                final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                //Declaramos el nombre del archivo
                final StorageReference ref_producto = mStorageRef.child("imagenes_de_" + Negocio + "/" + nombre + "." +
                        mimeTypeMap.getExtensionFromMimeType(cr.getType(selectedImage)));

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                ref_producto.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        ref_producto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                assert Negocio != null;
                                databaseReference.child(Negocio).child("Productos de "+Negocio).child(nombre).child("Imagen").setValue(uri.toString());
                                modelo.setFotoProd(uri);
                                Toast.makeText(getActivity(), "Se añadió la foto, refresque la página para verla", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
            /*String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = mContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);
            cursor.close();*/



            //



            //assert Negocio != null;
            //

            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
