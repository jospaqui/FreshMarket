package com.example.itm_proyectofinal.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itm_proyectofinal.Beans.Venta;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.example.itm_proyectofinal.R;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class AdapterCompras extends RecyclerView.Adapter<AdapterCompras.ViewHolderVerCompras> implements View.OnClickListener{
    ArrayList<Venta> lstVenta;
    private View.OnClickListener listener;
    Context context;

    public AdapterCompras(ArrayList<Venta> lstVenta, Context context) {
        this.lstVenta = lstVenta;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public AdapterCompras.ViewHolderVerCompras onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compras_clasico,null,false);
        view.setOnClickListener(this);
        return new AdapterCompras.ViewHolderVerCompras(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCompras.ViewHolderVerCompras holder, int position) {

        holder.departamento.setText(lstVenta.get(position).getDepart());
        holder.provincia.setText(lstVenta.get(position).getProvincia());
        holder.direccion.setText(lstVenta.get(position).getDireccion());
        holder.fecha.setText(lstVenta.get(position).getFecha());
        holder.delivery.setText(""+lstVenta.get(position).getDelivery());
        holder.monto.setText(""+lstVenta.get(position).getMonto());
        holder.mont_deli.setText(""+(lstVenta.get(position).getDelivery()+lstVenta.get(position).getMonto()));
        final SQLite_OpenHelper helper=new SQLite_OpenHelper(context);
        Cursor c= helper.user_agri_datos(lstVenta.get(position).getId_agri());

        while (c.moveToNext()){
            holder.nombreP.setText(c.getString(1)+" "+c.getString(2));
        }

        Cursor cp=helper.consultaVentaDetallexProducto(lstVenta.get(position).getId_venta());
        String texto="";
        while (cp.moveToNext()){
            texto+="____________________________________"+"\n";
            texto+="Producto: "+cp.getString(0)+"\n";
            texto+="Precio Unitario: S/ "+cp.getDouble(1)+"\n";
            texto+="Cantidad: "+cp.getInt(3)+" "+cp.getString(2)+"\n";
            texto+="Precio x Cantidad: S/ "+cp.getDouble(4)+"\n";
        }
        holder.productos.setText(texto);

    }

    @Override
    public int getItemCount() {
        return lstVenta.size();
    }

    public class ViewHolderVerCompras extends RecyclerView.ViewHolder{
        TextView nombreP,departamento,provincia,direccion,fecha,delivery,monto,mont_deli;
        EditText productos;
        public ViewHolderVerCompras(@NonNull View itemView) {
            super(itemView);

            nombreP=(TextView) itemView.findViewById(R.id.idCliente);
            departamento=(TextView) itemView.findViewById(R.id.idDepa);
            provincia=(TextView) itemView.findViewById(R.id.idProvi);
            direccion=(TextView) itemView.findViewById(R.id.idDireccion);
            fecha=(TextView) itemView.findViewById(R.id.idFecha);
            delivery=(TextView) itemView.findViewById(R.id.idDeli);
            monto=(TextView) itemView.findViewById(R.id.idMont);
            mont_deli=(TextView) itemView.findViewById(R.id.idMontDeli);
            productos=(EditText) itemView.findViewById(R.id.idProductos);
        }
    }
}
