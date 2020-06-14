package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class empleados extends Fragment {
    FloatingActionButton fab;

    private ListView mListView;
    private List<Modelo> mLista = new ArrayList<>();
    ListAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empleados, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = getView().findViewById(R.id.listView);
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        String Negocio = pref.getString("Negocio", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child(Negocio).child("Usuarios de " + Negocio).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {


                    if (objSnapshot.child("Permisos").getValue().toString().equals("Empleado")) {
                        String nombre = objSnapshot.child("Nombre").getValue().toString();
                        String telefono = objSnapshot.child("Teléfono").getValue().toString();
                        String cargo = objSnapshot.child("Cargo").getValue().toString();
                        String salario = objSnapshot.child("Salario").getValue().toString();


                        mLista.add(new Modelo(nombre, cargo, telefono, salario, R.drawable.empleado_ej1));

                        //Toast.makeText(requireActivity().getApplicationContext(), "hola"+mLista, Toast.LENGTH_SHORT).show();
                        mAdapter = new CustomAdapter_Empleados(requireActivity().getApplicationContext(), R.layout.elemento_listas, mLista);

                        mListView.setAdapter(mAdapter);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }

        });


        fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), crear_nuevo_empleado.class);
                getActivity().startActivity(intent);
            }
        });

    }


}