/*package com.wedeal.wedealproyect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter_Empleados extends BaseAdapter {

    //Aquí podemos instanciar el xml con la plantilla para los elementos del listview

    LayoutInflater inflater;
    Context contexto1;
    List<String> elemento_datos1;
    Integer imgs1;


    public CustomAdapter_Empleados(Context contexto, List<String> elemento_datos, Integer imgs) {
        this.contexto1 = contexto;
        this.elemento_datos1 = elemento_datos;
        this.imgs1 = imgs;
        //Inicializamos el inflater. Es necesario hacer un cast a LayoutInflater
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //LAYOUT_INFLATER_SERVICE instancia el xml
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.elemento_listas_empleados, null);

        TextView nombretxt = view.findViewById(R.id.nombre_empl);
        TextView telefonotxt = view.findViewById(R.id.tel_empl);
        TextView cargotxt = view.findViewById(R.id.rol);
        TextView salariotxt = view.findViewById(R.id.salario_empl);
        ImageView foto = view.findViewById(R.id.foto_empl);

        //Designamos el contenido que aparecerá en cada coso xd

        nombretxt.setText(elemento_datos1.get(0));
        telefonotxt.setText("Teléfono: " + elemento_datos1.get(1));
        cargotxt.setText("Cargo: " + elemento_datos1.get(2));
        salariotxt.setText("Salario: " + elemento_datos1.get(3));

        foto.setImageResource(imgs1);

        notifyDataSetChanged();


        /*nombretxt.setText(datos_empl[position][0]);
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

        /*return view;
    }



    @Override
    public int getCount() {
        return 4;
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
*/



















/*package com.wedeal.wedealproyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class empleados extends Fragment {
    FloatingActionButton fab;

    List<String> elemento_info = new ArrayList<>();

    private ListView listaaa;

    public empleados() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empleados, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Nombre negocio
        SharedPreferences pref = getActivity().getSharedPreferences("Registro", 0);
        String Negocio = pref.getString("Negocio", "");
        listaaa = getView().findViewById(R.id.listView);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Negocio).child("Usuarios de " + Negocio).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {


                    if (objSnapshot.child("Permisos").getValue().toString().equals("Empleado")) {
                        String nombre = objSnapshot.child("Usuario").getValue().toString();
                        String telefono = objSnapshot.child("Teléfono").getValue().toString();
                        String cargo = objSnapshot.child("Cargo").getValue().toString();
                        String salario = objSnapshot.child("Salario").getValue().toString();

                        elemento_info.add(nombre);
                        elemento_info.add(telefono);
                        elemento_info.add(cargo);
                        elemento_info.add(salario);

                    }

                }

                for (int i = 0; i < elemento_info.size() / 4; ++i){

                    List<String> elemento_info2 = new ArrayList<>();
                    elemento_info2.clear();

                    elemento_info2.add(elemento_info.get(4*i));
                    elemento_info2.add(elemento_info.get(1+4*i));
                    elemento_info2.add(elemento_info.get(2+4*i));
                    elemento_info2.add(elemento_info.get(3+4*i));


                    CustomAdapter_Empleados customAdapterEmpleados;

                    Toast.makeText(requireActivity().getApplicationContext(), "hola"+i, Toast.LENGTH_SHORT).show();

                    customAdapterEmpleados = new CustomAdapter_Empleados(requireActivity().getApplicationContext(), elemento_info2, R.drawable.empleado_ej1);
                    listaaa.setAdapter(customAdapterEmpleados);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Agregar empleado", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(getActivity(), crear_nuevo_empleado.class);
                getActivity().startActivity(intent);
            }
        });
    }
}*/
