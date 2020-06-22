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


public class CustomAdapter_GridView_Productos extends ArrayAdapter<modelo_producto> {

    private List<modelo_producto> mList;
    private Context Context;
    private int resourceLayout;
    private LayoutInflater thisInflater;

    public CustomAdapter_GridView_Productos(@NonNull Context context, int resource, List<modelo_producto> objects) {
        super(context, resource, objects);
        this.mList = objects;
        this.Context = context;
        this.resourceLayout = resource;
        this.thisInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = thisInflater.inflate( R.layout.elemento_lista_producto, parent, false );
        }


        modelo_producto modelo = mList.get(position);

        ImageView imgs = view.findViewById((R.id.foto_producto));
        imgs.setImageResource(modelo.getFotoProd());

        TextView textoCodigo = view.findViewById((R.id.codigo_prod));
        textoCodigo.setText(modelo.getCodigo());

        TextView textoNombre = view.findViewById((R.id.nombre_producto));
        String prod = "Producto " + modelo.getNombre();
        textoNombre.setText(prod);

        TextView textoPrecio = view.findViewById((R.id.precio_producto));
        String precio = "Precio venta: $" + modelo.getPrecio();
        textoPrecio.setText(precio);

        TextView textoStock = view.findViewById((R.id.stock_producto));
        String stock = "Cargo: " + modelo.getStock();
        textoStock.setText(stock);

        return view;

    }
}