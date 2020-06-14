package com.wedeal.wedealproyect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter_Proveedores extends BaseAdapter {
    LayoutInflater inflater;
    Context contexto;
    String[][] datos_prov;
    int[] imgs;

    public CustomAdapter_Proveedores(Context contexto, String[][] datos_prov, int[] imgs) {
        this.contexto = contexto;
        this.datos_prov = datos_prov;
        this.imgs = imgs;
        //Inicializamos el inflater. Es necesario hacer un cast a LayoutInflater
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE); //LAYOUT_INFLATER_SERVICE instancia el xml
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.elemento_listas_proveedores, null);
        TextView nombretxt = view.findViewById(R.id.nombre_prov);
        TextView telefonotxt = view.findViewById(R.id.tel_prov);
        TextView correotxt = view.findViewById(R.id.correo_prov);
        TextView direccion = view.findViewById(R.id.direccion_proveedor);
        TextView productostxt = view.findViewById(R.id.Productos);
        ImageView foto = view.findViewById(R.id.foto_prov);
        //Designamos el contenido que aparecerá en cada coso xd
        nombretxt.setText(datos_prov[position][0]);
        telefonotxt.setText("Teléfono: " + datos_prov[position][1]);
        correotxt.setText("Correo" + datos_prov[position][2]);
        direccion.setText("Dirección: " + datos_prov[position][3]);
        productostxt.setText("Productos: " + datos_prov[position][4]);
        //Foto de cada empleado
        foto.setImageResource(imgs[position]);
        foto.setTag(position);
        return view;
    }


    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
