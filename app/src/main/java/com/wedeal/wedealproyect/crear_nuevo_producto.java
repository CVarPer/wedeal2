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

public class crear_nuevo_producto extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_producto);
        Button registro_2 = findViewById(R.id.anadirproducto);

        inicializarFirebase();


        registro_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("Registro", 0);

                String negocio = pref.getString("Negocio", "");

                String producto = ((EditText) findViewById(R.id.nombreproducto)).getText().toString().trim();
                String numeroejemplares = ((EditText) findViewById(R.id.nmejemplaresproducto)).getText().toString().trim();
                String descripcion = ((EditText) findViewById(R.id.descripcionproducto)).getText().toString().trim();



                if(producto.length() <= 0) {
                    Toast.makeText(crear_nuevo_producto.this, "Ingrese un correo válido", Toast.LENGTH_LONG).show();
                }

                else{

                    assert negocio != null;

                    databaseReference.child(negocio).child("Productos de " + negocio).child(producto).child("Nombre").setValue(producto);
                    databaseReference.child(negocio).child("Productos de " + negocio).child(producto).child("Cantidad").setValue(numeroejemplares);
                    databaseReference.child(negocio).child("Productos de " + negocio).child(producto).child("Descripción").setValue(descripcion);
                    finish();
                    Toast.makeText(crear_nuevo_producto.this, "Producto añadido con éxito", Toast.LENGTH_LONG).show();
                    Intent acceso = new Intent(crear_nuevo_producto.this, Dueno.class);
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
