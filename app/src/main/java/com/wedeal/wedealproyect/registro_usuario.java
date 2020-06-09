package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registro_usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Button registro_2 = findViewById(R.id.registrarse);
        registro_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("Registro",0);
                String usuario = ((EditText) findViewById(R.id.registro_user)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.contra)).getText().toString().trim();
                String confpass = ((EditText) findViewById(R.id.confir_contra)).getText().toString().trim();

                if(usuario.length() <= 0){
                    Toast.makeText(registro_usuario.this, "Ingrese un correo válido", Toast.LENGTH_LONG).show();
                }
                else if (password.length() <=0){
                    Toast.makeText(registro_usuario.this, "Ingrese una contraseña válida", Toast.LENGTH_LONG).show();
                }
                else if (confpass.length() <=0){
                    Toast.makeText(registro_usuario.this, "Confirme su contraseña", Toast.LENGTH_LONG).show();
                }
                else if (password.equals(confpass)){
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("Usuario",usuario);
                    edit.putString("Contraseña",password);
                    edit.putString("Confirmación de contraseña",confpass);
                    edit.apply();

                    finish();
                    Toast.makeText(registro_usuario.this, "Usuario creado con éxito", Toast.LENGTH_LONG).show();
                    Intent acceso = new Intent(registro_usuario.this, Dueno.class);
                    startActivity(acceso);
                }
                else{
                    Toast.makeText(registro_usuario.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
