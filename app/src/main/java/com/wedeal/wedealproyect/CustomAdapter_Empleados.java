package com.wedeal.wedealproyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter_Empleados extends BaseAdapter {

    //Aquí podemos instanciar el xml con la plantilla para los elementos del listview
    LayoutInflater inflater = null;
    Context contexto;
    String[][] datos_empl;
    int[] imgs;

    public CustomAdapter_Empleados(Context contexto, String[][] datos_empl, int[] imgs) {
        this.contexto = contexto;
        this.datos_empl = datos_empl;
        this.imgs = imgs;
        //Inicializamos el inflater. Es necesario hacer un cast a LayoutInflater
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE); //LAYOUT_INFLATER_SERVICE instancia el xml
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.elemento_listas, null);
        TextView nombretxt = view.findViewById(R.id.nombre_empl);
        TextView telefonotxt = view.findViewById(R.id.tel_empl);
        TextView rol = view.findViewById(R.id.rol);
        TextView salariotxt = view.findViewById(R.id.salario_empl);
        ImageView foto = view.findViewById(R.id.foto_empl);
        //Designamos el contenido que aparecerá en cada coso xd
        nombretxt.setText(datos_empl[position][0]);
        telefonotxt.setText("Teléfono: "+datos_empl[position][1]);
        rol.setText("Rol: "+datos_empl[position][2]);
        salariotxt.setText("Salario: "+datos_empl[position][3]);
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

