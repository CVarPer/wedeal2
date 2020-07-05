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

public class registro_particular extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_particular);
        Button registro_2 = findViewById(R.id.registrar_particular);

        inicializarFirebase();


        registro_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("Registro", 0);
                final String negocio = pref.getString("Negocio", "");

                final String nombrepart = ((EditText) findViewById(R.id.nombre_particular)).getText().toString().trim();
                final String telpart = ((EditText) findViewById(R.id.tel_particular)).getText().toString().trim();
                final String part = ((EditText) findViewById(R.id.correo_particular)).getText().toString().trim();
                final String passpart = ((EditText) findViewById(R.id.contra_particular)).getText().toString().trim();
                final String confpasspart = ((EditText) findViewById(R.id.confir_contra_particular)).getText().toString().trim();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(negocio).exists()) {
                            Toast.makeText(registro_particular.this, "Ya existe un usuario registrado bajo este correo", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if (part.length() <= 0) {
                                Toast.makeText(registro_particular.this, "Ingrese un correo válido", Toast.LENGTH_LONG).show();
                            }
                            if (telpart.length() != 7 && telpart.length() != 9) {
                                Toast.makeText(registro_particular.this, "Ingrese un teléfono válido", Toast.LENGTH_LONG).show();
                            }
                            else if (passpart.length() < 5) {
                                Toast.makeText(registro_particular.this, "Su contraseña debe tener 5 o más dígitos", Toast.LENGTH_LONG).show();
                            } else if (confpasspart.length() < 5) {
                                Toast.makeText(registro_particular.this, "Confirme su contraseña", Toast.LENGTH_LONG).show();
                            } else if (passpart.equals(confpasspart)) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                        databaseReference.child(part.replace(".", "")).child("Usuarios de " + part.replace(".", ""))
                                                .child(part.replace(".", "")).child("Usuario").setValue(part.replace(".", ""));
                                        databaseReference.child(part.replace(".", "")).child("Usuarios de " + part.replace(".", ""))
                                                .child(part.replace(".", "")).child("Contraseña").setValue(passpart.replace(".", ""));
                                        databaseReference.child(part.replace(".", "")).child("Usuarios de " + part.replace(".", ""))
                                                .child(part.replace(".", "")).child("Permisos").setValue("Particular");

                                        databaseReference.child(part.replace(".", "")).child("Información").child("Nombre").setValue(nombrepart);
                                        databaseReference.child(part.replace(".", "")).child("Información").child("Teléfono").setValue(telpart);


                                        databaseReference.child(part.replace(".", "")).child("Información").child("Negocio").setValue("No");

                                        Toast.makeText(registro_particular.this, "Usuario " + nombrepart + " registrado con éxito", Toast.LENGTH_LONG).show();
                                        Intent acceso = new Intent(registro_particular.this, sesion_de_particular.class);
                                        startActivity(acceso);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {


                                    }
                                });
                            } else {
                                Toast.makeText(registro_particular.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                            }
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