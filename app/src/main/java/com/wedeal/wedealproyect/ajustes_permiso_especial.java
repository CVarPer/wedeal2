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

public class ajustes_permiso_especial extends AppCompatActivity {

    DatabaseReference nDatabase;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ajustes_permisoespecial);
        Button darpermiso = findViewById(R.id.darpermiso);

        nDatabase = FirebaseDatabase.getInstance().getReference();

        inicializarFirebase();



        darpermiso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                nDatabase.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        SharedPreferences pref = getSharedPreferences("Registro", 0);
                        String negocio = pref.getString("Negocio", "");

                        String usuarioadmin = ((EditText) findViewById(R.id.usuarioadmin)).getText().toString().trim();
                        String contraadmin = ((EditText) findViewById(R.id.contraadmin)).getText().toString().trim();
                        String usuarioempleado = ((EditText) findViewById(R.id.usuarioempleado)).getText().toString().trim();

                        String usuarioadminFirebase = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioadmin.replace(".", "")).child("Usuario").getValue()).toString();
                        String contraadminFirebase = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioadmin.replace(".", "")).child("Contraseña").getValue()).toString();
                        String usuarioempleadoFirebase = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioempleado.replace(".", "")).child("Usuario").getValue()).toString();

                        if (!usuarioadmin.replace(".", "").equals(usuarioadminFirebase)) {
                            Toast.makeText(ajustes_permiso_especial.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }
                        else if (!contraadmin.equals(contraadminFirebase)) {
                            Toast.makeText(ajustes_permiso_especial.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }
                        else if (!usuarioempleado.replace(".", "").equals(usuarioempleadoFirebase)) {

                            Toast.makeText(ajustes_permiso_especial.this, "El empleado no está registrado", Toast.LENGTH_LONG).show();

                        }
                        else{
                            nDatabase.child(negocio).child("Usuarios de " + negocio).child(usuarioempleado.replace(".","")).child("Permisos").setValue("Admin");
                            finish();
                            Toast.makeText(ajustes_permiso_especial.this, usuarioempleado + " ahora tiene permisos de administrador", Toast.LENGTH_LONG).show();
                            Intent acceso = new Intent(ajustes_permiso_especial.this, sesion_de_dueno.class);
                            startActivity(acceso);

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
        nDatabase = firebaseDatabase.getReference();
    }


}


