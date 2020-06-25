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


public class CustomAdapter_Solicitudes extends ArrayAdapter<modelo_solicitud_cliente> {

    private List<modelo_solicitud_cliente> mList;
    private Context mContext;
    private int resourceLayout;

    public CustomAdapter_Solicitudes(@NonNull Context context, int resource, List<modelo_solicitud_cliente> objects) {
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

        modelo_solicitud_cliente modelo = mList.get(position);

        ImageView foto = view.findViewById((R.id.foto));
        foto.setImageResource(modelo.getFotoProd());

        TextView textoNombre = view.findViewById((R.id.nombre_cliente));
        textoNombre.setText(modelo.getNombre());

        TextView textoTipo_Cliente = view.findViewById((R.id.tipo_cliente));
        textoTipo_Cliente.setText("Tipo de cliente: "+modelo.getTipo_Cliente());

        TextView textoValor_Compra = view.findViewById((R.id.valor_compra));
        textoValor_Compra.setText("Valor de la compra: $"+modelo.getValor_Compra());

        TextView textoTelefono = view.findViewById((R.id.tel));
        textoTelefono.setText("Teléfono: "+modelo.getTelefono());


        return view;

    }
}