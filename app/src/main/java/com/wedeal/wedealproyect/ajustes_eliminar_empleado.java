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

public class ajustes_eliminar_empleado extends AppCompatActivity {

    DatabaseReference nDatabase;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ajustes_eliminarempleado);
        Button eliminarusuario = findViewById(R.id.eliminarusuario);

        nDatabase = FirebaseDatabase.getInstance().getReference();

        inicializarFirebase();



        eliminarusuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                nDatabase.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        SharedPreferences pref = getSharedPreferences("Registro", 0);
                        String negocio = pref.getString("Negocio", "");

                        String usuarioadmin2 = ((EditText) findViewById(R.id.usuarioadmin2)).getText().toString().trim();
                        String contraadmin2 = ((EditText) findViewById(R.id.contraadmin2)).getText().toString().trim();
                        String usuarioempleado2 = ((EditText) findViewById(R.id.usuarioempleado2)).getText().toString().trim();

                        assert negocio != null;
                        String usuarioadminFirebase2 = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioadmin2.replace(".", "")).child("Usuario").getValue()).toString();
                        String contraadminFirebase2 = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioadmin2.replace(".", "")).child("Contraseña").getValue()).toString();
                        String usuarioempleadoFirebase2 = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioempleado2.replace(".", "")).child("Usuario").getValue()).toString();

                        String Permisoempleado = Objects.requireNonNull(dataSnapshot.child(negocio).child("Usuarios de " + negocio).child(usuarioempleado2.replace(".", "")).child("Permisos").getValue()).toString();

                        if (!usuarioadmin2.replace(".", "").equals(usuarioadminFirebase2)) {
                            Toast.makeText(ajustes_eliminar_empleado.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }
                        else if (!contraadmin2.equals(contraadminFirebase2)) {
                            Toast.makeText(ajustes_eliminar_empleado.this, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }
                        else if (!usuarioempleado2.replace(".", "").equals(usuarioempleadoFirebase2)) {

                            Toast.makeText(ajustes_eliminar_empleado.this, "El empleado no está registrado", Toast.LENGTH_LONG).show();

                        }
                        else if (Permisoempleado.equals("Admin")){
                            Toast.makeText(ajustes_eliminar_empleado.this, "No puede eliminar una cuenta de administrador", Toast.LENGTH_LONG).show();

                        }
                        else{
                            nDatabase.child(negocio).child("Usuarios de " + negocio).child(usuarioempleado2.replace(".","")).removeValue();
                            finish();
                            Toast.makeText(ajustes_eliminar_empleado.this, "El usuario ha sido eliminado", Toast.LENGTH_LONG).show();
                            Intent acceso = new Intent(ajustes_eliminar_empleado.this, sesion_de_dueno.class);
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


