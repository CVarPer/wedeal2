package com.wedeal.wedealproyect;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class in_frag_inventario_Fragment extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new inventario_Fragment()).commit();}
    }
}