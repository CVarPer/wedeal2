package com.wedeal.wedealproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class activity_escogerusuario extends AppCompatActivity {
    Spinner lista;
    Button botn = findViewById(R.id.continuar);
    String[] opciones = {"Seleccione su usuario","Cajero","Bodega","Dueño"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escogerusuario);
        lista = findViewById(R.id.selectorusuario);
            /* Aquí es necesario hacer uso del TextView del .xml para que el texto en el spinner no sea tan pequeño. Para personalizarlo, pues.
               Igualmente, en el adapter, se quita la escritura por defecto del R.Layout para poder introducir el modificador del spinner como parámetro.
             */
        ArrayAdapter<String> adap = new ArrayAdapter<>(this, R.layout.spinner_mod, opciones);

        lista.setAdapter(adap);
        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                switch(i){
                    case 1:
                        Toast.makeText(getApplicationContext(), opciones[i], Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), opciones[i], Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), opciones[i], Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Acción del botón: lanzar la interfaz del usuario correspondiente.
            /*botn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //El siguiente método nos permitirá convertir la opción del spinner escogida a String, para poder trabajar con ella.
                    String escogido= lista.getSelectedItem().toString();
                    if(escogido.equals("Dueño")){
                        Intent usuario_dueno = new Intent()
                    }
                    else if(escogido.equals("Cajero")){

                    }
                }
            });*/
    }
}
