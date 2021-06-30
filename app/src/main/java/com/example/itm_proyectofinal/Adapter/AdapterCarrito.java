package com.example.itm_proyectofinal.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itm_proyectofinal.Beans.Pedido;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.example.itm_proyectofinal.R;

import java.util.ArrayList;

public class AdapterCarrito extends RecyclerView.Adapter<AdapterCarrito.ViewHolderCarrito> implements View.OnClickListener{
    ArrayList<Pedido> lstPedido;
    private View.OnClickListener listener;
    Context context;

    public AdapterCarrito(ArrayList<Pedido> lstPedido, Context context) {
        this.lstPedido = lstPedido;
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
    public AdapterCarrito.ViewHolderCarrito onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_carrito,null,false);
        view.setOnClickListener(this);
        return new AdapterCarrito.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarrito.ViewHolderCarrito holder, int position) {
        final SQLite_OpenHelper helper=new SQLite_OpenHelper(context);
        Cursor c= helper.mostrarProductosxid(lstPedido.get(position).getCodProd());
        while (c.moveToNext()){
            holder.nombreP.setText(c.getString(1));
            holder.precioU.setText("S/ "+c.getDouble(2));
            holder.medida.setText(c.getString(3));
            byte[] imageBytes=c.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            holder.imagen.setImageBitmap(objectBitmap);
            holder.agricultor.setText(c.getString(8)+" "+c.getString(9));
        }
        holder.cantidad.setText(""+lstPedido.get(position).getCantidad());
        holder.precio_cant.setText("S/ "+lstPedido.get(position).getCant_precio());

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db= helper.getWritableDatabase();
                String selection="id_producto_pe= ? and id_clasico_pe= ?";
                //String selection="id_prod= ?";
                //String[] selectionArgs={""+lstPedido.get(position).getCodProd()};
                String[] selectionArgs={""+lstPedido.get(position).getCodProd(),""+lstPedido.get(position).getCodClas()};
                db.delete("PEDIDO_PRUEBA",selection,selectionArgs);

                 //para notificar el cambio en el recycler

                int cant;
                int cant_total;
                Cursor cp=helper.consultarProductos(lstPedido.get(position).getCodProd());
                while (cp.moveToNext()){
                    cant=cp.getInt(4);
                    cant_total=cant+lstPedido.get(position).getCantidad();
                    SQLiteDatabase dbd= helper.getReadableDatabase();
                    ContentValues values= new ContentValues();
                    values.put("stock_prod",cant_total);
                    //which row to update based on the title
                    String selectionn="id_prod= ?";
                    String[] selectionArgss={""+lstPedido.get(position).getCodProd()};

                    dbd.update(
                            "PRODUCTO",
                            values,
                            selectionn,
                            selectionArgss
                    );
                }
                lstPedido.remove(position);
                notifyDataSetChanged();


                Toast.makeText(context, "Registro eliminado satisfactoriamente!!!!",Toast.LENGTH_LONG).show();


                //i.putExtra("idAgricultor",lstPro.get(position).getCodAgri());
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstPedido.size();
    }

    public class ViewHolderCarrito extends RecyclerView.ViewHolder{
        TextView nombreP,precioU,cantidad,precio_cant,agricultor,medida;
        ImageView imagen;
        Button eliminar;

        public ViewHolderCarrito(@NonNull View itemView) {
            super(itemView);
            nombreP=(TextView) itemView.findViewById(R.id.idNombreP);
            precioU=(TextView) itemView.findViewById(R.id.idPrecioP);
            cantidad=(TextView) itemView.findViewById(R.id.idCant);
            medida=(TextView) itemView.findViewById(R.id.idMedidaP);
            precio_cant=(TextView) itemView.findViewById(R.id.idprecioParcial);
            agricultor=(TextView) itemView.findViewById(R.id.idAgricultorC);
            imagen=(ImageView) itemView.findViewById(R.id.idImagenP);
            eliminar=(Button) itemView.findViewById(R.id.idBorrarPR);
        }
    }
}
