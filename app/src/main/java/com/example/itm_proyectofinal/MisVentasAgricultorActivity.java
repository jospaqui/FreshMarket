package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itm_proyectofinal.Adapter.AdapterProductosComprar;
import com.example.itm_proyectofinal.Adapter.AdapterVentas;
import com.example.itm_proyectofinal.Beans.Producto;
import com.example.itm_proyectofinal.Beans.Venta;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

import java.util.ArrayList;

public class MisVentasAgricultorActivity extends AppCompatActivity {
    TextView regresar;
    RecyclerView ryc;
    int codAgri;
    ImageView home,icon;

    ArrayList<Venta> lstVenta;
    SQLite_OpenHelper helper;
    AdapterVentas adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_ventas_agricultor);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        ryc=(RecyclerView) findViewById(R.id.recyclerVentas);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);

        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
        }

        helper=new SQLite_OpenHelper(this);

        lstVenta= new ArrayList<>();
        listar();


        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                //i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                //i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                //i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });

    }

    public void listar(){
        SQLiteDatabase db= helper.getReadableDatabase();
        Venta p=null;
        //Cursor cursor= helper.consultarVentaxAgri(codAgri);
        Cursor cursor= db.rawQuery("SELECT * FROM VENTA WHERE id_agricultor="+codAgri+" ORDER BY fecha_venta DESC",null);
        while (cursor.moveToNext()){
            p= new Venta();
            p.setId_venta(cursor.getInt(0));
            p.setId_agri(cursor.getInt(1));
            p.setId_clas(cursor.getInt(2));
            p.setDelivery(cursor.getDouble(3));
            p.setMonto(cursor.getDouble(4));
            p.setDepart(cursor.getString(5));
            p.setProvincia(cursor.getString(6));
            p.setDireccion(cursor.getString(7));
            p.setFecha(cursor.getString(8));
            lstVenta.add(p);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(MisVentasAgricultorActivity.this));

        adaptador= new AdapterVentas(lstVenta,this);
        ryc.setAdapter(adaptador);

    }
}