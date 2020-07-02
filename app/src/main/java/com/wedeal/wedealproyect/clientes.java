package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class clientes extends Fragment {

    private ListView mListView;
    private List<modelo_cliente> mLista = new ArrayList<>();
    ListAdapter mAdapter;
    modelo_cliente modelo;
    FloatingActionButton faba;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mListView = getView().findViewById(R.id.listView4);
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        String Negocio = pref.getString("Negocio", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child(Negocio).child("Clientes de " + Negocio).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                        String nombre = objSnapshot.child("Nombre").getValue().toString();
                        String telefono = objSnapshot.child("Teléfono").getValue().toString();
                        String nm_compras = objSnapshot.child("Compras").getValue().toString();
                        String tipo_cliente = objSnapshot.child("Tipo de cliente").getValue().toString();

                        modelo = new modelo_cliente();
                        modelo.setTelefono(telefono);
                        modelo.setNombre(nombre);
                        modelo.setNm_Compras(nm_compras);
                        modelo.setTipo_Cliente(tipo_cliente);
                        modelo.setImgs(R.drawable.empleado_ej1);

                        mLista.add(modelo);

                        //Toast.makeText(requireActivity().getApplicationContext(), "hola"+mLista, Toast.LENGTH_SHORT).show();
                        mAdapter = new CustomAdapter_Clientes(getActivity(), R.layout.elemento_listas_empleados, mLista);

                        mListView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }

        });

        faba = getView().findViewById(R.id.fab4);
        faba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), crear_nuevo_cliente.class);
                getActivity().startActivity(intent);
            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clientes, container, false);
    }


}
