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


public class CustomAdapter_Encargos extends ArrayAdapter<modelo_encargo> {

    private List<modelo_encargo> mList;
    private Context mContext;
    private int resourceLayout;

    public CustomAdapter_Encargos(@NonNull Context context, int resource, List<modelo_encargo> objects) {
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

        modelo_encargo modelo = mList.get(position);

        TextView textoNombre = view.findViewById((R.id.nombre_proveedor));
        String nombre = "Solicitud a:  "+modelo.getNombre();
        textoNombre.setText(nombre);

        TextView textoFecha = view.findViewById((R.id.fecha));
        String fecha = "Solicitud enviada el:  "+modelo.getFecha();
        textoFecha.setText(fecha);

        TextView textoEstado = view.findViewById((R.id.estado));
        String estado = "Estado del encargo:  "+modelo.getEstado();
        textoEstado.setText(estado);

        TextView textoValor_compra = view.findViewById((R.id.totalcompra));
        String valor_compra = "Valor del encargo:  $"+modelo.getValor_Compra();
        textoValor_compra.setText(valor_compra);


        return view;

    }
}