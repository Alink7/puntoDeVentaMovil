package cl.crecic.puntodeventamovil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cl.crecic.puntodeventamovil.R;
import cl.crecic.puntodeventamovil.models.Producto;

/**
 * Created by Nicolas on 27-07-16.
 */
public class ListaProductosAdapter extends ArrayAdapter<Producto>{

    private final List<Producto> productos;
    private List<Producto> productosFiltrados = new ArrayList<>();

    public ListaProductosAdapter(Context context, HashMap<String, Producto> productos){
        super(context, 0);

        this.productos = new ArrayList<>();

        Set<String> keys = productos.keySet();

        for (String key : keys)
            this.productos.add(productos.get(key));

    }

    @Override
    public int getCount() {
        return productosFiltrados.size();
    }

    @Override
    public Filter getFilter() {
        return new FiltroProducto(this, productos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Producto producto = productosFiltrados.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        convertView = inflater.inflate(R.layout.productos_autocomplete_layout, parent, false);

        TextView codigo = (TextView)convertView.findViewById(R.id.codigo_producto);
        TextView nombre = (TextView)convertView.findViewById(R.id.nombre_producto);
        ImageView imagen = (ImageView)convertView.findViewById(R.id.imagen_producto);

        codigo.setText(producto.getCodigo());
        nombre.setText(producto.getNombre());
        imagen.setImageResource(producto.getImagen());

        return convertView;
    }

    class FiltroProducto extends Filter{

        ListaProductosAdapter adapter;
        List<Producto> listaOriginal, listaFiltrada;

        public FiltroProducto(ListaProductosAdapter adapter, List<Producto> listaOriginal){
            super();
            this.adapter = adapter;
            this.listaOriginal = listaOriginal;
            this.listaFiltrada = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            listaFiltrada.clear();

            final FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0){
                listaFiltrada.addAll(listaOriginal);
            }else{
                final String filterPattern = constraint.toString().toLowerCase().trim();

                //logica para filtrar
                for (final Producto p : listaOriginal){
                    if ((p.getCodigo() + p.getNombre()).toLowerCase().contains(filterPattern))
                        listaFiltrada.add(p);
                }
            }
            results.values = listaFiltrada;
            results.count = listaFiltrada.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.productosFiltrados.clear();
            adapter.productosFiltrados.addAll((List) results.values);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public Producto getItem(int position) {
        return productosFiltrados.get(position);
    }
}
