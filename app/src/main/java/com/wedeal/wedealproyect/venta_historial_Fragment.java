package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import java.util.Date;
import java.util.List;

public class venta_historial_Fragment extends Fragment {
    FloatingActionButton fab;
    private ListView listView;
    private List<modelo_producto_vendido> lista_ventas = new ArrayList<>();
    ListAdapter adapter;
    CustomAdapter_GridView_Productos_vendidos customAdapterGridViewProductos2;
    int info_producto;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ventas, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = getView().findViewById(R.id.listView_Ventas);
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "").replace(".", "");
        Date d = new Date();
        final String mes = (String) DateFormat.format("MMMM", d.getTime());
        final DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference();
        DbRef.child(Negocio).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Productos vendidos")) {
                    DbRef.child(Negocio).child("Productos vendidos").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                String nombre = snapshot.child("Nombre").getValue(String.class);
                                String codigo = snapshot.child("Código").getValue(String.class);
                                final String precio = snapshot.child("Precio").getValue(String.class);
                                final String Cantidad = snapshot.child("Stock").getValue(String.class);
                                String fecha = snapshot.child("Fecha").getValue(String.class);


                                lista_ventas.add(new modelo_producto_vendido(codigo, nombre, precio, Cantidad, fecha));

                            }

                            customAdapterGridViewProductos2 = new CustomAdapter_GridView_Productos_vendidos(requireActivity(),info_producto, lista_ventas);
                            listView.setAdapter(customAdapterGridViewProductos2);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab = getView().findViewById(R.id.fabVentas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), venta.class);
                requireActivity().startActivity(intent);
            }
        });

    }
}