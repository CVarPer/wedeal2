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
public class empleados extends Fragment {
    FloatingActionButton fab;
    ListView listView;
    CustomAdapter_Empleados customAdapterEmpleados;
    private String[][] info_empleado = {
            {"Fausto", "3116214856","Cajero","$899,500"},
            {"Alicia", "3158662830","Gerente", "1200,000"},
            {"Diana", "3183976980 ","Asesor en tienda", "$360,000"}

    };
    private int[] foto_empl = {R.drawable.empleado_ej1,R.drawable.empleada_ej2,R.drawable.empleada_ej2};

    public empleados() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empleados, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getView().findViewById(R.id.listView);
        customAdapterEmpleados = new CustomAdapter_Empleados(getActivity().getApplicationContext(), info_empleado, foto_empl);
        listView.setAdapter(customAdapterEmpleados);
        fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(),crear_nuevo_empleado.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
