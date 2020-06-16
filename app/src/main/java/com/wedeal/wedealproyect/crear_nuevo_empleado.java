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

public class crear_nuevo_empleado extends MainActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_empleado);
        Button registro_2 = findViewById(R.id.registrarempleado);

        neg_log = (EditText) findViewById(R.id.neg);
        inicializarFirebase();


        registro_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String emp = ((EditText) findViewById(R.id.usuarioempleado)).getText().toString().trim();
                String passemp = ((EditText) findViewById(R.id.contraempleado)).getText().toString().trim();

                neg_log = (EditText) findViewById(R.id.neg);
                String negocio = neg_log.getText().toString().trim();

                String confpassemp = ((EditText) findViewById(R.id.confir_contraempleado)).getText().toString().trim();

                if(emp.length() <= 0){
                    Toast.makeText(crear_nuevo_empleado.this, "Ingrese un correo válido", Toast.LENGTH_LONG).show();
                }
                else if (passemp.length() < 5){
                    Toast.makeText(crear_nuevo_empleado.this, "Su contraseña debe tener 5 o más dígitos", Toast.LENGTH_LONG).show();
                }
                else if (confpassemp.length() < 5){
                    Toast.makeText(crear_nuevo_empleado.this, "Confirme su contraseña", Toast.LENGTH_LONG).show();
                }

                else if (passemp.equals(confpassemp)){
                    databaseReference.child(negocio).child("Usuarios de " + negocio).child(emp.replace(".","")).child("Usuario").setValue(emp.replace(".",""));
                    databaseReference.child(negocio).child("Usuarios de " + negocio).child(emp.replace(".","")).child("Contraseña").setValue(passemp);
                    databaseReference.child(negocio).child("Usuarios de " + negocio).child(emp.replace(".","")).child("Permisos").setValue("False");

                    finish();
                    Toast.makeText(crear_nuevo_empleado.this, "Empleado registrado con éxito", Toast.LENGTH_LONG).show();
                    Intent acceso = new Intent(crear_nuevo_empleado.this, Dueno.class);
                    startActivity(acceso);
                }
                else{
                    Toast.makeText(crear_nuevo_empleado.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
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
