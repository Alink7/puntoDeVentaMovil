package cl.crecic.puntodeventamovil.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.models.Cliente;

/**
 * Created by Nicolas on 28-07-16.
 */
public class FragmentNuevoCliente extends Fragment implements View.OnClickListener {

    //objeto cliente
    private Cliente clienteEditar;

    //variable que indica si es edicion o no
    private boolean esEdicion;

    //elementos en la vista
    private EditText etNombreCliente;
    private EditText etRutCliente;
    private EditText etDireccionCliente;
    private EditText etTelefonoCliente;
    private EditText etCorreoCliente;
    private EditText etCupoCliente;
    private Button btnAccion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.nuevo_cliente_toolbar);

        if (appBarLayout != null)
            appBarLayout.setTitle("Nuevo cliente");

        if (getArguments().containsKey("EDITAR_CLIENTE")){

            clienteEditar = (Cliente) getArguments().get("EDITAR_CLIENTE");

            if (appBarLayout != null)
                appBarLayout.setTitle("Edición " + clienteEditar.getNombre());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nuevo_cliente, container, false);

        etNombreCliente = (EditText) view.findViewById(R.id.ingreso_nombre_cliente);
        etRutCliente = (EditText) view.findViewById(R.id.ingreso_rut_cliente);
        etDireccionCliente = (EditText) view.findViewById(R.id.ingreso_direccion_cliente);
        etTelefonoCliente = (EditText) view.findViewById(R.id.ingreso_telefono_cliente);
        etCorreoCliente = (EditText) view.findViewById(R.id.ingreso_correo_cliente);
        etCupoCliente = (EditText) view.findViewById(R.id.ingreso_cupo_cliente);
        btnAccion = (Button) view.findViewById(R.id.btn_nuevo_editar_cliente);
        btnAccion.setOnClickListener(this);

        //Si es edicion de cliente
        if (clienteEditar != null){
            esEdicion = true;
            etNombreCliente.setText(clienteEditar.getNombre());
            etRutCliente.setText(clienteEditar.getRut());
            etDireccionCliente.setText(clienteEditar.getDireccion());
            etTelefonoCliente.setText(clienteEditar.getTelefono());
            etCorreoCliente.setText(clienteEditar.getCorreo());
            etCupoCliente.setText(clienteEditar.getCupo() + "");
            btnAccion.setText("Editar");
        }else{
            //Si es un cliente nuevo
            esEdicion = false;
            btnAccion.setText("Guardar");
        }

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.btn_nuevo_editar_cliente:

                if (esEdicion)
                    editarCliente(clienteEditar);
                else
                    guardarNuevoCliente();
                break;
        }
    }

    /**
     * Realiza la edicion del cliente en la base de datos
     * @param clienteEditar objeto Cliente a editar
     */
    public void editarCliente(Cliente clienteEditar){

    }

    /**
     * Guarda al nuevo cliente en la base de datos
     */
    public void guardarNuevoCliente(){

        Cliente nuevoCliente;

        String nombre = etNombreCliente.getText().toString();
        String rut = etRutCliente.getText().toString();
        String direccion = etDireccionCliente.getText().toString();
        String telefono = etTelefonoCliente.getText().toString();
        String correo = etCorreoCliente.getText().toString();
        int cupo = Integer.parseInt(etCupoCliente.getText().toString());

        nuevoCliente = new Cliente(nombre, rut, telefono, correo, direccion, cupo, 0);
    }
}
