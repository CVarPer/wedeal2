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

public class CustomAdapter_Informes extends ArrayAdapter<modelo_Informes> {

    private List<modelo_Informes> mList;
    private LayoutInflater thisInflater;

    public CustomAdapter_Informes(@NonNull Context context, int resource, List<modelo_Informes> objects) {
        super(context, resource, objects);
        this.mList = objects;
        this.thisInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = thisInflater.inflate(R.layout.elemento_listas_informes, parent, false);
        }


        modelo_Informes modelo = mList.get(position);

        TextView txtMes = view.findViewById((R.id.info_mes));
        String reportes = "Reportes de " + modelo.getMes();
        txtMes.setText(reportes);

        TextView txtPagos = view.findViewById((R.id.pagos_prov));
        String pagos = "Pagos a proveedores: $" + modelo.getPagos();
        txtPagos.setText(pagos);

        TextView SalarioTxt = view.findViewById(R.id.pagos_salarios);
        String salario = "Pago a empleados: $" + modelo.getSalario();
        SalarioTxt.setText(salario);

        TextView GastTotTxt = view.findViewById((R.id.gastos_total));
        String gastotal = "Gastos totales: $" + modelo.getGastosTotal();
        GastTotTxt.setText(gastotal);

        TextView promVentas = view.findViewById((R.id.prom));
        String prom = "Promedio ganancias diarias: $" + modelo.getPromVentas();
        promVentas.setText(prom);

        TextView ganBrut = view.findViewById(R.id.ganancias_bruto);
        //ganBrutas = Integer.parseInt(modelo.getGanBrut())*Integer.parseInt(modelo.getPrecio());
        String Brutas = "Ganancias en bruto: $" + modelo.getGanBrut();
        ganBrut.setText(Brutas);

        TextView GanTotal = view.findViewById(R.id.totaltxt);
        String totalGanancias = "Ganancias totales: $" + modelo.getGanTotal();
        GanTotal.setText(totalGanancias);


        return view;

    }

}
