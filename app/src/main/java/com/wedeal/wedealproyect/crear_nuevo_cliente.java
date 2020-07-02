package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class crear_nuevo_cliente extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_cliente);
        Button registro_2 = findViewById(R.id.anadircliente);

        inicializarFirebase();


        registro_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference();

                SharedPreferences pref = getSharedPreferences("Registro", 0);

                final String negocio = pref.getString("Negocio", "").replace(".","");

                final String usuarioSHP = pref.getString("Usuario", "");

                final String nombrecl = ((EditText) findViewById(R.id.correocliente)).getText().toString().trim();


                if(nombrecl.length() <= 0 ){
                    Toast.makeText(crear_nuevo_cliente.this, "Ingrese un nombre", Toast.LENGTH_LONG).show();
                }


                else{

                    assert negocio != null;
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String telefonocl = dataSnapshot.child(nombrecl.replace(".","")).child("Información").child("Teléfono").getValue().toString();
                            String nombre = dataSnapshot.child(nombrecl.replace(".","")).child("Información").child("Nombre").getValue().toString();
                            String permiso = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio.replace(".","")).child(usuarioSHP.replace(".", "")).child("Permisos").getValue()).toString();
                            String neg = dataSnapshot.child(nombrecl.replace(".","")).child("Información").child("Negocio").getValue().toString();

                            if(neg.equals("Si")){
                                databaseReference.child(negocio).child("Clientes de " + negocio).child(nombrecl).child("Tipo de cliente").setValue("Negocio");
                            }

                            else if(neg.equals("No")){
                                databaseReference.child(negocio).child("Clientes de " + negocio).child(nombrecl).child("Tipo de cliente").setValue("Particular");
                            }

                            databaseReference.child(negocio).child("Clientes de " + negocio).child(nombrecl).child("Nombre").setValue(nombre);
                            databaseReference.child(negocio).child("Clientes de " + negocio).child(nombrecl).child("Teléfono").setValue(telefonocl);
                            databaseReference.child(negocio).child("Clientes de " + negocio).child(nombrecl).child("Compras").setValue("0");


                            if (permiso.equals("Admin")){
                                databaseReference.child(negocio).child("Clientes de " + negocio).child(nombrecl).child("Tipo de Cliente").setValue("Negocio");
                                Intent dueno = new Intent(crear_nuevo_cliente.this, sesion_de_dueno.class);
                                startActivity(dueno);
                            }
                            else if(permiso.equals("Empleado")){
                                Intent part = new Intent(crear_nuevo_cliente.this, sesion_de_empleado.class);
                                startActivity(part);
                            }



                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                }



            }


        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
