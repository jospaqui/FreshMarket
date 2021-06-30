package com.example.itm_proyectofinal.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.itm_proyectofinal.Beans.VentaDetalle;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.example.itm_proyectofinal.R;

import java.util.ArrayList;

public class AdapterVentaDetalleList extends BaseAdapter {
    Context context;
    ArrayList<VentaDetalle> lstVDeta;

    public AdapterVentaDetalleList(Context context, ArrayList<VentaDetalle> lstVDeta) {
        this.context = context;
        this.lstVDeta = lstVDeta;
    }

    @Override
    public int getCount() {
        return lstVDeta.size();
    }

    @Override
    public Object getItem(int position) {
        return lstVDeta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VentaDetalle Item=(VentaDetalle) getItem(position);
        //obtener la vista a llenar para este caso:item_producto
        convertView= LayoutInflater.from(context).inflate(R.layout.item_venta_detalle,null);
        //alinear el diseño con la programacion
        TextView nom=(TextView) convertView.findViewById(R.id.idProd);
        TextView preciU= (TextView) convertView.findViewById(R.id.idPre);
        TextView cant=(TextView) convertView.findViewById(R.id.idCanti);
        TextView medida=(TextView) convertView.findViewById(R.id.idMed);
        TextView precioCP=(TextView) convertView.findViewById(R.id.idPreCant);

        final SQLite_OpenHelper helper=new SQLite_OpenHelper(context);
        Cursor c1=helper.consultarProductos(Item.getId_prod());
        while (c1.moveToNext()){
            nom.setText(c1.getString(1));
            preciU.setText(""+c1.getDouble(2));
            cant.setText(""+Item.getCantidad());
            medida.setText(c1.getString(3));
            precioCP.setText(""+Item.getPrecio_cant());
        }


        return convertView;
    }
}
