package cl.crecic.puntodeventamovil.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nicolas on 27-07-16.
 */
public class Producto implements Parcelable {

    private String codigo, nombre, precio, descripcion;
    private int imagen;

    public Producto(String codigo, String nombre, String precio, String descripcion, int imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    protected Producto(Parcel in) {
        codigo = in.readString();
        nombre = in.readString();
        precio = in.readString();
        descripcion = in.readString();
        imagen = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(nombre);
        dest.writeString(precio);
        dest.writeString(descripcion);
        dest.writeInt(imagen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
