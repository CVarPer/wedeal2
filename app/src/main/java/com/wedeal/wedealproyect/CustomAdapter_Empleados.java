package com.wedeal.wedealproyect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter_Empleados extends ArrayAdapter<Modelo> {

    private List<Modelo> mList;
    private Context mContext;
    private int ressourceLayout;

    public CustomAdapter_Empleados(@NonNull Context context, int resource, List<Modelo> objects) {
        super(context, resource, objects);
        this.mList = objects;
        this.mContext = context;
        this.ressourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(ressourceLayout,null);

        Modelo modelo = mList.get(position);

        ImageView imgs = view.findViewById((R.id.foto_empl));
        imgs.setImageResource(modelo.getImgs());

        TextView textoNombre = view.findViewById((R.id.nombre_empl));
        textoNombre.setText(modelo.getNombre());

        TextView textoTelefono = view.findViewById((R.id.tel_empl));
        textoTelefono.setText(modelo.getTelefono());

        TextView textoSalario = view.findViewById((R.id.salario_empl));
        textoSalario.setText(modelo.getSalario());

        TextView textoCargo = view.findViewById((R.id.rol));
        textoCargo.setText(modelo.getCargo());

        return view;

    }
}