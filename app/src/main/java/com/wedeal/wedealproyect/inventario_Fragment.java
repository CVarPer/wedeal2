package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import java.util.Objects;

public class inventario_Fragment extends Fragment {
    FloatingActionButton fab;


    private GridView gridView;
    CustomAdapter_GridView_Productos customAdapterGridViewProductos;
    private List<modelo_producto> info_productos = new ArrayList<>();
    int foto_producto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventario, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = requireView().findViewById(R.id.grid_view_image_text);
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        final String Negocio = pref.getString("Negocio", "");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Negocio).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Productos de " + Negocio)) {
                    databaseReference.child(Negocio).child("Productos de " + Negocio).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                                String codigo = objSnapshot.child("Código").getValue().toString();
                                String nombre = objSnapshot.child("Nombre").getValue().toString();
                                String precio = objSnapshot.child("Precio").getValue().toString();
                                String stock = objSnapshot.child("Stock").getValue().toString();

                                info_productos.add(new modelo_producto(codigo, nombre, precio, stock, R.drawable.product));

                                customAdapterGridViewProductos = new CustomAdapter_GridView_Productos
                                        (requireActivity().getApplicationContext(), foto_producto, info_productos);
                                gridView.setAdapter(customAdapterGridViewProductos);
                                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int i, long id) {
                                        Toast.makeText(getActivity().getApplicationContext(), "GridView Item: " + info_productos.get(+i), Toast.LENGTH_LONG).show();
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
            public void onCancelled(@NonNull DatabaseError databaseerror) {

            }


        });
        fab = getView().findViewById(R.id.fab_inventario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), crear_nuevo_producto.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
