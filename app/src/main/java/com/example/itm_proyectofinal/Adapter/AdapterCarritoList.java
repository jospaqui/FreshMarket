package com.example.itm_proyectofinal.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itm_proyectofinal.Beans.Pedido;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.example.itm_proyectofinal.R;

import java.util.ArrayList;

public class AdapterCarritoList extends BaseAdapter {
    Context context;
    ArrayList<Pedido> lstPed;

    public AdapterCarritoList(Context context, ArrayList<Pedido> lstPed) {
        this.context = context;
        this.lstPed = lstPed;
    }

    @Override
    public int getCount() {
        return lstPed.size();
    }

    @Override
    public Object getItem(int position) {
        return lstPed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pedido Item=(Pedido) getItem(position);

        convertView= LayoutInflater.from(context).inflate(R.layout.item_list_carrito,null);

        TextView nombreP=(TextView) convertView.findViewById(R.id.idNombreP);
        TextView precioU=(TextView) convertView.findViewById(R.id.idPrecioP);
        TextView cantidad=(TextView) convertView.findViewById(R.id.idCant);
        TextView medida=(TextView) convertView.findViewById(R.id.idMedidaP);
        TextView precio_cant=(TextView) convertView.findViewById(R.id.idprecioParcial);
        TextView agricultor=(TextView) convertView.findViewById(R.id.idAgricultorC);
        ImageView imagen=(ImageView) convertView.findViewById(R.id.idImagenP);
        Button eliminar=(Button) convertView.findViewById(R.id.idBorrarPR);



        final SQLite_OpenHelper helper=new SQLite_OpenHelper(context);
        Cursor c= helper.mostrarProductosxid(Item.getCodProd());
        while (c.moveToNext()){
            nombreP.setText(c.getString(1));
            precioU.setText("S/ "+c.getDouble(2));
            medida.setText(c.getString(3));
            byte[] imageBytes=c.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            imagen.setImageBitmap(objectBitmap);
            agricultor.setText(c.getString(8)+" "+c.getString(9));
        }

        cantidad.setText(""+Item.getCantidad());
        precio_cant.setText("S/ "+Item.getCant_precio());
        //notifyDataSetChanged();



        return convertView;
    }
}
