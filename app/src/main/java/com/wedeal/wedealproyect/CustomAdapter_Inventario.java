package com.wedeal.wedealproyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class CustomAdapter_Inventario extends BaseAdapter {

    //Aquí podemos instanciar el xml con la plantilla para los elementos del listview
    LayoutInflater inflater = null;
    Context contexto;
    String[][] datos_empl;
    int[] imgs;

    public CustomAdapter_Inventario(Context contexto, String[][] datos_empl, int[] imgs) {
        this.contexto = contexto;
        this.datos_empl = datos_empl;
        this.imgs = imgs;
        //Inicializamos el inflater. Es necesario hacer un cast a LayoutInflater
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE); //LAYOUT_INFLATER_SERVICE instancia el xml
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.elemento_listas_empleados, null);

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

