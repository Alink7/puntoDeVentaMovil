package cl.crecic.puntodeventamovil.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nicolas on 29-07-16.
 */
public class Pedido implements Parcelable{

    private Calendar fecha;
    private Cliente cliente;

    public Pedido(Calendar fecha, Cliente cliente){

        this.fecha = fecha;
        this.cliente = cliente;
    }

    protected Pedido(Parcel in) {
        cliente = in.readParcelable(Cliente.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(cliente, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public String getFechaStr(){
        return  fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/" + fecha.get(Calendar.YEAR);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
