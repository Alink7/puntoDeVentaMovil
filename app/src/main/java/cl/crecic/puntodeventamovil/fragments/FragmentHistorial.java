package cl.crecic.puntodeventamovil.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.adapters.ListaHistorialAdapter;
import cl.crecic.puntodeventamovil.models.Cliente;
import cl.crecic.puntodeventamovil.models.Pedido;

/**
 * Created by Nicolas on 28-07-16.
 */
public class FragmentHistorial extends Fragment implements View.OnClickListener{

    //Elementos de la vista
    private Button btnCalendario;
    private EditText etFechaHistorial;
    private ListView lvHistorialPedido;

    //Estructura de datos
    private List<Pedido> pedidos;
    private List<Pedido> pedidosPrueba;

    //Adapter para la lista
    private ListaHistorialAdapter historialAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        etFechaHistorial = (EditText) view.findViewById(R.id.et_fecha_historial);
        etFechaHistorial.setOnClickListener(this);
        etFechaHistorial.setInputType(InputType.TYPE_NULL);
        btnCalendario = (Button) view.findViewById(R.id.btn_buscar_historial);
        btnCalendario.setOnClickListener(this);

        lvHistorialPedido = (ListView) view.findViewById(R.id.lv_historial_pedido);

        iniciarPedidosPrueba();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");


        pedidos = buscarPedidos(simpleDateFormat.format(calendar.getTime()));
        historialAdapter = new ListaHistorialAdapter(getContext(), pedidos);
        lvHistorialPedido.setAdapter(historialAdapter);

        return view;
    }


    /**
     * Muestra el dialog con el calendario para seleccionar una fecha
     */
    protected void mostrarDialogCalendario(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dialog_historial_fecha, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(view);

        final DatePicker calendario = (DatePicker) view.findViewById(R.id.fecha_historial);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int dia = calendario.getDayOfMonth();
                        int mes = calendario.getMonth()+1;
                        int anio = calendario.getYear();
                        String fecha = dia + "/" + mes + "/" + anio;

                        etFechaHistorial.setText(fecha);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * Realiza la consulta a la base de datos por los pedidos realizados desde la fecha que se
     * pasa por parametro
     * @param fecha la fecha de inicio d la busqueda en formato dd/mm/aaaa
     */
    public List<Pedido> buscarPedidos(String fecha){

        List<Pedido> pedidos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        //buscar en la base de datos
        for(Pedido p : pedidosPrueba){
            System.out.println("Fecha a buscar: " + fecha + " - Fecha del pedido: " + p.getFechaStr());
            if (p.getFechaStr().equalsIgnoreCase(fecha)){
                pedidos.add(p);
            }
        }

        return pedidos;
    }


    public void iniciarPedidosPrueba(){
        pedidosPrueba = new ArrayList<>();

        Cliente cliente = new Cliente("cliente", "11222333-5", "12345678", "", "", 2, 0);
        Cliente cliente1 = new Cliente("cliente1", "11222333-5", "12345678", "", "", 2, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 7, 29);

        pedidosPrueba.add(new Pedido(calendar, cliente));
        pedidosPrueba.add(new Pedido(calendar, cliente1));
        pedidosPrueba.add(new Pedido(calendar, cliente));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2016, 4, 29);
        pedidosPrueba.add(new Pedido(calendar1, cliente1));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2016, 5, 29);
        pedidosPrueba.add(new Pedido(calendar2, cliente));
        pedidosPrueba.add(new Pedido(calendar2, cliente1));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.et_fecha_historial:
                mostrarDialogCalendario();
                break;
            case R.id.btn_buscar_historial:
                pedidos = buscarPedidos(etFechaHistorial.getText().toString());
                historialAdapter = new ListaHistorialAdapter(getContext(), pedidos);
                lvHistorialPedido.setAdapter(historialAdapter);
                break;
        }
    }
}
