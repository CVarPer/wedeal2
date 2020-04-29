package com.wedeal.wedealproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button registro = findViewById(R.id.registro);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selec_usuario = new Intent(MainActivity.this,activity_escogerusuario.class);
                startActivity(selec_usuario);


            }
        });
        /*
        Button ingreso = findViewById(R.id.iniciar_sesion);
            ingreso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String correo = ((EditText) findViewById(R.id.email)).getText().toString();
                    String password = ((EditText) findViewById(R.id.contra)).getText().toString();

                    if(correo.equals("chvargasp@unal.edu.co") && password.equals("123")){
                        Toast.makeText(getApplicationContext(), "Usted ha ingresado con éxito", Toast.LENGTH_LONG).show();
                        Intent selec_usuario = new Intent(MainActivity.this, seleccion.class);
                        startActivity(selec_usuario);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }

                }
            });
             */
    }
}
