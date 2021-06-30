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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itm_proyectofinal.Adapter.AdapterCarrito;
import com.example.itm_proyectofinal.Adapter.AdapterCarritoList;
import com.example.itm_proyectofinal.Adapter.AdapterProductosAgricultor;
import com.example.itm_proyectofinal.Adapter.AdapterProductosComprar;
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

public class CarritoClasicoActivity extends AppCompatActivity {
    Spinner sp_depa,sp_provi;
    EditText direccion;
    RecyclerView ryc;
    Button  comprar;
    TextView montoT,delivery,montoF,regresar;
    ArrayList<Pedido> lstPedido;
    AdapterCarrito adaptador;
    ImageView home,icon;
    //AdapterCarritoList adp;
    int codCla;
    SQLite_OpenHelper helper;
    //String arr;
    //ListView lst;





    String[] arreglo_amazonas={"Elija una Provincia","Chachapoyas","Bagua","Bongara","Condorcanqui","Luya","Rodríguez de Mendoza","Utcubamba"};
    String[] arreglo_ancash={"Elija una Provincia","Huaraz","Aija","Antonio Raimondi","Asunción","Bolognesi","Carhuaz","Carlos Fermín Fitzcarrald","Casma","Corongo","Huari","Huarmey","Huaylas","Mariscal Luzuriaga","Ocros","Pallasca","Pomabamba","Recuay","Santa","Sihuas","Yungay"};
    String[] arreglo_apurimac={"Elija una Provincia","Abancay","Andahuaylas","Antabamba","Aymaraes","Cotabambas","Chincheros","Grau"};
    String[] arreglo_arequipa={"Elija una Provincia","Arequipa","Camaná","Caravelí","Castilla","Caylloma","Condesuyos","Islay","La Unión"};
    String[] arreglo_ayacucho={"Elija una Provincia","Huamanga","Cangallo","Huanca Sancos","Huanta","La Mar","Lucanas","Parinacochas","Páucar del Sara Sara","Sucre","Víctor Fajardo","Vilcashuamán"};
    String[] arreglo_cajamarca={"Elija una Provincia","Cajamarca","Cajabamba","Celendín","Chota","Contumazá","Cutervo","Hualgayoc","Jaén","San Ignacio","San Marcos","San Miguel","San Pablo","Santa Cruz"};
    String[] arreglo_callao={"Elija una Provincia","Prov. Const. del Callao"};
    String[] arreglo_cusco={"Elija una Provincia","Cuzco","Acomayo","Anta","Calca","Canas","Canchis","Chumbivilcas","Espinar","La Convención","Paruro","Paucartambo","Quispicanchi","Urubamba"};
    String[] arreglo_huancavelica={"Elija una Provincia","Huancavelica","Acobamba","Angaraes","Castrovirreyna","Churcampa","Huaytará","Tayacaja"};
    String[] arreglo_huanuco={"Elija una Provincia","Huánuco","Ambo","Dos de Mayo","Huacaybamba","Huamalíes","Leoncio Prado","Marañón","Pachitea","Puerto Inca","Lauricocha","Yarowilca"};
    String[] arreglo_ica={"Elija una Provincia","Ica","Chincha","Nazca","Palpa","Pisco"};
    String[] arreglo_junin={"Elija una Provincia","Huancayo","Chanchamayo","Chupaca","Concepción","Jauja","Junín","Satipo","Tarma","Yauli"};
    String[] arreglo_laLibertad={"Elija una Provincia","Trujillo","Ascope","Bolívar","Chepén","Gran Chimú","Julcán","Otuzco","Pacasmayo","Pataz","Sánchez Carrión","Santiago de Chuco","Virú"};
    String[] arreglo_lambayeque={"Elija una Provincia","Chiclayo","Ferreñafe","Lambayeque"};
    String[] arreglo_lima={"Elija una Provincia","Lima","Barranca","Cajatambo","Canta","Cañete","Huaral","Huarochirí","Huaura","Oyón","Yauyos"};
    String[] arreglo_loreto={"Elija una Provincia","Maynas","Alto Amazonas","Datem del Marañón","Loreto","Mariscal Ramón Castilla","Putumayo","Requena","Ucayali"};
    String[] arreglo_madreDios={"Elija una Provincia","Tambopata","Manu","Tahuamanu"};
    String[] arreglo_moquegua={"Elija una Provincia","Mariscal Nieto","General Sánchez Cerro","Ilo"};
    String[] arreglo_pasco={"Elija una Provincia","Pasco","Daniel Alcides Carrión","Oxapampa"};
    String[] arreglo_piura={"Elija una Provincia","Piura","Ayabaca","Huancabamba","Morropón","Paita","Sechura","Sullana","Talara"};
    String[] arreglo_puno={"Elija una Provincia","Puno","Azángaro","Carabaya","Chucuito","El Collao","Huancané","Lampa","Melgar","Moho","San Antonio de Putina","San Román","Sandia","Yunguyo"};
    String[] arreglo_sanMartin={"Elija una Provincia","Moyobamba","Bellavista","El Dorado","Huallaga","Lamas","Mariscal Cáceres","Picota","Rioja","San Martín","Tocache"};
    String[] arreglo_tacna={"Elija una Provincia","Tacna","Candarave","Jorge Basadre","Tarata"};
    String[] arreglo_tumbes={"Elija una Provincia","Tumbes","Contralmirante Villar","Zarumilla"};
    String[] arreglo_ucayali={"Elija una Provincia","Coronel Portillo","Atalaya","Padre Abad","Purús"};
    String[] arreglo_defecto={"Elija una Provincia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_clasico);

        sp_depa= (Spinner) findViewById(R.id.spinner_depa);
        sp_provi=(Spinner) findViewById(R.id.spinner_provincia);
        direccion=(EditText) findViewById(R.id.etUserDireccion_agri);
        ryc=(RecyclerView) findViewById(R.id.recyclerComprar);
        //lst=(ListView) findViewById(R.id.lstComprar);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        comprar=(Button) findViewById(R.id.btn_comprar);
        montoT=(TextView) findViewById(R.id.idMontoTotal);
        montoF=(TextView) findViewById(R.id.idMontoFinal);
        delivery=(TextView) findViewById(R.id.idDelivery);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idClasico")){
            codCla= bundle.getInt("idClasico");
        }
        helper= new SQLite_OpenHelper(this);
        lstPedido= new ArrayList<>();

        /*if (bundle.containsKey("Arreglo")){
            arr=bundle.getString("Arreglo");
            Gson gson= new Gson();
            Type type= new TypeToken<ArrayList<Pedido>>(){}.getType();
            lstPedido= gson.fromJson(arr,type);
            lstPedido=(ArrayList<Pedido>) bundle.getSerializable("Arreglo");
        }*/

        //ryc.setLayoutManager(new LinearLayoutManager(CarritoClasicoActivity.this));

        //adaptador= new AdapterCarrito(lstPedido,this);
        //ryc.setAdapter(adaptador);

        /*adp= new AdapterCarritoList(CarritoClasicoActivity.this,lstPedido);
        lst.setAdapter(adp);*/


        listar();

        Cursor cd=helper.consultarMontoTotal(codCla);

        if (cd==null){
            montoT.setText("");
        }else{
            while (cd.moveToNext()){
                montoT.setText(""+cd.getDouble(0));
            }
        }




        ArrayAdapter adapters= new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo_defecto);
        sp_provi.setAdapter(adapters);

        sp_depa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case (0):
                        ArrayAdapter adapters= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_defecto);
                        sp_provi.setAdapter(adapters);
                        break;
                    case(1):
                        ArrayAdapter adapter1= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_amazonas);
                        sp_provi.setAdapter(adapter1);
                        break;
                    case (2):
                        ArrayAdapter adapter2= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_ancash);
                        sp_provi.setAdapter(adapter2);
                        break;
                    case(3):
                        ArrayAdapter adapter3= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_arequipa);
                        sp_provi.setAdapter(adapter3);
                        break;
                    case (4):
                        ArrayAdapter adapter4= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_apurimac);
                        sp_provi.setAdapter(adapter4);
                        break;
                    case(5):
                        ArrayAdapter adapter5= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_ayacucho);
                        sp_provi.setAdapter(adapter5);
                        break;
                    case(6):
                        ArrayAdapter adapter6= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_cajamarca);
                        sp_provi.setAdapter(adapter6);
                        break;
                    case (7):
                        ArrayAdapter adapter7= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_callao);
                        sp_provi.setAdapter(adapter7);
                        break;
                    case(8):
                        ArrayAdapter adapter8= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_cusco);
                        sp_provi.setAdapter(adapter8);
                        break;
                    case(9):
                        ArrayAdapter adapter9= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_huancavelica);
                        sp_provi.setAdapter(adapter9);
                        break;

                    case (10):
                        ArrayAdapter adapter10= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_huanuco);
                        sp_provi.setAdapter(adapter10);
                        break;
                    case(11):
                        ArrayAdapter adapter11= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_ica);
                        sp_provi.setAdapter(adapter11);
                        break;
                    case(12):
                        ArrayAdapter adapter12= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_junin);
                        sp_provi.setAdapter(adapter12);
                        break;
                    case (13):
                        ArrayAdapter adapter13= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_laLibertad);
                        sp_provi.setAdapter(adapter13);
                        break;
                    case(14):
                        ArrayAdapter adapter14= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_lambayeque);
                        sp_provi.setAdapter(adapter14);
                        break;
                    case(15):
                        ArrayAdapter adapter15= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_lima);
                        sp_provi.setAdapter(adapter15);
                        break;
                    case (16):
                        ArrayAdapter adapter16= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_loreto);
                        sp_provi.setAdapter(adapter16);
                        break;
                    case(17):
                        ArrayAdapter adapter17= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_madreDios);
                        sp_provi.setAdapter(adapter17);
                        break;
                    case(18):
                        ArrayAdapter adapter18= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_moquegua);
                        sp_provi.setAdapter(adapter18);
                        break;
                    case (19):
                        ArrayAdapter adapter19= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_pasco);
                        sp_provi.setAdapter(adapter19);
                        break;
                    case(20):
                        ArrayAdapter adapter20= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_piura);
                        sp_provi.setAdapter(adapter20);
                        break;
                    case(21):
                        ArrayAdapter adapter21= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_puno);
                        sp_provi.setAdapter(adapter21);
                        break;
                    case (22):
                        ArrayAdapter adapter22= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_sanMartin);
                        sp_provi.setAdapter(adapter22);
                        break;
                    case(23):
                        ArrayAdapter adapter23= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_tacna);
                        sp_provi.setAdapter(adapter23);
                        break;
                    case (24):
                        ArrayAdapter adapter24= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_tumbes);
                        sp_provi.setAdapter(adapter24);
                        break;
                    case(25):
                        ArrayAdapter adapter25= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arreglo_ucayali);
                        sp_provi.setAdapter(adapter25);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_provi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_depa.getSelectedItemPosition()!=0){
                    if (position!=0){
                        if (sp_depa.getSelectedItemPosition()>0 && sp_depa.getSelectedItemPosition()<5){
                            delivery.setText("5.50");
                            double mon= Double.parseDouble(montoT.getText().toString());
                            if (mon>0){
                                montoF.setText(""+(mon+Double.parseDouble(delivery.getText().toString())));
                            }else{
                                montoF.setText("");
                            }

                        }else{
                            if (sp_depa.getSelectedItemPosition()>=5 && sp_depa.getSelectedItemPosition()<10){
                                delivery.setText("6.50");
                                double mon= Double.parseDouble(montoT.getText().toString());
                                if (mon>0){
                                    montoF.setText(""+(mon+Double.parseDouble(delivery.getText().toString())));
                                }else{
                                    montoF.setText("");
                                }
                            }else{
                                if (sp_depa.getSelectedItemPosition()>=10 && sp_depa.getSelectedItemPosition()<15){
                                    delivery.setText("7.00");
                                    double mon= Double.parseDouble(montoT.getText().toString());
                                    if (mon>0){
                                        montoF.setText(""+(mon+Double.parseDouble(delivery.getText().toString())));
                                    }else{
                                        montoF.setText("");
                                    }
                                }else{
                                    if (sp_depa.getSelectedItemPosition()>=15 && sp_depa.getSelectedItemPosition()<20){
                                        delivery.setText("7.50");
                                        double mon= Double.parseDouble(montoT.getText().toString());
                                        if (mon>0){
                                            montoF.setText(""+(mon+Double.parseDouble(delivery.getText().toString())));
                                        }else{
                                            montoF.setText("");
                                        }
                                    }else{
                                        delivery.setText("8.50");
                                        double mon= Double.parseDouble(montoT.getText().toString());
                                        if (mon>0){
                                            montoF.setText(""+(mon+Double.parseDouble(delivery.getText().toString())));
                                        }else{
                                            montoF.setText("");
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        delivery.setText("");
                    }
                }else{
                    delivery.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                /*Gson gson=new Gson();
                String arr1= gson.toJson(lstPedido);
                i.putExtra("Arreglo",arr1);*/
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                /*Gson gson=new Gson();
                String arr1= gson.toJson(lstPedido);
                i.putExtra("Arreglo",arr1);*/
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
                startActivity(i);
            }
        });

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!direccion.getText().toString().isEmpty()){
                    if (sp_depa.getSelectedItemPosition()!=0 && sp_provi.getSelectedItemPosition()!=0){
                        if (Double.parseDouble(montoT.getText().toString())>0 && Double.parseDouble(delivery.getText().toString())>0 && Double.parseDouble(montoF.getText().toString())>0){
                            Cursor c=helper.consultarPedidoxID(codCla);
                            while (c.moveToNext()){
                                SQLiteDatabase db1=helper.getWritableDatabase();
                                ContentValues valuess=new ContentValues();
                                valuess.put("id_agricultor",c.getInt(0));
                                valuess.put("id_clasico",c.getInt(1));
                                valuess.put("costo_delivery",Double.parseDouble(delivery.getText().toString()));
                                valuess.put("monto_total",Double.parseDouble(montoT.getText().toString()));
                                valuess.put("departamento_entrega",sp_depa.getSelectedItem().toString());
                                valuess.put("provincia_entrega",sp_provi.getSelectedItem().toString());
                                valuess.put("direccion_entrega",direccion.getText().toString());
                                //valuess.put("fecha_venta",obtenerFechaConFormato("YYYY-MM-dd HH:mm:ss","America/Lima"));
                                valuess.put("fecha_venta",obtenerFechaConFormato("dd-MM-YYYY HH:mm:ss","America/Lima"));
                                db1.insert("VENTA",null,valuess);

                                Cursor cp=helper.consultarPedidoxIDClaxAgri(c.getInt(1),c.getInt(0));
                                Cursor cid=helper.maxIdVenta();
                                while (cid.moveToNext()){
                                    while (cp.moveToNext()){
                                        SQLiteDatabase db2=helper.getWritableDatabase();
                                        ContentValues values=new ContentValues();
                                        values.put("id_v",cid.getInt(0));
                                        values.put("id_prodc",cp.getInt(1));
                                        values.put("cantidad",cp.getInt(4));
                                        values.put("precio_cant_prodc",cp.getDouble(5));
                                        db2.insert("VENTA_DETALLE",null,values);

                                    }
                                }


                                //db1.insert("PEDIDO_PRUEBA",null,valuess);
                            }
                            SQLiteDatabase db3= helper.getWritableDatabase();
                            String selection="id_clasico_pe= ?";
                            String[] selectionArgs={""+codCla};
                            db3.delete("PEDIDO_PRUEBA",selection,selectionArgs);
                            sp_depa.setSelection(0);
                            sp_provi.setSelection(0);
                            direccion.setText("");
                            lstPedido= new ArrayList<>();
                            listar();

                            Toast.makeText(getApplicationContext(),"Compra realizada satisfactoriamente",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"No hay productos para su compra",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Debe seleccionar un departamento y provincia",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Debe completar la direccion",Toast.LENGTH_SHORT).show();
                }



                //1ero agrupar por codClasico y codAgri en PEDIDO_PRUEBA WHERE codClasico= codCla


                //



                /*ultimo eliminartodo los registros WHERE id_clasico_pe=codCla en PEDIDO_PRUEBA*/

            }
        });

    }

    public void listar(){
        SQLiteDatabase db= helper.getReadableDatabase();
        Pedido p=null;
        Cursor cursor= db.rawQuery("SELECT * FROM PEDIDO_PRUEBA WHERE id_clasico_pe="+codCla+" ORDER BY id_pedido DESC",null);
        //Cursor cursor= db.rawQuery("SELECT * FROM "+ Estructura_BBDD.TABLE_NAME,null);
        while (cursor.moveToNext()){
            p= new Pedido();
            p.setCodPedi(cursor.getInt(0));
            p.setCodProd(cursor.getInt(1));
            p.setCodAgri(cursor.getInt(2));
            p.setCodClas(cursor.getInt(3));
            p.setCantidad(cursor.getInt(4));
            p.setCant_precio(cursor.getDouble(5));
            lstPedido.add(p);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(CarritoClasicoActivity.this));

        adaptador= new AdapterCarrito(lstPedido,this);
        ryc.setAdapter(adaptador);

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