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


public class CustomAdapter_Negocios extends ArrayAdapter<modelo_negocio> {

    private List<modelo_negocio> mList;
    private Context mContext;
    private int resourceLayout;

    public CustomAdapter_Negocios(@NonNull Context context, int resource, List<modelo_negocio> objects) {
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

        modelo_negocio modelo = mList.get(position);

        ImageView imgs = view.findViewById((R.id.foto_prov));
        imgs.setImageResource(modelo.getImgs());

        TextView textoNombre = view.findViewById((R.id.nombre_neg));
        textoNombre.setText(modelo.getNombre());

        TextView textoTelefono = view.findViewById((R.id.tel_neg));
        String tel = "Teléfono: "+modelo.getTelefono();
        textoTelefono.setText(tel);

        TextView textoDireccion = view.findViewById((R.id.direcc_neg));
        String direc = "Dirección: "+modelo.getDireccion();
        textoDireccion.setText(direc);

        return view;

    }
}