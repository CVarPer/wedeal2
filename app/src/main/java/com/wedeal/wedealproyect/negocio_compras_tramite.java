package com.wedeal.wedealproyect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class negocio_compras_tramite extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DatabaseReference pDatabase = FirebaseDatabase.getInstance().getReference();
        String proveedor = getIntent().getStringExtra("proveedor");
        String negocio = getIntent().getStringExtra("negocio");
        String nombre = getIntent().getStringExtra("nombre");
        String f = getIntent().getStringExtra("f");

        Toast.makeText(negocio_compras_tramite.this, "Tiene " + f + " " + nombre + " en su carro de compras", Toast.LENGTH_SHORT).show();


        assert negocio != null;
        assert nombre != null;
        pDatabase.child(negocio).child("Solicitud a " + proveedor).child(nombre).child("Stock").setValue(f);

        Intent intent = new Intent(negocio_compras_tramite.this, negocio_compras.class);
        intent.putExtra("proveedor", proveedor);
        startActivity(intent);

    }
}