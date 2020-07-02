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


public class CustomAdapter_Clientes extends ArrayAdapter<modelo_cliente> {

    private List<modelo_cliente> mList;
    private Context mContext;
    private int resourceLayout;

    public CustomAdapter_Clientes(@NonNull Context context, int resource, List<modelo_cliente> objects) {
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

        modelo_cliente modelo = mList.get(position);

        ImageView imgs = view.findViewById((R.id.foto_empl));
        imgs.setImageResource(modelo.getImgs());

        TextView textoNombre = view.findViewById((R.id.nombre_empl));
        textoNombre.setText(modelo.getNombre());

        TextView textoTelefono = view.findViewById((R.id.tel_empl));
        String tel = "Teléfono: " + modelo.getTelefono();
        textoTelefono.setText(tel);

        TextView textoTipo_Cliente = view.findViewById((R.id.salario_empl));
        String salario = "Tipo de cliente: " + modelo.getTipo_Cliente();
        textoTipo_Cliente.setText(salario);

        TextView textoNm_Compras = view.findViewById((R.id.rol));
        String cargo = "Número de compras: " + modelo.getNm_Compras();
        textoNm_Compras.setText(cargo);

        return view;

    }
}
