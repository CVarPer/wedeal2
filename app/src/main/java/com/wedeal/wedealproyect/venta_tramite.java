package com.wedeal.wedealproyect;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wedeal.wedealproyect.venta;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class venta_tramite extends venta {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DatabaseReference pDatabase = FirebaseDatabase.getInstance().getReference();
        String negocio = getIntent().getStringExtra("negocio");
        String nombre = getIntent().getStringExtra("nombre");
        String f = getIntent().getStringExtra("f");

        Toast.makeText(venta_tramite.this, "Tiene "+f+" "+nombre+" en su carro de compras", Toast.LENGTH_LONG).show();


        pDatabase.child(negocio).child("Productos en trámite").child(nombre).child("Stock").setValue(f);

        Intent intent = new Intent(venta_tramite.this, venta.class);
        startActivity(intent);

    }
}