package com.example.itm_proyectofinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itm_proyectofinal.Beans.Pedido;
import com.example.itm_proyectofinal.Beans.Producto;
import com.example.itm_proyectofinal.DetalleProductoClasicoActivity;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.example.itm_proyectofinal.R;

import java.util.ArrayList;

public class AdapterProductosComprar extends RecyclerView.Adapter<AdapterProductosComprar.ViewHolderProductosComprar> implements View.OnClickListener {
    ArrayList<Producto> lstPro;
    private View.OnClickListener listener;
    Context context;


    public AdapterProductosComprar(ArrayList<Producto> lstPro, Context context) {
        this.lstPro = lstPro;
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
    public AdapterProductosComprar.ViewHolderProductosComprar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productos_comprar,null,false);
        view.setOnClickListener(this);
        return new AdapterProductosComprar.ViewHolderProductosComprar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductosComprar.ViewHolderProductosComprar holder, int position) {
        holder.nombre.setText(lstPro.get(position).getNombre());
        holder.precio.setText(""+lstPro.get(position).getPrecio());
        holder.medida.setText(lstPro.get(position).getCriterio_medida());
        holder.imagen.setImageBitmap(lstPro.get(position).getImagen());
        final SQLite_OpenHelper helper=new SQLite_OpenHelper(context);
        Cursor c= helper.consultarRegistro(lstPro.get(position).getCodigo());
        while (c.moveToNext()){
            holder.agricultor.setText(c.getString(1)+" "+c.getString(2));
        }
        //holder.codProd=lstPro.get(position).getCodigo();
        //holder.codAgri=lstPro.get(position).getCodAgri();
        //holder.codCla=lstPro.get(position).getCodCla();




    }

    @Override
    public int getItemCount() {
        return lstPro.size();
    }


    public class ViewHolderProductosComprar extends RecyclerView.ViewHolder{
        TextView nombre,precio,medida,agricultor;
        ImageView imagen;
        //int codProd;
        //int codCla;
        //int codAgri;
        //String arr;
        //ArrayList<Pedido> lstPedido;
        public ViewHolderProductosComprar(@NonNull View itemView) {
            super(itemView);
            nombre=(TextView) itemView.findViewById(R.id.idNombreProdAgri);
            precio=(TextView) itemView.findViewById(R.id.idPrecioAgri);
            medida=(TextView) itemView.findViewById(R.id.idCretrioAgri);
            agricultor=(TextView) itemView.findViewById(R.id.idNombreAgri);
            imagen=(ImageView) itemView.findViewById(R.id.imagenProdAgri);


            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(context, DetalleProductoClasicoActivity.class);
                    i.putExtra("idProducto",codProd);
                    i.putExtra("idClasico",codCla);
                    i.putExtra("idAgricultor",codAgri);
                    i.putExtra("Arreglo",arr);
                    context.startActivity(i);
                }
            });*/
        }
    }

    public Producto obtenerPro(int indice){
        if (lstPro.size()==0){
            return null;
        }else{
            return lstPro.get(indice);
        }

    }

    public void filterLisProducts(ArrayList<Producto> filterProd){
        this.lstPro=filterProd;
        notifyDataSetChanged();
    }

}
