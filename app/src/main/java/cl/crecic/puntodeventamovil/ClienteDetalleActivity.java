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

/**
 * Created by Nicolas on 28-07-16.
 */
public class ClienteDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cliente_detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            //carga el fragmento de detalles y lo añade a la actividad
            Bundle argumentos = new Bundle();
            argumentos.putParcelable(FragmentDetalleCliente.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(FragmentDetalleCliente.ARG_ITEM_ID));

            FragmentDetalleCliente fragment = new FragmentDetalleCliente();
            fragment.setArguments(argumentos);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cliente_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){

            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
