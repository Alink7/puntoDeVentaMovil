package cl.crecic.puntodeventamovil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.models.Pedido;

/**
 * Created by Nicolas on 29-07-16.
 */
public class ListaHistorialAdapter extends ArrayAdapter<Pedido> {

    private List<Pedido> pedidos;

    public ListaHistorialAdapter(Context context, List<Pedido> pedidos) {
        super(context, 0, pedidos);

        this.pedidos = pedidos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pedido pedido = pedidos.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        convertView = inflater.inflate(R.layout.historial_pedido_content, parent, false);

        TextView fecha = (TextView) convertView.findViewById(R.id.tv_lista_fecha_pedido);
        TextView cliente = (TextView) convertView.findViewById(R.id.tv_lista_cliente_pedido);

        fecha.setText(pedido.getFechaStr());
        cliente.setText(pedido.getCliente().getNombre());

        return convertView;

    }
}
