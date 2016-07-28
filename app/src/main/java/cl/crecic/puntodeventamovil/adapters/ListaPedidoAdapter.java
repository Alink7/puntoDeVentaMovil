package cl.crecic.puntodeventamovil.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.models.Producto;

/**
 * Created by Nicolas on 27-07-16.
 */
public class ListaPedidoAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private List<Producto> productos;
    private TextView subtotal, iva, total;

    public ListaPedidoAdapter(Activity context, List<Producto> productos, TextView subtotal,
                              TextView iva, TextView total){
        this.context = context;
        this.productos = productos;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
    }

    @Override
    public int getGroupCount() {
        return productos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return productos.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String nombreProducto = ((Producto)getGroup(groupPosition)).getNombre();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pedido_item, null);
        }

        //Cantidad
        final TextView cantidad = (TextView) convertView.findViewById(R.id.cantidad_producto);

        //Botones para manejar la cantidad
        Button btn_menos = (Button) convertView.findViewById(R.id.menos_cantidad);
        Button btn_mas = (Button) convertView.findViewById(R.id.mas_cantidad);
        btn_menos.setFocusable(false);
        btn_mas.setFocusable(false);
        btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.menos_cantidad){
                    if((Integer.parseInt(cantidad.getText().toString()) - 1) == 0)
                        cantidad.setText("1");
                    else
                        cantidad.setText("" + (Integer.parseInt(cantidad.getText().toString()) -1));
                }
            }
        });

        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.mas_cantidad)
                    cantidad.setText("" + (Integer.parseInt(cantidad.getText().toString()) + 1));
            }
        });

        //Boton para eliminar item de la lista
        final ImageButton eliminar_producto = (ImageButton) convertView.findViewById(R.id.eliminar_producto);
        eliminar_producto.setFocusable(false);
        eliminar_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.eliminar_producto)
                    eliminarItemLista(groupPosition);
            }
        });

        //nombre del producto en la lista
        TextView nombre_producto = (TextView) convertView.findViewById(R.id.lista_nombre_producto);
        nombre_producto.setText(nombreProducto);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Producto producto = ((Producto)getGroup(groupPosition));

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.pedido_item_detalle, parent, false);

        TextView txtNombre = (TextView) convertView.findViewById(R.id.detalle_nombre_producto_label);
        txtNombre.setText(producto.getNombre());

        TextView txtPrecio = (TextView) convertView.findViewById(R.id.detalle_precio_producto);
        txtPrecio.setText("$ " + producto.getPrecio());

        TextView txtDescripcion = (TextView) convertView.findViewById(R.id.detalle_descripcion_producto);
        txtDescripcion.setText(producto.getDescripcion());

        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen_producto_lista);
        imagen.setImageResource(producto.getImagen());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean removeGroup(int groupPosition){
        String precio = "-"+productos.get(groupPosition).getPrecio();
        actualizarValores(precio);
        productos.remove(groupPosition);
        notifyDataSetChanged();
        return true;
    }

    public void addItem(Producto groupTitle){
        productos.add(0, groupTitle);
        actualizarValores(groupTitle.getPrecio());
        notifyDataSetChanged();
    }

    private void actualizarValores(String nuevoValor){

        int precio = Integer.parseInt(nuevoValor);
        int vSub = Integer.parseInt(subtotal.getText().toString()) + precio;
        int vTotal = Integer.parseInt(subtotal.getText().toString()) + precio;
        int vIva = (int)(vTotal*0.19);

        subtotal.setText(""+vSub);
        total.setText(""+vTotal);
        iva.setText(""+vIva);
    }

    public void eliminarItemLista(final int groupPosition){

        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_eliminar_item_title)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeGroup(groupPosition);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }
}
