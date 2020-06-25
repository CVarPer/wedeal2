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

public class crear_nuevo_proveedor extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_proveedor);
        Button registro_2 = findViewById(R.id.anadirproveedor);

        inicializarFirebase();


        registro_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference();

                SharedPreferences pref = getSharedPreferences("Registro", 0);

                final String negocio = pref.getString("Negocio", "");

                final String nombreprov = ((EditText) findViewById(R.id.nombreproveedor)).getText().toString().trim();


                if(nombreprov.length() <= 0 ){
                    Toast.makeText(crear_nuevo_proveedor.this, "Ingrese un nombre", Toast.LENGTH_LONG).show();
                }


                else{

                    assert negocio != null;
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String telefonoprov = dataSnapshot.child(nombreprov).child("Información del negocio "+nombreprov).child("Teléfono").getValue().toString();
                            String direccionprov = dataSnapshot.child(nombreprov).child("Información del negocio "+nombreprov).child("Dirección").getValue().toString();



                            databaseReference.child(negocio).child("Proveedores de " + negocio).child(nombreprov).child("Nombre").setValue(nombreprov);
                            databaseReference.child(negocio).child("Proveedores de " + negocio).child(nombreprov).child("Teléfono").setValue(telefonoprov);
                            databaseReference.child(negocio).child("Proveedores de " + negocio).child(nombreprov).child("Dirección").setValue(direccionprov);


                            finish();
                            Toast.makeText(crear_nuevo_proveedor.this, "Proveedor registrado con éxito", Toast.LENGTH_LONG).show();
                            Intent acceso = new Intent(crear_nuevo_proveedor.this, sesion_de_dueno.class);
                            startActivity(acceso);

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





        /*Button agregar_empl = findViewById(R.id.crear_nuevo_empl);
        agregar_empl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("Nuevo Empleado", 0);
                String nombre = ((EditText) findViewById(R.id.nuevo_nombretxt)).getText().toString().trim();
                String telefono = ((EditText) findViewById(R.id.nuevoteltxt)).getText().toString().trim();
                String cargo = ((EditText) findViewById(R.id.roltxt)).getText().toString().trim();
                String salario = ((EditText) findViewById(R.id.salariotxt)).getText().toString().trim();
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("Nombre empleado",nombre);
                edit.putString("Teléfono empleado",telefono);
                edit.putString("Cargo empleado",cargo);
                edit.putString("Salario empleado",salario);
                edit.commit();
                finish();


            }
        });*/
