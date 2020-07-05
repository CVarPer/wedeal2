package com.wedeal.wedealproyect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class informes_Fragment extends Fragment {

    Context mContext;
    private ListView listView;
    private List<modelo_Informes> listaInfor = new ArrayList<>();
    private CustomAdapter_Informes customAdapter_informes;
    int informes;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informes, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Date d = new Date();
        final String dia = (String) DateFormat.format("dd", d.getTime());
        final String mes = (String) DateFormat.format("MMMM", d.getTime());

        listView = getView().findViewById(R.id.listView_Informes);
        SharedPreferences pref = mContext.getSharedPreferences("Registro", 0);
        final String negocio = pref.getString("Negocio", "").replace(".", "");

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(negocio).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Informes de " + mes).exists() && snapshot.child("Informes de " + mes).child("Gastos compras").exists() && snapshot.child("Encargos").exists()
                        && snapshot.child("Productos vendidos").exists()) {


                    String salarios = snapshot.child("Información").child("Salarios").getValue().toString();
                    String gastosProv = snapshot.child("Informes de " + mes).child("Gastos compras").getValue().toString();
                    String totalGastos = String.valueOf(Integer.parseInt(salarios) + Integer.parseInt(gastosProv));

                    String VentasTotales = snapshot.child("Informes de " + mes).child("Total ventas").getValue(String.class);
                    String promedioVentas = String.valueOf(Integer.parseInt(VentasTotales) / Integer.parseInt(dia));

                    String gananciasTotales = String.valueOf(Integer.parseInt(VentasTotales) - Integer.parseInt(totalGastos));


                    listaInfor.add(new modelo_Informes
                            (mes, gastosProv, salarios, totalGastos, promedioVentas, VentasTotales, gananciasTotales));
                }
                customAdapter_informes = new CustomAdapter_Informes(requireActivity(), informes, listaInfor);
                listView.setAdapter(customAdapter_informes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
