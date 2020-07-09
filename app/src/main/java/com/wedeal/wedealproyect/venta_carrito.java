package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class venta_carrito extends AppCompatActivity{
    FloatingActionButton fab1,fab2;
    private DatabaseReference oDatabase;
    FirebaseDatabase firebaseDatabase;


    private GridView gridView;
    CustomAdapter_GridView_Productos customAdapterGridViewProductos2;
    private List<modelo_producto> info_productos = new ArrayList<>();
    int foto_producto;
    ListAdapter m;
    modelo_producto modelo;
    int total = 0;
    boolean b = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ventas_carrito);

        gridView = findViewById(R.id.grid_view_carrito);
        SharedPreferences pref = getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");





        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        assert Negocio != null;
        databaseReference.child(Negocio).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Productos en trámite")) {
                    databaseReference.child(Negocio).child("Productos en trámite").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                                String codigo = objSnapshot.child("Código").getValue(String.class);
                                String nombre = objSnapshot.child("Nombre").getValue(String.class);
                                String precio = objSnapshot.child("Precio").getValue(String.class);
                                String stock = objSnapshot.child("Stock").getValue(String.class);

                                int t;

                                assert precio != null;
                                assert stock != null;
                                t = Integer.parseInt(precio) * Integer.parseInt(stock);

                                total = total + t;

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

                            }

                            customAdapterGridViewProductos2 = new CustomAdapter_GridView_Productos(com.wedeal.wedealproyect.venta_carrito.this, foto_producto, info_productos);
                            gridView.setAdapter(customAdapterGridViewProductos2);

                            String ttt = "Total venta: " + total;
                            TextView tootal = findViewById((R.id.total));
                            tootal.setText(ttt);


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

        fab1 = findViewById(R.id.fab_cancelar);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializarFirebase();
                FirebaseDatabase.getInstance().getReference().child(Negocio).child("Productos en trámite").removeValue();
                FirebaseDatabase.getInstance().getReference().child(Negocio).child("Informac").child("Productos en trámite").removeValue();
                Toast.makeText(venta_carrito.this, "El carro de compras ha sido vaciado", Toast.LENGTH_LONG).show();
            }
        });



        fab2 = findViewById(R.id.fab_confirmar);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference.child(Negocio).child("Productos en trámite").addValueEventListener(new ValueEventListener() {

                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot objSnapshot : dataSnapshot.getChildren()) {


                            String codigo = Objects.requireNonNull(objSnapshot.child("Código").getValue()).toString();

                            final String existencias = Objects.requireNonNull(objSnapshot.child("Stock").getValue()).toString();
                            final String precio = Objects.requireNonNull(objSnapshot.child("Precio").getValue()).toString();
                            final String nombre = Objects.requireNonNull(objSnapshot.child("Nombre").getValue()).toString();

                            databaseReference2.child(Negocio).child("Productos vendidos").child(nombre).child("Código").setValue(codigo);
                            databaseReference2.child(Negocio).child("Productos vendidos").child(nombre).child("Nombre").setValue(nombre);
                            databaseReference2.child(Negocio).child("Productos vendidos").child(nombre).child("Precio").setValue(precio);
                            databaseReference2.child(Negocio).child("Productos vendidos").child(nombre).child("Stock").setValue(existencias);

                            Date d = new Date();
                            String fecha = (String) DateFormat.format("MMMM d, yyyy ", d.getTime());
                            String mes = (String) DateFormat.format("MMMM", d.getTime());
                            databaseReference2.child(Negocio).child("Productos vendidos").child(nombre).child("Fecha").setValue(fecha);
                            databaseReference2.child(Negocio).child("Productos vendidos").child(nombre).child("Mes").setValue(mes);
                            databaseReference2.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(Negocio).child("Productos de " + Negocio).child(nombre).child("Stock").exists()) {
                                        String ext = Objects.requireNonNull(dataSnapshot.child(Negocio).child("Productos de " + Negocio).child(nombre).child("Stock").getValue()).toString();
                                        int p = Integer.parseInt(ext);
                                        int q = Integer.parseInt(existencias);

                                        Date d = new Date();
                                        final String mes = (String) DateFormat.format("MMMM", d.getTime());


                                        if (b) {
                                            b = false;

                                            databaseReference2.child(Negocio).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.child("Informes de " + mes).child("Total ventas").exists()) {
                                                        String Ventas = snapshot.child("Informes de " + mes).child("Total ventas").getValue(String.class);
                                                        long ventaTotal = Long.parseLong(Ventas);
                                                        ventaTotal += Long.parseLong(precio) * Long.parseLong(existencias);
                                                        databaseReference2.child(Negocio).child("Informes de " + mes).child("Total ventas").setValue(String.valueOf(ventaTotal));
                                                    } else {


                                                        databaseReference2.child(Negocio).child("Informes de " + mes).child("Total ventas").setValue(String.valueOf(Integer.parseInt(precio) * Integer.parseInt(existencias)));
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                            p = p - q;

                                            if (p <= 0) {
                                                databaseReference2.child(Negocio).child("Productos de " + Negocio).child(nombre).removeValue();

                                            } else {

                                                String ext2 = String.valueOf(p);
                                                databaseReference2.child(Negocio).child("Productos de " + Negocio).child(nombre).child("Stock").setValue(ext2);
                                            }

                                        }


                                    }


                                    databaseReference2.child(Negocio).child("Productos en trámite").removeValue();

                                    Toast.makeText(venta_carrito.this, "Venta realizada con éxito", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(venta_carrito.this, MainActivity.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });






            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        oDatabase = firebaseDatabase.getReference();
    }

}