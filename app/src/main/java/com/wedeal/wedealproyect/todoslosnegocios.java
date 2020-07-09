package com.wedeal.wedealproyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class todoslosnegocios extends Fragment {

    private ListView mListView;
    private List<modelo_negocio> mLista = new ArrayList<>();
    ListAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todoslosnegocios, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = getView().findViewById(R.id.listView2);
        final SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        String Negocio = pref.getString("Negocio", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    String estatus = objSnapshot.child("Información").child("Negocio").getValue().toString();

                    if(estatus.equals("Si")){
                        String nombre = objSnapshot.child("Información").child("Nombre").getValue().toString();

                        String telefono = objSnapshot.child("Información").child("Teléfono").getValue().toString();
                        String direccion = objSnapshot.child("Información").child("Dirección").getValue().toString();

                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("123456", nombre);
                        edit.apply();

                        mLista.add(new modelo_negocio(nombre,direccion,telefono,R.drawable.negocio));
                        mAdapter = new CustomAdapter_Negocios(requireActivity().getApplicationContext(), R.layout.elemento_listas_negocios,mLista);

                    }

                }

                mListView.setAdapter(mAdapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        Intent intent = new Intent(getActivity(), negocio_compras.class);
                        //Intent intent = new Intent(proveedores.this, negocio_compras.class);
                        intent.putExtra("proveedor", mLista.get(i).getNombre());
                        requireActivity().startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror){

            }

        });

    }




}
