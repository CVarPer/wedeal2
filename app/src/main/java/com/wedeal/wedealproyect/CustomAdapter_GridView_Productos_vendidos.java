package com.wedeal.wedealproyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class CustomAdapter_GridView_Productos_vendidos extends ArrayAdapter<modelo_producto_vendido> {

    private List<modelo_producto_vendido> mList;
    private LayoutInflater thisInflater;

    public CustomAdapter_GridView_Productos_vendidos(@NonNull Context context, int resource, List<modelo_producto_vendido> objects) {
        super(context, resource,objects);
        this.mList = objects;
        this.thisInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = thisInflater.inflate( R.layout.elemento_listas_ventas, parent, false );
        }


        modelo_producto_vendido modelo = mList.get(position);

        TextView textoCodigo = view.findViewById((R.id.codigoProducto));
        textoCodigo.setText(modelo.getCodigo());

        TextView textoNombre = view.findViewById((R.id.nombre_prod));
        String prod = modelo.getNombre();
        textoNombre.setText(prod);

        TextView fechatxt = view.findViewById(R.id.fecha_venta);
        String fecha = modelo.getFecha();
        fechatxt.setText(fecha);

        TextView textoPrecio = view.findViewById((R.id.precio_unidad));
        String precio = "Precio venta: $" + modelo.getPrecio();
        textoPrecio.setText(precio);

        TextView textoStock = view.findViewById((R.id.Cantidad));
        String stock = "Unidades vendidas: " + modelo.getStock();
        textoStock.setText(stock);

        TextView totalCompra = view.findViewById(R.id.totaltxt);
        int total = Integer.parseInt(modelo.getStock())*Integer.parseInt(modelo.getPrecio());
        String totalConv = "Total venta: "+ total;
        totalCompra.setText(totalConv);

        return view;

    }
}