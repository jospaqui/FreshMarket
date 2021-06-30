package com.example.itm_proyectofinal.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.itm_proyectofinal.Beans.Comentario;
import com.example.itm_proyectofinal.Beans.Pedido;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.example.itm_proyectofinal.R;

import java.util.ArrayList;

public class AdapterComentarios extends BaseAdapter {
    Context context;
    ArrayList<Comentario> lstComentario;

    public AdapterComentarios(Context context, ArrayList<Comentario> lstComentario) {
        this.context = context;
        this.lstComentario = lstComentario;
    }

    @Override
    public int getCount() {
        return lstComentario.size();
    }

    @Override
    public Object getItem(int position) {
        return lstComentario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comentario cm= (Comentario)  getItem(position);

        convertView= LayoutInflater.from(context).inflate(R.layout.item_comentarios,null);

        TextView usuario=(TextView) convertView.findViewById(R.id.idUsuario);
        TextView fecha=(TextView) convertView.findViewById(R.id.idFechaComent);
        TextView comentario=(TextView) convertView.findViewById(R.id.idContenido);

        fecha.setText(cm.getFecha());
        comentario.setText(cm.getContenido());

        final SQLite_OpenHelper helper=new SQLite_OpenHelper(context);

        Cursor c= helper.obtenerUsuarioCla(cm.getIdClas());
        while (c.moveToNext()){
            usuario.setText(c.getString(1)+" "+c.getString(2));
        }


        return convertView;
    }
}
