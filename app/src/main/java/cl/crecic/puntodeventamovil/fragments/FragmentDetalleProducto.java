package cl.crecic.puntodeventamovil.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.models.Producto;

/**
 * Created by Nicolas on 27-07-16.
 */
public class FragmentDetalleProducto extends Fragment {

    private Producto producto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //cargar el modelo segun el ID
        if (getArguments().containsKey("PRODUCTO"))
            producto = (getArguments().getParcelable("PRODUCTO"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detalle_producto, container, false);

        if (producto != null){
            TextView prueba = (TextView) v.findViewById(R.id.txtPrueba);
            prueba.setText(producto.getNombre());
        }

        return v;
    }
}
