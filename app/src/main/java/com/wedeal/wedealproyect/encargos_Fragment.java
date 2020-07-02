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
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class encargos_Fragment extends Fragment {

    private ListView mListView;
    private List<modelo_encargo> mLista = new ArrayList<>();
    ListAdapter mAdapter;
    boolean b = true;
    int suma = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_encargos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = getView().findViewById(R.id.listView4);
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "").replace(".","");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();

        /*try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        databaseReference.child(Negocio).child("Encargos").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    final String estado = objSnapshot.child("Info").child("Estado").getValue(String.class);
                    final String fecha = objSnapshot.child("Info").child("Fecha").getValue(String.class);
                    final String proveedor = objSnapshot.child("Info").child("Nombre").getValue(String.class);


                    databaseReference2.child(Negocio).child("Encargos").child("Solicitud a "+proveedor).child("Productos").addListenerForSingleValueEvent(new ValueEventListener() {
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

                                mLista.add(new modelo_encargo(estado,fecha,Valor_Compra,proveedor));

                                mAdapter = new CustomAdapter_Encargos(requireActivity().getApplicationContext(), R.layout.elemento_listas_encargos,mLista);

                                mListView.setAdapter(mAdapter);

                                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                        Intent intent = new Intent(getActivity(), encargos_vistaproductos.class);
                                        //Intent intent = new Intent(proveedores.this, negocio_compras.class);
                                        intent.putExtra("proveedor", proveedor);
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
