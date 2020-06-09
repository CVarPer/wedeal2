package com.wedeal.wedealproyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomAdapter_Clientes extends BaseAdapter {

    //Aquí podemos instanciar el xml con la plantilla para los elementos del listview
    LayoutInflater inflater;
    Context contexto;
    String[][] datos_clients;
    int[] imgs;

    public CustomAdapter_Clientes(Context contexto, String[][] datos_clients, int[] imgs) {
        this.contexto = contexto;
        this.datos_clients = datos_clients;
        this.imgs = imgs;
        //Inicializamos el inflater. Es necesario hacer un cast a LayoutInflater
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE); //LAYOUT_INFLATER_SERVICE instancia el xml
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.elemento_listas_clientes,null);
        TextView nombretxt = view.findViewById(R.id.nombre_cliente);
        TextView telefonotxt = view.findViewById(R.id.tel_client);
        TextView cedula = view.findViewById(R.id.cedula);
        TextView compras = view.findViewById(R.id.num_compras);
        ImageView foto = view.findViewById(R.id.foto_cliente);
        //Designamos el contenido que aparecerá en cada coso xd
        nombretxt.setText(datos_clients[position][0]);
        telefonotxt.setText("Teléfono: "+datos_clients[position][1]);
        cedula.setText("Cédula: "+datos_clients[position][2]);
        compras.setText("Compras: "+datos_clients[position][3]);
        //Foto de cada empleado
        foto.setImageResource(imgs[position]);
        foto.setTag(position);

        /*foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verFoto = new Intent(contexto, VerFoto.class);
                verFoto.putExtra("IMG",imgs[(Integer)v.getTag()]);
            }
        });*/

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
