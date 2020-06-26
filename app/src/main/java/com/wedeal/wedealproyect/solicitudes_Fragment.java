package com.wedeal.wedealproyect;

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
public class solicitudes_Fragment extends Fragment {

    private ListView mListView;
    private List<modelo_solicitud_cliente> mLista = new ArrayList<>();
    ListAdapter mAdapter;
    boolean b = true;
    int suma = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solicitudes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = getView().findViewById(R.id.listView3);
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();


        databaseReference.child(Negocio).child("Solicitudes").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    final String nombre = objSnapshot.child("Info").child("Cliente").getValue().toString();
                    final String telefono = objSnapshot.child("Info").child("Teléfono").getValue().toString();
                    final String Tipo_Cliente = objSnapshot.child("Info").child("Tipo de Cliente").getValue().toString();

                    databaseReference2.child(Negocio).child("Solicitudes").child("Solicitud de "+nombre).child("Productos").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot objSnapshot2 : dataSnapshot.getChildren()){
                                int precio = Integer.parseInt(objSnapshot2.child("Precio").getValue().toString());
                                int unidades = Integer.parseInt(objSnapshot2.child("Stock").getValue().toString());
                                int d = precio * unidades;

                                suma = suma + d;
                                String Valor_Compra = String.valueOf(suma);

                                if (mLista.size() > 0){
                                    mLista.remove(0);
                                }

                                mLista.add(new modelo_solicitud_cliente(telefono,Tipo_Cliente,Valor_Compra,nombre,R.drawable.empleado_ej1));


                                Toast.makeText(requireActivity().getApplicationContext(), "hola"+mLista, Toast.LENGTH_SHORT).show();
                                mAdapter = new CustomAdapter_Solicitudes(requireActivity().getApplicationContext(), R.layout.elemento_listas_solicitudes_clientes,mLista);

                                mListView.setAdapter(mAdapter);

                                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                        Intent intent = new Intent(getActivity(), solicitudes_confirmar.class);
                                        //Intent intent = new Intent(proveedores.this, negocio_compras.class);
                                        intent.putExtra("cliente", nombre);
                                        requireActivity().startActivity(intent);
                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror){

            }

        });

    }


}
