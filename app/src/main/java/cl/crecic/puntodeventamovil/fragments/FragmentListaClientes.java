package cl.crecic.puntodeventamovil.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.crecic.puntodeventamovil.ClienteDetalleActivity;
import cl.crecic.puntodeventamovil.ClienteNuevoActivity;
import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.models.Cliente;

/**
 * Created by Nicolas on 27-07-16.
 */
public class FragmentListaClientes extends Fragment {

    private ArrayList<Cliente> clientes;
    private boolean mTwoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.cliente_list, container, false);

        cargarClientes();
        View recyclerView = view.findViewById(R.id.cliente_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (view.findViewById(R.id.cliente_detail_container) != null)
            mTwoPane = true;

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_clientes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.ic_nuevo_usuario:
                Intent i = new Intent(getContext(), ClienteNuevoActivity.class);
                startActivity(i);
                return true;
        }

        return true;
    }

    /**
     * Carga los clientes desde la base de datos
     * los guarda en una lista para mostrarlo en la vista.
     */
    public void cargarClientes(){
        clientes = new ArrayList<>();
        clientes.add(new Cliente("cliente1", "11222333-5", "12345678", "cliente1@gmail.com", "Direccion 1", 10, 0));
        clientes.add(new Cliente("cliente2", "11222333-6", "12345678", "cliente2@gmail.com", "Direccion 2", 100, 0));
        clientes.add(new Cliente("cliente3", "11222333-7", "12345678", "cliente3@gmail.com", "Direccion 3", 1000, 0));
        clientes.add(new Cliente("cliente4", "11222333-8", "12345678", "cliente4@gmail.com", "Direccion 4", 10000, 0));
        clientes.add(new Cliente("cliente5", "11222333-9", "12345678", "cliente5@gmail.com", "Direccion 5", 100000, 0));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(clientes));
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>{

        private final List<Cliente> clientes;

        public SimpleItemRecyclerViewAdapter(List<Cliente> clientes) {
            this.clientes = clientes;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cliente_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = clientes.get(position);
            holder.listaNombreCliente.setText(clientes.get(position).getNombre());
            holder.listaDireccionCliente.setText(clientes.get(position).getDireccion());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //si es una tablet se muestra el fragmento de detalle
                    if (mTwoPane){
                        Bundle argumentos = new Bundle();
                        argumentos.putParcelable(FragmentDetalleCliente.ARG_ITEM_ID, holder.mItem);
                        FragmentDetalleCliente fragment = new FragmentDetalleCliente();
                        fragment.setArguments(argumentos);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.cliente_detail_container, fragment)
                                .commit();
                    }else{
                        //sino se lanza la actividad de detalle
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ClienteDetalleActivity.class);
                        intent.putExtra(FragmentDetalleCliente.ARG_ITEM_ID, holder.mItem);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return clientes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public final View mView;
            public final TextView listaNombreCliente;
            public final TextView listaDireccionCliente;

            public Cliente mItem;

            public ViewHolder(View view){
                super(view);
                mView = view;
                listaNombreCliente = (TextView) view.findViewById(R.id.lista_nombre_cliente);
                listaDireccionCliente = (TextView) view.findViewById(R.id.lista_direccion_cliente);
            }
        }
    }
}
