package cl.crecic.puntodeventamovil.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nicolas on 27-07-16.
 */
public class Cliente implements Parcelable {

    private String nombre, rut, telefono, correo, direccion;
    private int cupo, estado;

    public Cliente(String nombre, String rut, String telefono, String correo, String direccion, int cupo, int estado) {
        this.nombre = nombre;
        this.rut = rut;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.cupo = cupo;
        this.estado = estado;
    }

    protected Cliente(Parcel in) {
        nombre = in.readString();
        rut = in.readString();
        telefono = in.readString();
        correo = in.readString();
        direccion = in.readString();
        cupo = in.readInt();
        estado = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(rut);
        dest.writeString(telefono);
        dest.writeString(correo);
        dest.writeString(direccion);
        dest.writeInt(cupo);
        dest.writeInt(estado);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
