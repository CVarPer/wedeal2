package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ajustes_modificar_empleado extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes_modificarempleado);
        Button registro_2 = findViewById(R.id.guardarcambios);

        inicializarFirebase();


        registro_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("Registro", 0);

                String negocio = pref.getString("Negocio", "");

                String correoempleado = ((EditText) findViewById(R.id.correoemp)).getText().toString().trim();
                String cargoempleado = ((EditText) findViewById(R.id.nuevocargo)).getText().toString().trim();
                String telefonoempleado = ((EditText) findViewById(R.id.nuevotelefono)).getText().toString().trim();
                String salarioempleado = ((EditText) findViewById(R.id.nuevosalario)).getText().toString().trim();


                if(correoempleado.length() <= 0){
                    Toast.makeText(ajustes_modificar_empleado.this, "Ingrese un correo válido", Toast.LENGTH_LONG).show();
                }

                else{

                    assert negocio != null;

                    databaseReference.child(negocio).child("Usuarios de " + negocio).child(correoempleado.replace(".","")).child("Cargo").setValue(cargoempleado.replace(".",""));
                    databaseReference.child(negocio).child("Usuarios de " + negocio).child(correoempleado.replace(".","")).child("Telefono").setValue(telefonoempleado);
                    databaseReference.child(negocio).child("Usuarios de " + negocio).child(correoempleado.replace(".","")).child("Salario").setValue(salarioempleado);

                    finish();
                    Toast.makeText(ajustes_modificar_empleado.this, "Cambios guardados", Toast.LENGTH_LONG).show();
                    Intent acceso = new Intent(ajustes_modificar_empleado.this, Dueno.class);
                    startActivity(acceso);
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





