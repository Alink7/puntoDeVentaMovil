package cl.crecic.puntodeventamovil.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.models.Cliente;

/**
 * Created by Nicolas on 28-07-16.
 */
public class FragmentDetalleCliente extends Fragment {

    public static final String ARG_ITEM_ID = "ITEM_ID";

    private Cliente cliente;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)){

            cliente = (Cliente) getArguments().get(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
                appBarLayout.setTitle(cliente.getNombre());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cliente_detail, container, false);

        if (cliente != null){
            ((TextView) rootView.findViewById(R.id.cliente_nombre)).setText(cliente.getNombre());
            ((TextView) rootView.findViewById(R.id.cliente_rut)).setText(cliente.getRut());
            ((TextView) rootView.findViewById(R.id.cliente_direccion)).setText(cliente.getDireccion());
            ((TextView) rootView.findViewById(R.id.cliente_telefono)).setText(cliente.getTelefono());
            ((TextView) rootView.findViewById(R.id.cliente_correo)).setText(cliente.getCorreo());
            ((TextView) rootView.findViewById(R.id.cliente_cupo)).setText(cliente.getCupo() + "");
        }

        return rootView;
    }
}
