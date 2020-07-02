package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class encargos_vistaproductos extends AppCompatActivity {
    FloatingActionButton fab;
    FirebaseDatabase firebaseDatabase;


    private GridView gridView;
    CustomAdapter_GridView_Productos customAdapterGridViewProductos2;
    private List<modelo_producto> info_productos = new ArrayList<>();
    int foto_producto;
    modelo_producto modelo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ver_encargo);

        gridView = findViewById(R.id.grid_ver_encargo);
        SharedPreferences pref = getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");
        final String proveedor = getIntent().getStringExtra("proveedor");


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Negocio).child("Encargos").child("Solicitud a "+proveedor).child("Productos").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Toast.makeText(encargos_vistaproductos.this, proveedor, Toast.LENGTH_LONG).show();



                            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                                String codigo = objSnapshot.child("Código").getValue(String.class);
                                String nombre = objSnapshot.child("Nombre").getValue(String.class);
                                String precio = objSnapshot.child("Precio").getValue(String.class);
                                String stock = objSnapshot.child("Stock").getValue(String.class);

                                modelo = new modelo_producto();
                                modelo.setCodigo(codigo);
                                modelo.setNombre(nombre);
                                modelo.setPrecio(precio);
                                modelo.setStock(stock);

                                if(objSnapshot.child("Imagen").exists()){
                                    String urlImagen = objSnapshot.child("Imagen").getValue(String.class);
                                    Uri imagen = Uri.parse(urlImagen);
                                    modelo.setFotoProd(imagen);
                                }



                                info_productos.add(modelo);



                            customAdapterGridViewProductos2 = new CustomAdapter_GridView_Productos(encargos_vistaproductos.this, foto_producto, info_productos);
                            gridView.setAdapter(customAdapterGridViewProductos2);


                        }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }


        });
    }


}