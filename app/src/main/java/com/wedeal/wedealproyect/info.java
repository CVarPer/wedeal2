package com.wedeal.wedealproyect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class info extends Fragment {
    static TextView nombre_negocio, direccion_negocio, telefono_negocio;
    private DatabaseReference databaseReference;


    public info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nombre_negocio = getView().findViewById(R.id.nombre_negocio);
        direccion_negocio = getView().findViewById(R.id.direccion_negocio);
        telefono_negocio = getView().findViewById(R.id.telefono_negocio);

        //Nodo principal de la base de datos
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        String Negocio = pref.getString("Negocio", "");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Negocio).child("Información negocio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("Nombre").getValue().toString();
                    nombre_negocio.setText(nombre);
                    String direccion = dataSnapshot.child("Dirección").getValue().toString();
                    direccion_negocio.setText(direccion);
                    String telefono = dataSnapshot.child("Teléfono").getValue().toString();
                    telefono_negocio.setText(telefono);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
