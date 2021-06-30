package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

import java.util.ArrayList;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class MisEstadisticasAgricultorActivity extends AppCompatActivity {
    PieChartView pieChartView;
    SQLite_OpenHelper helper;
    int codAgri;
    TextView regresar;
    TextView Nventas,Nproductos,ingresoT,nombreProd,montoProd,cliente,veces,NombreCliente,montoCliente;
    ImageView home,icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_estadisticas_agricultor);

        //PieChartView pieChartView = findViewById(R.id.chart);


        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        pieChartView = findViewById(R.id.chart);
        Nventas=(TextView) findViewById(R.id.idCantidadVentas);
        Nproductos=(TextView) findViewById(R.id.idCantidadProductos);
        ingresoT=(TextView) findViewById(R.id.idIngresoTotal);
        nombreProd=(TextView) findViewById(R.id.idNombreProducto);
        montoProd=(TextView) findViewById(R.id.idMontoProducto);
        cliente=(TextView) findViewById(R.id.idCliente);
        NombreCliente=(TextView) findViewById(R.id.idNombreCliente);
        montoCliente=(TextView) findViewById(R.id.idMontoCliente);
        veces=(TextView) findViewById(R.id.idVeces);
        home = (ImageView) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
        }



        helper=new SQLite_OpenHelper(this);

        Cursor c=helper.cantidadxProductoVendido(codAgri);
        Cursor cv= helper.cantidadVentas(codAgri);
        Cursor cp=helper.cantidadProductos(codAgri);
        Cursor ci=helper.ingresoTotal(codAgri);
        Cursor pd=helper.productoDestacado(codAgri);
        Cursor cf=helper.clienteFrecuente(codAgri);
        Cursor cd=helper.clienteDestacado(codAgri);

        if (c!=null){

            ArrayList pieData = new ArrayList<>();
            while (c.moveToNext()){
                pieData.add(new SliceValue(c.getInt(3), getRandomColor()).setLabel(c.getString(1)+" - "+c.getInt(3)+" "+c.getString(2)));
            }
            /*int colorBlack = Color.parseColor("#0");
            PieChartData.setEntryLabelColor(colorBlack);*/


            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(20);
            //pieChartData.setHasCenterCircle(true).setCenterText1("Cantidad x Productos").setCenterText1FontSize(22).setCenterText1Color(Color.parseColor("#0097A7"));
            //pieChartData.setHasCenterCircle(true).setCenterText2("vendidos").setCenterText1FontSize(22).setCenterText2Color(Color.parseColor("#0097A7"));
            int colorWhite = Color.parseColor("#FFFFFFFF");
            pieChartData.setValueLabelsTextColor(colorWhite);
            //pieChartData.setValueLabelTextSize(20);
            pieChartView.setPieChartData(pieChartData);
        }
        if (cv!=null){
            while (cv.moveToNext()){
                Nventas.setText(""+cv.getInt(0));
            }
        }
        if (cp!=null){
            while (cp.moveToNext()){
                Nproductos.setText(""+cp.getInt(0));
            }
        }
        if (ci!=null){
            while (ci.moveToNext()){
                ingresoT.setText("S/ "+ci.getDouble(0));
            }
        }
        if (pd!=null){
            //int i=1;
            while (pd.moveToNext()){
                //if (i==1){
                nombreProd.setText(pd.getString(0));
                montoProd.setText("S/ "+pd.getDouble(1));

                //}
                break;
            }
        }
        if (cf!=null){
            while (cf.moveToNext()){
                cliente.setText(cf.getString(0)+" "+cf.getString(1));
                veces.setText(cf.getInt(2)+" veces");
                break;
            }

        }
        if (cd!=null){
            while (cd.moveToNext()){
                NombreCliente.setText(cd.getString(0)+" "+cd.getString(1));
                montoCliente.setText("S/ "+cd.getDouble(2));
                break;
            }

        }

        //ArrayList pieData = new ArrayList<>();
        //pieData.add(new SliceValue(15).setLabel("Q1: $10"));
        //pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
        //pieData.add(new SliceValue(10, Color.RED).setLabel("Q3: $18"));
        //pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));

        /*PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Cantidad x Productos vendidos").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);*/


        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });

    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(240), rnd.nextInt(155), rnd.nextInt(155));
    }
}