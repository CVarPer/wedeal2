package com.wedeal.wedealproyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Proveedores extends Fragment {
    public String[][] info_proveedor = {
            {"Dunder Mifflin", "7451321", "dundermifflin@burgoña.com", "Scranton", "Resma Papel"},
            {"El Brasero Rojo", "3158662830", "braserorojo@pollo.com", "Quirigua ", "Pollo asado"},
            {"Panamericana", "3183976980 ", "panamericana@panamericana.com", "Carrera 7 #75-23", "Útiles escolares"}
    };
    ListView listView;
    CustomAdapter_Proveedores CustomAdapter_Proveedores;
    FloatingActionButton fab;
    private int[] foto_proveedor = {R.drawable.proveedores, R.drawable.proveedores, R.drawable.proveedores};

    public Proveedores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proveedores, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = requireView().findViewById(R.id.listView_prov);
        CustomAdapter_Proveedores = new CustomAdapter_Proveedores(requireActivity().getApplicationContext(), info_proveedor, foto_proveedor);
        listView.setAdapter(CustomAdapter_Proveedores);
        fab = requireView().findViewById(R.id.fab_prov);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), crear_nuevo_proveedor.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
