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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public EditText usuario_log, contra_log, neg_log;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("Registro", 0);

        String userSHP = pref.getString("Usuario", "");


        mDatabase = FirebaseDatabase.getInstance().getReference();

        assert userSHP != null;
        if (userSHP.length()>2) {



            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SharedPreferences preferences = getSharedPreferences("Registro", 0);

                    String negocio = preferences.getString("Negocio", "");
                    String usuarioSHP = preferences.getString("Usuario", "");
                    String contraSHP = preferences.getString("Contraseña", "");

                    assert negocio != null;
                    String permiso = Objects.requireNonNull(dataSnapshot.child(negocio.replace(".", ""))
                            .child("Usuarios de " + negocio.replace(".", ""))
                            .child(usuarioSHP.replace(".", "")).child("Permisos").getValue()).toString();

                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                    preferencesEditor.clear();
                    preferencesEditor.apply();

                    preferencesEditor.putString("Usuario", usuarioSHP);
                    preferencesEditor.putString("Contraseña", contraSHP);
                    preferencesEditor.putString("Negocio", negocio);
                    preferencesEditor.apply();


                    switch (permiso) {
                        case "Admin":
                            Intent dueno = new Intent(MainActivity.this, sesion_de_dueno.class);
                            startActivity(dueno);
                            break;
                        case "Empleado": {
                            Intent empleado = new Intent(MainActivity.this, sesion_de_empleado.class);
                            startActivity(empleado);
                            break;
                        }
                        case "Particular": {
                            Intent empleado = new Intent(MainActivity.this, sesion_de_particular.class);
                            startActivity(empleado);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        else {
            usuario_log = findViewById(R.id.email);
            contra_log = findViewById(R.id.password);
            neg_log = findViewById(R.id.neg);
            Button registro = findViewById(R.id.registro);
            Button registro_cliente = findViewById(R.id.registro_cliente);
            mDatabase = FirebaseDatabase.getInstance().getReference();

            registro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent reg = new Intent(MainActivity.this, registro_negocio.class);
                    startActivity(reg);
                }
            });

            registro_cliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent regc = new Intent(MainActivity.this, registro_particular.class);
                    startActivity(regc);
                }
            });

            Button ingreso = findViewById(R.id.ingreso);
            ingreso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            SharedPreferences preferences = getSharedPreferences("Registro", 0);

                            String negocio = neg_log.getText().toString().trim();
                            String user = usuario_log.getText().toString().trim();
                            String pass = contra_log.getText().toString().trim();


                            if (negocio.length() == 0){
                                negocio = user;
                            }

                            if (user.length() == 0 | pass.length() == 0) {
                                Toast.makeText(MainActivity.this, "Porfavor llenar todos los campos", Toast.LENGTH_LONG).show();
                            }
                            else {
                                String usuarioenfirebase = Objects.requireNonNull(dataSnapshot.child(negocio.replace(".", ""))
                                        .child("Usuarios de " + negocio.replace(".", "")).child(user.replace(".", ""))
                                        .child("Usuario").getValue(String.class));
                                String contrasenaenfirebase = Objects.requireNonNull(dataSnapshot.child(negocio.replace(".", ""))
                                        .child("Usuarios de " + negocio.replace(".", "")).child(user.replace(".", ""))
                                        .child("Contraseña").getValue(String.class));

                                if (user.replace(".", "").equals(usuarioenfirebase) && pass.equals(contrasenaenfirebase)) {

                                    SharedPreferences.Editor edit = preferences.edit();
                                    edit.putString("Usuario", user);
                                    edit.putString("Contraseña", pass);
                                    edit.putString("Negocio", negocio);
                                    edit.apply();

                                    String permiso = Objects.requireNonNull(dataSnapshot.child(negocio.replace(".", ""))
                                            .child("Usuarios de " + negocio.replace(".", "")).child(user.replace(".", ""))
                                            .child("Permisos").getValue()).toString();

                                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                                    preferencesEditor.clear();
                                    preferencesEditor.apply();

                                    preferencesEditor.putString("Usuario", user);
                                    preferencesEditor.putString("Contraseña", pass);
                                    preferencesEditor.putString("Negocio", negocio);
                                    preferencesEditor.apply();


                                    switch (permiso) {
                                        case "Admin":
                                            Intent dueno = new Intent(MainActivity.this, sesion_de_dueno.class);
                                            startActivity(dueno);
                                            break;
                                        case "Empleado": {
                                            Intent empleado = new Intent(MainActivity.this, sesion_de_empleado.class);
                                            startActivity(empleado);
                                            break;
                                        }
                                        case "Particular": {
                                            Intent empleado = new Intent(MainActivity.this, sesion_de_particular.class);
                                            startActivity(empleado);
                                            break;
                                        }
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Usuario y/o contraseña y/o negocio incorrectos", Toast.LENGTH_LONG).show();
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });





                /*SharedPreferences pref = getSharedPreferences("Registro", 0);
                //EditText verifica si el usuario existe
                String user = pref.getString("Usuario", "");
                String contraseña = pref.getString("Contraseña", "");
                if (usuario_log.getText().toString().trim().length() == 0 && contralog.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "Los campos están vacíos", Toast.LENGTH_LONG).show();
                }
                //Procede a iniciar la sesión del usuario registrado
                else if (usuario_log.getText().toString().trim().equals(user) && contralog.getText().toString().trim().equals(contraseña)) {
                    Intent logear = new Intent(MainActivity.this, Dueno.class);
                    startActivity(logear);
                }
                else{
                    Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }*/

                }
            });

        }
    }


}