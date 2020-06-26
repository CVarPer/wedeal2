package com.wedeal.wedealproyect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ajustes_Fragment extends Fragment {
    Button permisoespecial, cerrarsesion, modificarempleado, eliminarempleado, eliminar_proveedor;
    Context mContext;

    public ajustes_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_ajustes_admin, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        permisoespecial = getView().findViewById(R.id.permisoespecial);
        permisoespecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ajustes_permiso_especial.class);
                requireActivity().startActivity(intent);
            }


        });


        cerrarsesion = getView().findViewById(R.id.cerrarsesion);
        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ajustes_cerrar_sesion.class);
                requireActivity().startActivity(intent);
            }


        });

        modificarempleado = getView().findViewById(R.id.modificarcuentaempleado);
        modificarempleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ajustes_modificar_empleado.class);
                requireActivity().startActivity(intent);
            }


        });

        eliminarempleado = getView().findViewById(R.id.eliminarusuario);
        eliminarempleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ajustes_eliminar_empleado.class);
                requireActivity().startActivity(intent);
            }


        });

        eliminar_proveedor = getView().findViewById(R.id.eliminarproveedor);
        eliminar_proveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ajustes_eliminar_proveedor.class);
                requireActivity().startActivity(intent);
            }


        });

    }
}
