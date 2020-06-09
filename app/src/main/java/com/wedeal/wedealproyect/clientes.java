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
public class clientes extends Fragment {
    ListView listView;
    CustomAdapter_Clientes CustomAdapter_Clientes;
    FloatingActionButton fab;
    private String[][]  info_cliente = {
            {"Camilo Romero", "3146661248", "1000372988", "5"},
            {"Sebastián Arango", "3158662830", "51743448", "3"},
            {"María Jesusa", "3183976980 ", "270156370", "1"}
    };

    private int[] foto_cliente = {R.drawable.cliente_generico,R.drawable.cliente_generico,R.drawable.cliente_generico};

    public clientes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clientes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = requireView().findViewById(R.id.listView);
        CustomAdapter_Clientes = new CustomAdapter_Clientes(requireActivity().getApplicationContext(), info_cliente, foto_cliente);
        listView.setAdapter(CustomAdapter_Clientes);
        fab = requireView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(),crear_nuevo_cliente.class);
                requireActivity().startActivity(intent);
            }
        });

    }
}
