package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itm_proyectofinal.Adapter.AdapterCompras;
import com.example.itm_proyectofinal.Adapter.AdapterVentas;
import com.example.itm_proyectofinal.Beans.Venta;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

import java.util.ArrayList;

public class MisComprasClasicoActivity extends AppCompatActivity {
   TextView regresar;
    RecyclerView ryc;
    int codCla;
    ImageView home,icon;

    ArrayList<Venta> lstVenta;
    SQLite_OpenHelper helper;
    AdapterCompras adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_compras_clasico);
        ryc=(RecyclerView) findViewById(R.id.recyclerCompras);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        home = (ImageView) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();

        if (bundle.containsKey("idClasico")){
            codCla= bundle.getInt("idClasico");
        }

        helper=new SQLite_OpenHelper(this);

        lstVenta= new ArrayList<>();
        listar();

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                //i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                //i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
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
        Cursor cursor= db.rawQuery("SELECT * FROM VENTA WHERE id_clasico="+codCla+" ORDER BY fecha_venta DESC",null);
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

        ryc.setLayoutManager(new LinearLayoutManager(MisComprasClasicoActivity.this));

        adaptador= new AdapterCompras(lstVenta,this);
        ryc.setAdapter(adaptador);

    }



}