package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usuario_log, contralog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario_log = (EditText) findViewById(R.id.email);
        contralog = (EditText) findViewById(R.id.password);
        Button registro = findViewById(R.id.registro);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selec_usuario = new Intent(MainActivity.this, registro_usuario.class);
                startActivity(selec_usuario);
            }
        });
        Button ingreso = findViewById(R.id.ingreso);
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("Registro", 0);
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
                }

            }
        });
    }


}
