package cl.crecic.puntodeventamovil;

import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cl.crecic.puntodeventamovil.fragments.FragmentHistorial;
import cl.crecic.puntodeventamovil.fragments.FragmentListaClientes;
import cl.crecic.puntodeventamovil.fragments.FragmentVenta;

public class MainActivity extends AppCompatActivity {

    private static final int FRAGMENT_VENTA = 0;
    private static final int FRAGMENT_CLIENTES = 1;
    private static final int FRAGMENT_HISTORIAL = 2;
    private int fragmentActual;

    //Tags para los fragments
    private static final String FRAGMENT_VENTA_TAG = "FRAGMENT_VENTA";
    private static final String FRAGMENT_CLIENTES_TAG = "FRAGMENT_CLIENTES";
    private static final String FRAGMENT_HISTORIAL_TAG = "FRAGMENT_HISTORIAL";

    private Fragment[] fragments = new Fragment[FRAGMENT_HISTORIAL +1];

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if(navigationView != null)
            setupNavigationDrawerContent(navigationView);


        setupNavigationDrawerContent(navigationView);

        //iniciar fragments
        iniciarFragments();

        if (savedInstanceState != null){
            setFragment(fragmentActual);
            System.out.println("Instacia anterior creada");
        }else{
            fragmentActual = FRAGMENT_VENTA;
            setFragment(FRAGMENT_VENTA);
            System.out.println("No hay instancias creadas");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setupNavigationDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_venta:
                                item.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_clientes:
                                item.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_historial:
                                item.setChecked(true);
                                setFragment(2);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_salir:
                                item.setChecked(true);
                                return true;
                        }
                        return true;
                    }
                }
        );
    }

    public void iniciarFragments(){
        fragments[FRAGMENT_VENTA] = new FragmentVenta();
        fragments[FRAGMENT_CLIENTES] = new FragmentListaClientes();
        fragments[FRAGMENT_HISTORIAL] = new FragmentHistorial();
    }

    /**
     * Maneja la visualización de los fragmentos
     * @param position posicion del fragmento
     */
    public void setFragment(int position){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(FRAGMENT_VENTA_TAG) != null)
            fragmentTransaction.hide(fragments[FRAGMENT_VENTA]);
        if (fragmentManager.findFragmentByTag(FRAGMENT_CLIENTES_TAG) != null)
            fragmentTransaction.hide(fragments[FRAGMENT_CLIENTES]);
        if (fragmentManager.findFragmentByTag(FRAGMENT_HISTORIAL_TAG) != null)
            fragmentTransaction.hide(fragments[FRAGMENT_HISTORIAL]);


        switch (position){
            case 0:
                if (fragmentManager.findFragmentByTag(FRAGMENT_VENTA_TAG) == null)
                    fragmentTransaction.add(R.id.fragment, fragments[FRAGMENT_VENTA], FRAGMENT_VENTA_TAG);
                else
                    fragmentTransaction.show(fragments[FRAGMENT_VENTA]);
                fragmentTransaction.commit();
                fragmentActual = FRAGMENT_VENTA;
                break;

            case 1:
                if (fragmentManager.findFragmentByTag(FRAGMENT_CLIENTES_TAG) == null)
                    fragmentTransaction.add(R.id.fragment, fragments[FRAGMENT_CLIENTES], FRAGMENT_CLIENTES_TAG);
                else
                    fragmentTransaction.show(fragments[FRAGMENT_CLIENTES]);
                fragmentTransaction.commit();
                fragmentActual = FRAGMENT_CLIENTES;
                break;

            case 2:
                if (fragmentManager.findFragmentByTag(FRAGMENT_HISTORIAL_TAG) == null)
                    fragmentTransaction.add(R.id.fragment, fragments[FRAGMENT_HISTORIAL], FRAGMENT_HISTORIAL_TAG);
                else
                    fragmentTransaction.show(fragments[FRAGMENT_HISTORIAL]);
                fragmentTransaction.commit();
                fragmentActual = FRAGMENT_HISTORIAL;
                break;
        }
    }
}
