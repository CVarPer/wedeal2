package com.wedeal.wedealproyect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class perfil extends Fragment {
    Context mContext;
    private ImageButton imageButton;
    private ImageView fotoNeg;
    static TextView nombre_negocio, telefono_negocio;
    private DatabaseReference databaseReference;

    private static int RESULT_LOAD_IMAGE = 10;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public perfil() {
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
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nombre_negocio = getView().findViewById(R.id.nombre_negocio);
        telefono_negocio = getView().findViewById(R.id.telefono_negocio);
        imageButton = getView().findViewById(R.id.imageButton);
        fotoNeg = getView().findViewById(R.id.imagen_negocio);
        //Nodo principal de la base de datos
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");
        final String Usuario = pref.getString("Usuario","");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Particulares").child("Usuarios de Particulares").child(Usuario.replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("Nombre").getValue().toString();
                    nombre_negocio.setText(nombre);
                    String telefono = dataSnapshot.child("Teléfono").getValue().toString();
                    telefono_negocio.setText(telefono);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child(Negocio.replace(".", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fotoNeg.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(R.drawable.tienda)));

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
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            assert selectedImage != null;
            Cursor cursor = mContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);
            cursor.close();

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            SharedPreferences pref = mContext.getSharedPreferences("Registro", 0);
            final String Negocio = pref.getString("Negocio", "");

            assert Negocio != null;
            databaseReference.child(Negocio).child("Información negocio").child("Imagen").setValue(picturePath);



        }
    }

}
