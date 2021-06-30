package com.example.itm_proyectofinal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itm_proyectofinal.Beans.Pedido;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class HomeClasicoActivity extends AppCompatActivity {
    int codCla;
    ViewFlipper v_flipper;
    //TextView home,producto,compras,cuenta,cerrar,carrito;
    LinearLayout producto,compras,cuenta,cerrar,carrito,nosotros;
    //ArrayList<Pedido> lstPedido;
    //para trabajar el spinner
    //Spinner correos;
    int i=0;
    //String arr;
    //String[] arreglo_correos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_clasico);
        producto=(LinearLayout) findViewById(R.id.idProductosClasi);
        compras=(LinearLayout) findViewById(R.id.idMisCompras);
        cuenta=(LinearLayout) findViewById(R.id.idMicuentaClasi);
        cerrar=(LinearLayout) findViewById(R.id.idCerrarClasi);
        //correos=(Spinner) findViewById(R.id.sp_correosClasico);
        carrito=(LinearLayout) findViewById(R.id.idCarrito);
        nosotros=(LinearLayout) findViewById(R.id.idNosotros);

        Bundle bundle=getIntent().getExtras();

        if (bundle.containsKey("idClasico")){
            codCla= bundle.getInt("idClasico");
        }
        /*if (bundle.containsKey("Arreglo")){
            lstPedido=(ArrayList<Pedido>) bundle.get("Arreglo");
        }*/

        /*if (bundle.containsKey("Arreglo")){
            arr=bundle.getString("Arreglo");
            //lstPedido=(ArrayList<Pedido>) bundle.getSerializable("Arreglo");
        }*/



        int images[]= {R.drawable.vegetables,R.drawable.frutas,R.drawable.naranja,R.drawable.manzana,R.drawable.estadosfruta};

        v_flipper=(ViewFlipper) findViewById(R.id.vFlipper);
        for (int image:images){
            flipperImages(image);
        }


        final SQLite_OpenHelper helper=new SQLite_OpenHelper(this);

        /*Cursor c1=helper.obtenerUsuarioClaTotales();

        if (c1==null){
            arreglo_correos= new String[1];
            arreglo_correos[0]="Elija un correo";
        }else{
            while (c1.moveToNext()) {

                if (i==0){
                    arreglo_correos= new String[c1.getCount()+1];
                    arreglo_correos[i]="Elija un correo";
                }
                i=i+1;
                arreglo_correos[i]=c1.getString(6);


            }
        }

        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo_correos);
        correos.setAdapter(adapter);*/

        nosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),NosotrosClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });


        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),CarritoClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });

        cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MiCuentaClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });
        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),ProductosClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });

        compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MisComprasClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });




        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                //i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });



    }


    public void flipperImages(int image){
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(5000);
        v_flipper.setAutoStart(true);

    }
}
