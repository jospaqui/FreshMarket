package com.example.itm_proyectofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

public class HomeAgricultorActivity extends AppCompatActivity {
    ViewFlipper v_flipper;
    //TextView home,agregar,estadistica,producto,venta,cuenta,cerrar,nosotros;
    LinearLayout agregar,estadistica,producto,venta,cuenta,cerrar,nosotros;
    int codAgri;

    //para trabajar el spinner
    //Spinner correos;
    int i=0;
    //String[] arreglo_correos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_agricultor);
        agregar=(LinearLayout) findViewById(R.id.idAgregarProducto);
        estadistica=(LinearLayout) findViewById(R.id.idMisEstadisticasAgri);
        //producto=(TextView) findViewById(R.id.idMisProductos);
        producto=(LinearLayout) findViewById(R.id.idMisProductos);
        venta=(LinearLayout) findViewById(R.id.idMisVentas);
        cuenta=(LinearLayout) findViewById(R.id.idMicuentaAgri);
        cerrar=(LinearLayout) findViewById(R.id.idCerrarAgri);
        //correos=(Spinner) findViewById(R.id.sp_correos);
        nosotros=(LinearLayout) findViewById(R.id.idNosotros);

        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
        }

        int images[]= {R.drawable.vegetables,R.drawable.frutas,R.drawable.naranja,R.drawable.manzana,R.drawable.estadosfruta};

        v_flipper=(ViewFlipper) findViewById(R.id.vFlipper);
        for (int image:images){
            flipperImages(image);
        }


        final SQLite_OpenHelper helper=new SQLite_OpenHelper(this);

        /*Cursor c1=helper.obtenerUsuarioAgriTotales();

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
                arreglo_correos[i]=c1.getString(10);


            }
        }

        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo_correos);*/
        //correos.setAdapter(adapter);

        nosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),NosotrosAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] opciones= {"AGREGAR PRODUCTO EXISTENTE","AGREGAR PRODUCTO NUEVO","CANCELAR"};
                final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(HomeAgricultorActivity.this);
                alertOpciones.setTitle("¿Qué desea realizar?");
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which].equals("AGREGAR PRODUCTO EXISTENTE")){
                            Intent i= new Intent(getApplicationContext(),AumentarProductoAgricultorActivity.class);
                            i.putExtra("idAgricultor",codAgri);
                            startActivity(i);


                        }else {
                            if (opciones[which].equals("AGREGAR PRODUCTO NUEVO")){
                                Intent i= new Intent(getApplicationContext(),RegistrarProductoActivity.class);
                                i.putExtra("idAgricultor",codAgri);
                                startActivity(i);
                                //dialog.dismiss(); //cerrar el dialogo
                                //cod.setText(oalu.generarCodigo());
                            }
                        }
                    }
                });
                alertOpciones.show();

            }
        });
        producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),VerProductosAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });

        cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MiCuentaAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);

            }
        });

        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MisVentasAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);

            }
        });

        estadistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MisEstadisticasAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);

            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                //finishAffinity();
                //finish();
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
