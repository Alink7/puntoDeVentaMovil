package cl.crecic.puntodeventamovil;

import android.support.design.widget.NavigationView;
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

        setFragment(0);
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

    public void setFragment(int position){

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position){
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentVenta fragmentVenta = new FragmentVenta();
                fragmentTransaction.replace(R.id.fragment, fragmentVenta);
                fragmentTransaction.commit();
                break;

            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentListaClientes fragmentListaClientes = new FragmentListaClientes();
                fragmentTransaction.replace(R.id.fragment, fragmentListaClientes);
                fragmentTransaction.commit();
                break;

            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragmentHistorial fragmentHistorial = new FragmentHistorial();
                fragmentTransaction.replace(R.id.fragment, fragmentHistorial);
                fragmentTransaction.commit();
                break;
        }

    }
}
