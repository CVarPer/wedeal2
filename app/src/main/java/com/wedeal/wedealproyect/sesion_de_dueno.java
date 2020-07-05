package com.wedeal.wedealproyect;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sesion_de_dueno extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dueno);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
        } else {
            signInAsAnonymous();
        }

        //Como se quitó la ActionBar en esta pantalla, se agrega una barra de herramientas en su lugar, de la siguiente manera
        Toolbar barra = findViewById(R.id.barra_tareas);
        setSupportActionBar(barra);

        drawer = findViewById(R.id.DrawerLayout);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        //(contexto, variable drawer, variable toolbar, las strings que definimos anteriormene.
        ActionBarDrawerToggle tog = new ActionBarDrawerToggle(this, drawer, barra, R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawer.addDrawerListener(tog);
        //Rota el hamburger icon (el cuadro que abre el drawer)
        tog.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.cont_fragmentos, new miTienda_Fragment()).commit(); //Inicia con la actividad información en primer lugar, no vacío
            nav_view.setCheckedItem(R.id.nav_informacion);
        }
    }

    private void signInAsAnonymous() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                /* perform your actions here*/

            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("MainActivity", "signFailed****** ", exception);
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_informacion:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_fragmentos, new miTienda_Fragment()).commit();
                break;
            case R.id.nav_ventas:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_fragmentos, new ventasysolicitudes_Fragment()).commit();
                break;
            case R.id.nav_inventario:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_fragmentos, new inventario_Fragment()).commit();
                break;
            case R.id.nav_informes:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_fragmentos, new informes_Fragment()).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Compartir", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_ajustes:
                getSupportFragmentManager().beginTransaction().replace(R.id.cont_fragmentos, new ajustes_Fragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true; //Item selected
    }

    //El siguiente método permite que, cuando nuestro drawer está abierto, y al presionar "volver" en nuestro teléfono, este layout se cierre, como es común.
    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final CharSequence[] opciones = {"Cerrar Sesión", "Cancelar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Elige una opción");
            builder.setItems(opciones, new DialogInterface.OnClickListener() {
                @SuppressLint("IntentReset")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (opciones[i] == "Cerrar Sesión") {
                        Intent intent = new Intent(sesion_de_dueno.this, ajustes_cerrar_sesion.class);
                        startActivity(intent);

                    } else if (opciones[i] == "Cancelar") {
                        dialogInterface.dismiss();
                    }
                }
            });
            builder.show();
        }

    }


}
