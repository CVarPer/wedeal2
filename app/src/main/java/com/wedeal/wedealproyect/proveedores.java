package com.wedeal.wedealproyect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
public class proveedores extends Fragment {

    private ListView mListView;
    private List<modelo_negocio> mLista = new ArrayList<>();
    ListAdapter mAdapter;
    FloatingActionButton fab;
    Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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

        mListView = getView().findViewById(R.id.listView2);
        final SharedPreferences pref = mContext.getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");
        final String Usuario = pref.getString("Usuario","");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        assert Negocio != null;
        databaseReference.child(Negocio.replace(".", "")).child("Proveedores de "+Negocio.replace(".", "")).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    final String nombre = objSnapshot.child("Nombre").getValue(String.class);
                    String telefono = objSnapshot.child("Teléfono").getValue(String.class);
                    String direccion = objSnapshot.child("Dirección").getValue(String.class);

                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("12345", nombre);
                    edit.apply();

                    mLista.add(new modelo_negocio(nombre,direccion,telefono,R.drawable.proveedores));
                    mAdapter = new CustomAdapter_Negocios(requireActivity().getApplicationContext(), R.layout.elemento_listas_negocios,mLista);

                    mListView.setAdapter(mAdapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                            TextView prov = getActivity().findViewById(R.id.nombre_neg);
                            Intent intent = new Intent(getActivity(), negocio_compras.class);
                            //Intent intent = new Intent(proveedores.this, negocio_compras.class);
                            intent.putExtra("proveedor", mLista.get(i).getNombre());

                            requireActivity().startActivity(intent);
                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror){

            }

        });

        fab = getView().findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), crear_nuevo_proveedor.class);
                requireActivity().startActivity(intent);
            }
        });

    }

}