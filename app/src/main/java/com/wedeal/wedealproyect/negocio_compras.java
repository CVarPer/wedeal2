package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
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

public class negocio_compras extends AppCompatActivity implements AdapterView.OnItemClickListener{
    FloatingActionButton fab;
    private DatabaseReference oDatabase;
    FirebaseDatabase firebaseDatabase;


    private GridView gridView;
    CustomAdapter_GridView_Productos customAdapterGridViewProductos2;
    private List<modelo_producto> info_productos = new ArrayList<>();
    int foto_producto;
    Boolean click = false;
    modelo_producto modelo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ventas_crearventa);

        final String proveedor = getIntent().getStringExtra("proveedor");

        gridView = findViewById(R.id.grid_view_crearventa);
        gridView.setOnItemClickListener(this);
        SharedPreferences pref = getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(proveedor).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Productos de " + proveedor)) {
                    databaseReference.child(proveedor).child("Productos de " + proveedor).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                                String codigo = objSnapshot.child("Código").getValue().toString();
                                String nombre = objSnapshot.child("Nombre").getValue().toString();
                                String precio = objSnapshot.child("Precio").getValue().toString();
                                String stock = objSnapshot.child("Stock").getValue().toString();

                                modelo = new modelo_producto();
                                modelo.setCodigo(codigo);
                                modelo.setNombre(nombre);
                                modelo.setPrecio(precio);
                                modelo.setStock(stock);
                                modelo.setFotoProd(BitmapFactory.decodeFile(String.valueOf(R.drawable.product)));

                                info_productos.add(modelo);

                            }

                            customAdapterGridViewProductos2 = new CustomAdapter_GridView_Productos(negocio_compras.this, foto_producto, info_productos);
                            gridView.setAdapter(customAdapterGridViewProductos2);


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

        fab = findViewById(R.id.fab_carrito);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(negocio_compras.this, negocio_compras_carrito.class);
                intent.putExtra("proveedor", proveedor);
                startActivity(intent);

            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        oDatabase = firebaseDatabase.getReference();
    }


    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onItemClick(AdapterView<?> AdapterView, final View view, final int i, long id) {

        if(!click){
            click = true;



            inicializarFirebase();

            oDatabase = FirebaseDatabase.getInstance().getReference();

            oDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SharedPreferences preferences = getSharedPreferences("Registro", 0);

                    String negocio = preferences.getString("Negocio", "");

                    final String proveedor = getIntent().getStringExtra("proveedor");


                    String codigo = Objects.requireNonNull(dataSnapshot.child(proveedor).child("Productos de " + proveedor).child(info_productos.get(i).getNombre()).child("Código").getValue().toString());
                    String existencias = Objects.requireNonNull(dataSnapshot.child(proveedor).child("Productos de " + proveedor).child(info_productos.get(i).getNombre()).child("Stock").getValue()).toString();
                    String precio = Objects.requireNonNull(dataSnapshot.child(proveedor).child("Productos de " + proveedor).child(info_productos.get(i).getNombre()).child("Precio").getValue().toString());
                    String nombre = Objects.requireNonNull(dataSnapshot.child(proveedor).child("Productos de " + proveedor).child(info_productos.get(i).getNombre()).child("Nombre").getValue().toString());

                    databaseReference.child(negocio).child("Solicitud a " + proveedor).child(info_productos.get(i).getNombre()).child("Nombre").setValue(nombre);
                    databaseReference.child(negocio).child("Solicitud a " + proveedor).child(info_productos.get(i).getNombre()).child("Precio").setValue(precio);
                    databaseReference.child(negocio).child("Solicitud a " + proveedor).child(info_productos.get(i).getNombre()).child("Código").setValue(codigo);
                    databaseReference.child(negocio).child("Solicitud a " + proveedor).child(info_productos.get(i).getNombre()).child("Stock").setValue("0");

                    int b =  Integer.parseInt(existencias);

                    if(!preferences.contains(nombre)){
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putInt(nombre, 0);
                        edit.apply();
                    }

                    int k = preferences.getInt(nombre,0);

                    if (k < b){
                        k += 1;
                    }

                    else if (k == b){
                        Toast.makeText(negocio_compras.this, "No hay más "+nombre+" disponibles", Toast.LENGTH_LONG).show();
                    }

                    String f = String.valueOf(k);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putInt(nombre, k);
                    edit.apply();


                    Intent intent = new Intent(negocio_compras.this, negocio_compras_tramite.class);
                    intent.putExtra("proveedor", proveedor);
                    intent.putExtra("negocio",negocio);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("f",f);
                    startActivity(intent);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
}