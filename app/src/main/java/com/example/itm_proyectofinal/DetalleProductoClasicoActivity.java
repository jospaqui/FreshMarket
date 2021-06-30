package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itm_proyectofinal.Adapter.AdapterComentarios;
import com.example.itm_proyectofinal.Adapter.AdapterProductosAgricultor;
import com.example.itm_proyectofinal.Adapter.AdapterProductosComprar;
import com.example.itm_proyectofinal.Beans.Comentario;
import com.example.itm_proyectofinal.Beans.Pedido;
import com.example.itm_proyectofinal.Beans.Producto;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DetalleProductoClasicoActivity extends AppCompatActivity {
    int codProd,codAgri,codCla;
    Button agregar,comentar;
    ImageView imagen;
    ImageView home,icon;
    TextView nombre,descripcion,precio,medida,agricultor,stock,medida_2,regresar;
    EditText cantidad,comentario;
    //RecyclerView ryc_comen;
    //ArrayList<Pedido> lstPedido;
    ListView list;
    double prec;
    //String arr;

    ArrayList<Comentario> lstComentario;
    AdapterComentarios adapter;
    SQLite_OpenHelper helper;
    //AdapterComentarios adaptador;
    //falta crear el arreglo de comentarios

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto_clasico);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        agregar=(Button) findViewById(R.id.idAgregar);
        comentar=(Button) findViewById(R.id.idComentar);
        imagen=(ImageView) findViewById(R.id.idImagenP);
        nombre=(TextView) findViewById(R.id.idNombreP);
        descripcion=(TextView) findViewById(R.id.descripcionP);
        precio=(TextView) findViewById(R.id.idPrecioP);
        medida=(TextView) findViewById(R.id.idMedidaP);
        agricultor=(TextView) findViewById(R.id.idAgricultor);
        stock=(TextView) findViewById(R.id.idStockP);
        cantidad=(EditText) findViewById(R.id.stock_prod_aumentar);
        comentario=(EditText) findViewById(R.id.descripcion_prod);
        list=(ListView) findViewById(R.id.LstComentario);
        //ryc_comen=(RecyclerView) findViewById(R.id.recyclerComentario);
        medida_2=(TextView) findViewById(R.id.idMedida2);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idProducto")){
            codProd= bundle.getInt("idProducto");
            //bundle.remove("Mensaje");
            //cantidad.setText(""+codProd);
        }

        if (bundle.containsKey("idClasico")){
            codCla= bundle.getInt("idClasico");
        }

        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
            //bundle.remove("Mensaje");
        }

        /*if (bundle.containsKey("Arreglo")){
            arr=bundle.getString("Arreglo");
            Gson gson= new Gson();
            Type type= new TypeToken<ArrayList<Pedido>>(){}.getType();
            lstPedido= gson.fromJson(arr,type);
            //lstPedido=(ArrayList<Pedido>) bundle.getSerializable("Arreglo");
        }*/

        helper=new SQLite_OpenHelper(this);



        completar();
        listar();

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),ProductosClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                /*Gson gson=new Gson();
                String arr1= gson.toJson(lstPedido);
                i.putExtra("Arreglo",arr1);*/
                //i.putExtra("Arreglo",lstPedido);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),ProductosClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                /*Gson gson=new Gson();
                String arr1= gson.toJson(lstPedido);
                i.putExtra("Arreglo",arr1);*/
                //i.putExtra("Arreglo",lstPedido);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                /*Gson gson=new Gson();
                String arr1= gson.toJson(lstPedido);
                i.putExtra("Arreglo",arr1);*/
                //i.putExtra("Arreglo",lstPedido);
                startActivity(i);
            }
        });
        //lstProducto= new ArrayList<>();

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cantidad.getText().toString().isEmpty()){
                    if (Integer.parseInt(cantidad.getText().toString())>0){
                        Cursor c=helper.consultarProductosxCant(codProd,Integer.parseInt(cantidad.getText().toString()));
                        if (c==null){
                            Toast.makeText(getApplicationContext(),"La cantidad excede al stock!!!",Toast.LENGTH_SHORT).show();
                            cantidad.setText("");
                            cantidad.requestFocus();
                        }else{
                            int cant;
                            int cant_total;
                            Cursor cp=helper.consultarProductos(codProd);
                            while (cp.moveToNext()){
                                cant=cp.getInt(4);
                                cant_total=cant-Integer.parseInt(cantidad.getText().toString());
                                SQLiteDatabase db= helper.getReadableDatabase();
                                ContentValues values= new ContentValues();
                                values.put("stock_prod",cant_total);
                                //which row to update based on the title
                                String selection="id_prod= ?";
                                String[] selectionArgs={""+codProd};

                                db.update(
                                        "PRODUCTO",
                                        values,
                                        selection,
                                        selectionArgs
                                );
                            }
                            Cursor cu=helper.consultarPedidoPrueba(codProd,codCla);

                            if (cu==null){
                                //agregar
                                SQLiteDatabase db1=helper.getWritableDatabase();
                                ContentValues valuess=new ContentValues();
                                valuess.put("id_producto_pe",codProd);
                                valuess.put("id_agricul_pe",codAgri);
                                valuess.put("id_clasico_pe",codCla);
                                valuess.put("cantidad_pe",cantidad.getText().toString());
                                valuess.put("precio_parcial_pe",prec*Integer.parseInt(cantidad.getText().toString()));

                                db1.insert("PEDIDO_PRUEBA",null,valuess);

                            }else{
                                //actualizar
                                int cant_p;

                                while (cu.moveToNext()){
                                    cant_p=cu.getInt(4);
                                    SQLiteDatabase dbb= helper.getReadableDatabase();
                                    ContentValues valuesx= new ContentValues();
                                    valuesx.put("cantidad_pe",cant_p+Integer.parseInt(cantidad.getText().toString()));
                                    //which row to update based on the title
                                    String selection="id_producto_pe= ? and id_clasico_pe= ?";
                                    String[] selectionArgs={""+codProd,""+codCla};

                                    dbb.update(
                                            "PEDIDO_PRUEBA",
                                            valuesx,
                                            selection,
                                            selectionArgs
                                    );
                                }


                            }
                            /*Pedido p= new Pedido();
                            p.setCodProd(codProd);
                            p.setCodAgri(codAgri);
                            p.setCodClas(codCla);
                            p.setCantidad(Integer.parseInt(cantidad.getText().toString()));
                            p.setCant_precio(p.getCantidad()*prec);*/
                            //lstPedido.add(p);
                            completar();
                            Toast.makeText(getApplicationContext(),"Se agrego correctamente el producto",Toast.LENGTH_SHORT).show();
                            cantidad.setText("");
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"La cantidad debe ser > 0",Toast.LENGTH_SHORT).show();
                        cantidad.setText("");
                        cantidad.requestFocus();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Debe completar el campo!!!",Toast.LENGTH_SHORT).show();
                    cantidad.requestFocus();
                }
            }
        });

        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comentario.getText().toString().isEmpty()){
                    SQLiteDatabase db1=helper.getWritableDatabase();
                    ContentValues valuess=new ContentValues();
                    valuess.put("descrip_comen",comentario.getText().toString());
                    valuess.put("id_us_clasic",codCla);
                    valuess.put("id_producto",codProd);
                    valuess.put("fecha_comen",obtenerFechaConFormato("dd-MM-YYYY HH:mm:ss","America/Lima"));
                    db1.insert("COMENTARIO",null,valuess);
                    listar();

                    Toast.makeText(getApplicationContext(),"Se registro correctamente su comentario!!!",Toast.LENGTH_SHORT).show();
                    comentario.setText("");

                }else{
                    Toast.makeText(getApplicationContext(),"Debe escribir un comentario!!!",Toast.LENGTH_SHORT).show();
                    //comentario.requestFocus();
                }

            }
        });


    }


    public void completar(){
        SQLiteDatabase db= helper.getReadableDatabase();
        //Producto pro=null;
        Cursor cursor= helper.mostrarProductosxid(codProd);
        //Cursor cursor= db.rawQuery("SELECT * FROM "+ Estructura_BBDD.TABLE_NAME,null);
        while (cursor.moveToNext()){
            //pro= new Producto();
            //pro.setCodigo(cursor.getInt(0));
            nombre.setText(cursor.getString(1));
            precio.setText("S/ "+cursor.getDouble(2));
            prec=cursor.getDouble(2);
            medida.setText("/ "+cursor.getString(3));
            stock.setText(""+cursor.getInt(4));
            byte[] imageBytes=cursor.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            imagen.setImageBitmap(objectBitmap);
            medida_2.setText(cursor.getString(3));
            descripcion.setText(cursor.getString(6));
            agricultor.setText(cursor.getString(8)+" "+cursor.getString(9));
            //pro.setCodAgri(cursor.getInt(7));
            //lstProducto.add(pro);
        }
    }

    public void listar(){
        lstComentario= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Comentario comen=null;
        Cursor cursor= db.rawQuery("SELECT * FROM COMENTARIO WHERE id_producto="+codProd+" ORDER BY fecha_comen DESC",null);
        //Cursor cursor= db.rawQuery("SELECT * FROM "+ Estructura_BBDD.TABLE_NAME,null);
        //lstComentario.clear();
        while (cursor.moveToNext()){
            comen= new Comentario();
            comen.setIdComent(cursor.getInt(0));
            comen.setContenido(cursor.getString(1));
            comen.setIdClas(cursor.getInt(2));
            comen.setIdProd(cursor.getInt(3));
            comen.setFecha(cursor.getString(4));
            lstComentario.add(comen);
        }
        //ryc.setHasFixedSize(true);

        adapter= new AdapterComentarios(DetalleProductoClasicoActivity.this,lstComentario);
        list.setAdapter(adapter);

    }

    @SuppressLint("SimpleDateFormat")
    public static String obtenerFechaConFormato(String formato, String zonaHoraria) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone(zonaHoraria));
        return sdf.format(date);
    }




}