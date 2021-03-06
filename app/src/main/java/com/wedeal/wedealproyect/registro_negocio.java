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

public class registro_negocio extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Button registro_2 = findViewById(R.id.registrarse);
        inicializarFirebase();


        registro_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final SharedPreferences preferences = getSharedPreferences("Registro",0);


                final String usuario = ((EditText) findViewById(R.id.usuario)).getText().toString().trim();
                final String password = ((EditText) findViewById(R.id.contra)).getText().toString().trim();

                final String confpass = ((EditText) findViewById(R.id.confir_contra)).getText().toString().trim();
                final String Negocio = ((EditText) findViewById(R.id.nombre_neg)).getText().toString().trim();
                final String Direccion = ((EditText) findViewById(R.id.direccion_neg)).getText().toString().trim();
                final String Telefono = ((EditText) findViewById(R.id.tel_neg)).getText().toString().trim();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(Negocio).exists()){
                            Toast.makeText(registro_negocio.this, "Ya existe un negocio registrado bajo este nombre", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(usuario.length() <= 0){
                                Toast.makeText(registro_negocio.this, "Ingrese un correo válido", Toast.LENGTH_LONG).show();
                            }
                            else if (password.length() < 5){
                                Toast.makeText(registro_negocio.this, "Su contraseña debe tener 5 o más dígitos", Toast.LENGTH_LONG).show();
                            }
                            else if (confpass.length() < 5){
                                Toast.makeText(registro_negocio.this, "Confirme su contraseña", Toast.LENGTH_LONG).show();
                            }

                            else if(Negocio.length() <= 0){
                                Toast.makeText(registro_negocio.this, "Ingrese el nombre de su negocio", Toast.LENGTH_LONG).show();
                            }
                            else if (Direccion.length() <= 0) {
                                Toast.makeText(registro_negocio.this, "Ingrese la dirección del negocio", Toast.LENGTH_LONG).show();
                            } else if (Telefono.length() != 7 && Telefono.length() != 9) {
                                Toast.makeText(registro_negocio.this, "Ingrese un téléfono válido para su negocio", Toast.LENGTH_LONG).show();
                            } else if (password.equals(confpass)) {


                                databaseReference.child(Negocio).child("Usuarios de " + Negocio).child(usuario.replace(".", ""))
                                        .child("Usuario").setValue(usuario.replace(".", ""));
                                databaseReference.child(Negocio).child("Usuarios de " + Negocio).child(usuario.replace(".", ""))
                                        .child("Contraseña").setValue(password);
                                databaseReference.child(Negocio).child("Usuarios de " + Negocio).child(usuario.replace(".", ""))
                                        .child("Permisos").setValue("Admin");

                                databaseReference.child(Negocio).child("Información").child("Dirección").setValue(Direccion);
                                databaseReference.child(Negocio).child("Información").child("Teléfono").setValue(Telefono);
                                databaseReference.child(Negocio).child("Información").child("Nombre").setValue(Negocio);
                                databaseReference.child(Negocio).child("Información").child("Negocio").setValue("Si");


                                SharedPreferences.Editor edit = preferences.edit();
                                edit.clear();
                                edit.apply();

                                edit.putString("Usuario",usuario);
                                edit.putString("Contraseña",password);
                                edit.putString("Negocio",Negocio);
                                edit.apply();

                                finish();
                                Toast.makeText(registro_negocio.this, "Usuario creado con éxito", Toast.LENGTH_LONG).show();
                                Intent acceso = new Intent(registro_negocio.this, sesion_de_dueno.class);
                                startActivity(acceso);
                            }
                            else{
                                Toast.makeText(registro_negocio.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
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

