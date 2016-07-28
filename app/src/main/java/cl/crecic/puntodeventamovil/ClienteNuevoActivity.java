package cl.crecic.puntodeventamovil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cl.crecic.puntodeventamovil.fragments.FragmentDetalleCliente;
import cl.crecic.puntodeventamovil.fragments.FragmentListaClientes;
import cl.crecic.puntodeventamovil.fragments.FragmentNuevoCliente;

/**
 * Created by Nicolas on 28-07-16.
 */
public class ClienteNuevoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nuevo_cliente);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nuevo_cliente_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            //carga el fragmento de detalles y lo añade a la actividad
            Bundle argumentos = new Bundle();

            if (getIntent().getExtras() != null) {
                argumentos.putParcelable("EDITAR_CLIENTE",
                        getIntent().getParcelableExtra("EDITAR_CLIENTE"));
            }

            FragmentNuevoCliente fragment = new FragmentNuevoCliente();
            fragment.setArguments(argumentos);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nuevo_cliente_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){

            NavUtils.navigateUpTo(this, new Intent(this, FragmentListaClientes.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
