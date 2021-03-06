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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class info extends Fragment {
    Context mContext;
    private ImageButton imageButton;
    private ImageView fotoNeg;
    static TextView nombre_negocio, direccion_negocio, telefono_negocio;
    private DatabaseReference databaseReference;

    private static int RESULT_LOAD_IMAGE = 10;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public info() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nombre_negocio = getView().findViewById(R.id.nombre_negocio);
        direccion_negocio = getView().findViewById(R.id.direccion_negocio);
        telefono_negocio = getView().findViewById(R.id.telefono_negocio);
        imageButton = getView().findViewById(R.id.imageButton);
        fotoNeg = getView().findViewById(R.id.imagen_negocio);
        //Nodo principal de la base de datos
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        assert Negocio != null;
        databaseReference.child(Negocio).child("Información").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("Nombre").getValue(String.class);
                    nombre_negocio.setText(nombre);
                    String direccion = dataSnapshot.child("Dirección").getValue(String.class);
                    direccion_negocio.setText(direccion);
                    String telefono = dataSnapshot.child("Teléfono").getValue(String.class);
                    telefono_negocio.setText(telefono);
                    if (dataSnapshot.hasChild("Imagen")){
                        String foto = dataSnapshot.child("Imagen").getValue(String.class);
                        Uri imgUrl = Uri.parse(foto);
                        Picasso.get().load(imgUrl).into(fotoNeg);
                    }
                    else{
                        fotoNeg.setImageResource(R.drawable.tienda);
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                startActivityForResult(Intent.createChooser(intent, "Selecciona app de imágenes"), RESULT_LOAD_IMAGE);
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
                final StorageReference ref_producto = mStorageRef.child("imagenes_de_" + Negocio + "/" + Negocio + "." +
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
                                databaseReference.child(Negocio).child("Información").child("Imagen").setValue(uri.toString());
                                Picasso.get().load(uri).into(fotoNeg);

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
            ;


        }
    }

}
