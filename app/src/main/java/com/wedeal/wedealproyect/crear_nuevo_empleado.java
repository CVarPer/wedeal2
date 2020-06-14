package com.wedeal.wedealproyect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class crear_nuevo_empleado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_empleado);
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
    }
}