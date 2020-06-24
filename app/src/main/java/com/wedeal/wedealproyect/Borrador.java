/*package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

public class venta extends AppCompatActivity {
    FloatingActionButton fab;
    private DatabaseReference oDatabase;
    FirebaseDatabase firebaseDatabase;


    private GridView gridView;
    CustomAdapter_GridView_Productos customAdapterGridViewProductos;
    private List<modelo_producto> info_productos = new ArrayList<>();
    int foto_producto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ventas_crearventa);

        gridView = findViewById(R.id.grid_view_crearventa);
        SharedPreferences pref = getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Negocio).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Productos de " + Negocio)) {
                    databaseReference.child(Negocio).child("Productos de " + Negocio).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                                String codigo = objSnapshot.child("Código").getValue().toString();
                                String nombre = objSnapshot.child("Nombre").getValue().toString();
                                String precio = objSnapshot.child("Precio").getValue().toString();
                                String stock = objSnapshot.child("Stock").getValue().toString();

                                info_productos.add(new modelo_producto(codigo, nombre, precio, stock, R.drawable.product));

                                customAdapterGridViewProductos = new CustomAdapter_GridView_Productos(venta.this, foto_producto, info_productos);
                                gridView.setAdapter(customAdapterGridViewProductos);
                                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            final int i, long id) {
                                        Toast.makeText(venta.this, "GridView Item: " + info_productos.get(i).getNombre(), Toast.LENGTH_LONG).show();

                                        inicializarFirebase();


                                        oDatabase = FirebaseDatabase.getInstance().getReference();

                                        oDatabase.addValueEventListener(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                SharedPreferences preferences = getSharedPreferences("Registro", 0);

                                                String negocio = preferences.getString("Negocio", "");

                                                Object prod = Objects.requireNonNull(dataSnapshot.child(negocio).child("Productos de " + negocio).child(info_productos.get(i).getNombre()).getValue());
                                                String existencias = Objects.requireNonNull(dataSnapshot.child(negocio).child("Productos de " + negocio).child(info_productos.get(i).getNombre()).child("Stock").getValue().toString());

                                                databaseReference.child(negocio).child("Productos en trámite").child(info_productos.get(i).getNombre()).setValue(prod);

                                                String existenciasTR = Objects.requireNonNull(dataSnapshot.child(negocio).child("Productos en trámite").child(info_productos.get(i).getNombre()).child("Stock").getValue()).toString();






                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });





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
        fab = findViewById(R.id.fab_confirmarventa);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();

            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        oDatabase = firebaseDatabase.getReference();
    }
}*/