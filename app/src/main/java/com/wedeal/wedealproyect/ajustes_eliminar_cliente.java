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

public class ajustes_eliminar_cliente extends AppCompatActivity {

    DatabaseReference nDatabase;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ajustes_eliminarcliente);
        Button eliminarusuario = findViewById(R.id.eliminar_cliente);

        nDatabase = FirebaseDatabase.getInstance().getReference();

        inicializarFirebase();



        eliminarusuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                nDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        SharedPreferences pref = getSharedPreferences("Registro", 0);
                        String negocio = pref.getString("Negocio", "");

                        String usuarioadmin2 = ((EditText) findViewById(R.id.usuarioadmin3)).getText().toString().trim();
                        String contraadmin2 = ((EditText) findViewById(R.id.contraadmin3)).getText().toString().trim();
                        String cliente = ((EditText) findViewById(R.id.cliente)).getText().toString().trim();

                        assert negocio != null;
                        String usuarioadminFirebase2 = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioadmin2.replace(".", "")).child("Usuario").getValue()).toString();
                        String contraadminFirebase2 = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioadmin2.replace(".", "")).child("Contraseña").getValue()).toString();
                        String clienteFirebase = Objects.requireNonNull(dataSnapshot.child(negocio).child("Clientes de " + negocio).child(cliente).child("Nombre").getValue()).toString();


                        if (!dataSnapshot.child(negocio).child("Clientes de " + negocio).child(cliente).child("Nombre").exists()) {

                            Toast.makeText(ajustes_eliminar_cliente.this, "Usted no cuenta con este cliente", Toast.LENGTH_LONG).show();
                            Toast.makeText(ajustes_eliminar_cliente.this, clienteFirebase, Toast.LENGTH_LONG).show();

                        }

                        else{
                            if (!usuarioadmin2.replace(".", "").equals(usuarioadminFirebase2)) {
                                Toast.makeText(ajustes_eliminar_cliente.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                            }
                            else if (!contraadmin2.equals(contraadminFirebase2)) {
                                Toast.makeText(ajustes_eliminar_cliente.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                            }
                            else{
                                nDatabase.child(negocio).child("Clientes de " + negocio).child(cliente).removeValue();
                                finish();
                                Toast.makeText(ajustes_eliminar_cliente.this, "El cliente ha sido eliminado", Toast.LENGTH_LONG).show();
                                Intent acceso = new Intent(ajustes_eliminar_cliente.this, sesion_de_dueno.class);
                                startActivity(acceso);

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
        nDatabase = firebaseDatabase.getReference();
    }


}


