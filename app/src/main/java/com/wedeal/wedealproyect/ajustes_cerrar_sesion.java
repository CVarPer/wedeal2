package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ajustes_cerrar_sesion extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("Registro",0);

        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();


        finish();
        Toast.makeText(ajustes_cerrar_sesion.this, "Sesión cerrada con éxito", Toast.LENGTH_LONG).show();
        Intent acceso = new Intent(ajustes_cerrar_sesion.this, MainActivity.class);
        startActivity(acceso);
    }
}


