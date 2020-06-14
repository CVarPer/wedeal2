package com.wedeal.wedealproyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class CustomAdapter_Empleados extends ArrayAdapter<Modelo> {

    private List<Modelo> mList;
    private Context mContext;
    private int resourceLayout;

    public CustomAdapter_Empleados(@NonNull Context context, int resource, List<Modelo> objects) {
        super(context, resource, objects);
        this.mList = objects;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(mContext).inflate(resourceLayout, null);

        Modelo modelo = mList.get(position);

        ImageView imgs = view.findViewById((R.id.foto_empl));
        imgs.setImageResource(modelo.getImgs());

        TextView textoNombre = view.findViewById((R.id.nombre_empl));
        textoNombre.setText(modelo.getNombre());

        TextView textoTelefono = view.findViewById((R.id.tel_empl));
        String tel = "Teléfono: " + modelo.getTelefono();
        textoTelefono.setText(tel);

        TextView textoSalario = view.findViewById((R.id.salario_empl));
        String salario = "Salario: " + modelo.getSalario();
        textoSalario.setText(salario);

        TextView textoCargo = view.findViewById((R.id.rol));
        String cargo = "Cargo: " + modelo.getCargo();
        textoCargo.setText(cargo);

        return view;

    }
}