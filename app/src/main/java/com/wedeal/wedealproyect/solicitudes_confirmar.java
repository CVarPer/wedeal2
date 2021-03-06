package com.wedeal.wedealproyect;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.GridView;
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

public class solicitudes_confirmar extends AppCompatActivity{
    FloatingActionButton fab1,fab2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference oDatabase;

    private GridView gridView;
    CustomAdapter_GridView_Productos customAdapterGridViewProductos2;
    private List<modelo_producto> info_productos = new ArrayList<>();
    int foto_producto;
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
        final String cliente = getIntent().getStringExtra("cliente");
        final String tel_cliente = getIntent().getStringExtra("telefono");
        final String tipo_cliente = getIntent().getStringExtra("TP");

        Date d = new Date();
        final String mes = (String) DateFormat.format("MMMM", d.getTime());

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference databRef = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Negocio).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child(Negocio).child("Solicitudes").child("Solicitud de " + cliente).child("Productos").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                            String codigo = objSnapshot.child("Código").getValue(String.class);
                            String nombre = objSnapshot.child("Nombre").getValue(String.class);
                            String precio = objSnapshot.child("Precio").getValue(String.class);
                            String stock = objSnapshot.child("Stock").getValue(String.class);

                            int t;

                            t = Integer.parseInt(precio) * Integer.parseInt(stock);
                            total = total + t;
                            final String Valor_Compra = String.valueOf(total);

                            modelo = new modelo_producto();
                            modelo.setCodigo(codigo);
                            modelo.setNombre(nombre);
                            modelo.setPrecio(precio);
                            modelo.setStock(stock);
                            if (objSnapshot.child("Imagen").exists()) {
                                String urlImagen = objSnapshot.child("Imagen").getValue(String.class);
                                Uri imagen = Uri.parse(urlImagen);
                                modelo.setFotoProd(imagen);
                            }

                            info_productos.add(modelo);

                        }

                            customAdapterGridViewProductos2 = new CustomAdapter_GridView_Productos(solicitudes_confirmar.this, foto_producto, info_productos);
                            gridView.setAdapter(customAdapterGridViewProductos2);

                            String ttt = String.valueOf(total);
                            TextView tootal = findViewById((R.id.total));
                            tootal.setText(ttt);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




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

                FirebaseDatabase.getInstance().getReference().child(cliente).child("Encargos").child("Solicitud a "+Negocio).child("Info").child("Estado").setValue("Rechazado");
                FirebaseDatabase.getInstance().getReference().child(Negocio).child("Solicitudes").child("Solicitud de "+cliente).removeValue();
                Toast.makeText(solicitudes_confirmar.this, "La solicitud de "+cliente+" ha sido rechazada", Toast.LENGTH_LONG).show();



            }
        });



        fab2 = findViewById(R.id.fab_confirmar);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                        if(!dataSnapshot2.child(Negocio).child("Clientes de "+Negocio).child(cliente).child("Compras").exists()){
                            databRef.child(Negocio).child("Clientes de "+Negocio).child(cliente).child("Compras").setValue("1");
                        }
                        else{
                            String c = dataSnapshot2.child(Negocio).child("Clientes de "+Negocio).child(cliente).child("Compras").getValue().toString();
                            int z = Integer.parseInt(c);
                            z = z+1;
                            c = String.valueOf(z);
                            databaseReference.child(Negocio).child("Clientes de "+Negocio).child(cliente).child("Compras").setValue(c);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });{

                }

                databRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        assert cliente != null;
                        final String nombrecliente = snapshot.child(cliente).child("Información").child("Nombre").getValue().toString();
                        databaseReference.child(Negocio).child("Solicitudes").child("Solicitud de "+cliente).child("Productos").addListenerForSingleValueEvent(new ValueEventListener() {

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

                                    databaseReference2.child(Negocio).child("Clientes de "+Negocio).child(cliente).child("Nombre").setValue(nombrecliente);
                                    databaseReference2.child(Negocio).child("Clientes de "+Negocio).child(cliente).child("Teléfono").setValue(tel_cliente);
                                    databaseReference2.child(Negocio).child("Clientes de "+Negocio).child(cliente).child("Tipo de cliente").setValue(tipo_cliente);

                                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.child(Negocio).child("Productos de " + Negocio).child(nombre).child("Stock").exists()){
                                                String ext = Objects.requireNonNull(dataSnapshot.child(Negocio).child("Productos de " + Negocio).child(nombre).child("Stock").getValue()).toString();
                                                int p = Integer.parseInt(ext);
                                                final int q = Integer.parseInt(existencias);

                                                if (b) {
                                                    b = false;
                                                    p = p - q;
                                                    databaseReference.child(cliente).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.child("Informes de " + mes).child("Total ventas").exists()) {
                                                                String gasto = snapshot.child("Informes").child("Total ventas").getValue().toString();
                                                                int SolicitudesSuma = Integer.parseInt(gasto);
                                                                SolicitudesSuma += total;
                                                                databaseReference.child(cliente).child("Informes de " + mes).child("Total ventas").setValue(String.valueOf(SolicitudesSuma));
                                                            } else {
                                                                int precio_ = Integer.parseInt(precio) * q;
                                                                databaseReference.child(cliente).child("Informes de " + mes).child("Total ventas").setValue(String.valueOf(precio_));
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                    if (p <= 0) {
                                                        databaseReference2.child(Negocio).child("Productos de " + Negocio).child(nombre).removeValue();

                                                    } else {

                                                        String ext2 = String.valueOf(p);
                                                        databaseReference2.child(Negocio).child("Productos de " + Negocio).child(nombre).child("Stock").setValue(ext2);
                                                    }

                                                }


                                            }

                                            FirebaseDatabase.getInstance().getReference().child(cliente).child("Encargos").child("Solicitud a "+Negocio).child("Info").child("Estado").setValue("Aceptado");

                                            databaseReference2.child(Negocio).child("Solicitudes").child("Solicitud de "+cliente).removeValue();

                                            Toast.makeText(solicitudes_confirmar.this, "La solicitud de "+cliente+" ha sido aceptada y los productos han sido vendidos", Toast.LENGTH_LONG).show();


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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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