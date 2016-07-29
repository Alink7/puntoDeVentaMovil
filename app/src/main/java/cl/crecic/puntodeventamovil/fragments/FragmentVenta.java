package cl.crecic.puntodeventamovil.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.adapters.ListaPedidoAdapter;
import cl.crecic.puntodeventamovil.adapters.ListaProductosAdapter;
import cl.crecic.puntodeventamovil.models.Producto;

/**
 * Created by Nicolas on 27-07-16.
 */
public class FragmentVenta extends Fragment implements View.OnClickListener{

    //Elementos de la vista
    private AutoCompleteTextView txtCliente, txtBusquedaProducto;
    private TextView txtSubtotal, txtIva, txtTotal;
    private Button btnAsignar, btnAniadirProducto, btnRealizarPedido;

    //Adapters
    private ListaPedidoAdapter pedidoAdapter;
    private ArrayAdapter<String> clientesAdapter;

    //Estructuras de datos
    private HashMap<String, Producto> listaMaestra;
    private List<Producto> listaProductos;

    //Variables
    private String strSubtotal, StrIva, strTotal;
    private int cantidadProducto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_venta, container, false);

        txtCliente = (AutoCompleteTextView)view.findViewById(R.id.txtCliente);
        txtBusquedaProducto = (AutoCompleteTextView)view.findViewById(R.id.txtBusquedaProducto);
        txtCliente.setOnClickListener(this);
        txtCliente.setFocusable(false);
        txtBusquedaProducto.setFocusable(false);
        txtBusquedaProducto.setOnClickListener(this);

        txtSubtotal = (TextView)view.findViewById(R.id.txtSubtotalValorBottom);
        txtIva = (TextView)view.findViewById(R.id.txtIvaValorBottom);
        txtTotal = (TextView)view.findViewById(R.id.txtTotalValorBottom);
        btnAsignar = (Button)view.findViewById(R.id.btn_asignar_cliente);
        btnAniadirProducto = (Button)view.findViewById(R.id.btn_aniadir_producto);
        btnRealizarPedido = (Button)view.findViewById(R.id.btn_realizar_pedido);

        //setea el listener para el evento onclick
        btnAsignar.setOnClickListener(this);
        btnAniadirProducto.setOnClickListener(this);
        btnRealizarPedido.setOnClickListener(this);


        listarProductos();
        cargarClientes();
        cargarProductos();

        final ExpandableListView lista = (ExpandableListView)view.findViewById(R.id.listaProductosPedido);
        pedidoAdapter = new ListaPedidoAdapter(getActivity(), listaProductos, txtSubtotal, txtIva, txtTotal);
        lista.setAdapter(pedidoAdapter);

        return view;
    }

    public void listarProductos(){
        listaProductos = new ArrayList<>();
    }

    /**
     * Carga los clientes desde la base de datos y los carga en el adapter
     */
    public void cargarClientes(){
        String[] clientes = {"cliente1", "cliente2", "cliente3", "cliente4", "cliente5"};
        clientesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, clientes);
        txtCliente.setAdapter(clientesAdapter);
    }

    /**
     * Carga los productos desde el HashMap de productos en el
     * adapter del editText
     */
    public void cargarProductos(){
        //lista maestra de productos en bodega
        listaMaestra = cargarListaMaestra();
        final ListaProductosAdapter productosAdapter = new ListaProductosAdapter(getActivity(), listaMaestra);

        txtBusquedaProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtBusquedaProducto.setText(productosAdapter.getItem(position).getNombre());
            }
        });

        txtBusquedaProducto.setAdapter(productosAdapter);
    }

    /**
     * Carga los productos desde la base de datos en un HashMap
     * @return HashMap con los productos
     */
    public HashMap<String, Producto> cargarListaMaestra(){
        HashMap<String, Producto> listaMaestra = new HashMap<>();

        listaMaestra.put("Leche", new Producto("0001", "Leche", "500", "", R.mipmap.leche));
        listaMaestra.put("Mantequilla", new Producto("0002", "Mantequilla", "1000", "", R.mipmap.mantequilla));
        listaMaestra.put("Pan", new Producto("0003", "Pan", "1500", "", R.mipmap.pan));
        listaMaestra.put("Mermelada", new Producto("0004", "Mermelada", "2000", "", R.mipmap.mermelada));
        listaMaestra.put("Huevo", new Producto("0005", "Huevo", "2500", "", R.mipmap.huevo));
        listaMaestra.put("Palta", new Producto("0006", "Palta", "3000", "", R.mipmap.palta));

        return listaMaestra;
    }

    //acciones de botones
    public void addItem(View v){

        //preguntar por cantidad
        mostrarDialogoCantidad();
    }

    public void asignarCliente(View v){
        txtCliente.setEnabled(false);
        btnAsignar.setText("Editar");
        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCliente.setEnabled(true);
                btnAsignar.setText("Asignar");
                btnAsignar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        asignarCliente(v);
                    }
                });
            }
        });

        Toast.makeText(getContext(), "El pedido se asignó a " + txtCliente.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void realizarPedido(View v){

        View focus = null;
        boolean cancelar = false;

        //validar cliente
        String cliente = txtCliente.getText().toString();
        if (TextUtils.isEmpty(cliente)){
            txtCliente.setError("Este campo es obligatorio");
            focus = txtCliente;
            cancelar = true;
        }
        if (!esClienteValido(cliente)){
            txtCliente.setError("El cliente no es válido");
            focus = txtCliente;
            cancelar = true;
        }

        //validar lista
        if (pedidoAdapter.getGroupCount() == 0){
            txtBusquedaProducto.setError("La lista de productos está vacía");
            focus = txtBusquedaProducto;
            cancelar = true;
        }

        //si hay algun error de validacion
        if (cancelar){
            focus.requestFocus();
        }else{
            //guardar pedido
            int size = pedidoAdapter.getGroupCount();

            //lista de productos con los que forman parte del pedido
            List<Producto> productosEnPedido = new ArrayList<>();

            for (int i = 0; i < size; i++)
                productosEnPedido.add((Producto) pedidoAdapter.getGroup(i));

            //guardar lista del pedido en la base de datos
            guardarPedido(productosEnPedido);

        }
    }

    /**
     * Guarda la lista de productos en la base de datos
     * @param productos la lista de los productos en el pedido
     */
    public void guardarPedido(List<Producto> productos) {


    }

    //Valida el campo de texto del cliente
    private boolean esClienteValido(String nCliente){
        if (clientesAdapter.getPosition(nCliente) > - 1)
            return true;
        return false;
    }

    /**
     * Carga el fragmento, cuando es una vista en tablet, para ver los detalles del producto
     * @param producto
     */
    public void cargarFragmento(Producto producto){
        Bundle argumentos = new Bundle();
        argumentos.putParcelable("PRODUCTO", producto);
        FragmentDetalleProducto fragmentDetalle = new FragmentDetalleProducto();
        fragmentDetalle.setArguments(argumentos);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_detalle_producto, fragmentDetalle)
                .commit();
    }

    /**
     * Muestra un dialogo para el ingreso de la cantidad del producto
     */
    protected void mostrarDialogoCantidad(){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_cantidad, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(view);

        final EditText et_cantidad_producto = (EditText) view.findViewById(R.id.et_cantidad_producto);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cantidadProducto = Integer.parseInt(et_cantidad_producto.getText().toString());

                        String producto = txtBusquedaProducto.getText().toString();
                        txtBusquedaProducto.setText("");

                        //limpiar fragmento
                        cargarFragmento(new Producto("", "", "", "", 0));

                        //añadir elemento
                        pedidoAdapter.addItem(listaMaestra.get(producto), cantidadProducto);
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    //Asigna la funcionalidad a cada elemento de la vista
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_aniadir_producto:
                addItem(v);
                break;
            case R.id.btn_asignar_cliente:
                asignarCliente(v);
                break;
            case R.id.btn_realizar_pedido:
                realizarPedido(v);
                break;
            case R.id.txtCliente:
                txtCliente.setFocusableInTouchMode(true);
                break;
            case R.id.txtBusquedaProducto:
                txtBusquedaProducto.setFocusableInTouchMode(true);
                break;
        }
    }
}
