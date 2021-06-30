package com.example.itm_proyectofinal;

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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itm_proyectofinal.Adapter.AdapterProductosAgricultor;
import com.example.itm_proyectofinal.Adapter.AdapterProductosComprar;
import com.example.itm_proyectofinal.Beans.Pedido;
import com.example.itm_proyectofinal.Beans.Producto;
import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductosClasicoActivity extends AppCompatActivity {
    TextView regresar;
    Spinner sp_agri,sp_tipo,sp_precio;
    RecyclerView ryc;
    EditText buscar;
    ImageView home,icon;

    //ArrayList<Pedido> lstPedido;
    //String arr;

    ArrayList<Producto> lstProducto;
    SQLite_OpenHelper helper;
    AdapterProductosComprar adaptador;

    int i=0;
    int codCla;
    String[] arreglo_tipo={"Tipo de agricultor","Agricultor Tradicional","Agricultor Industrial"};
    String[] arreglo_agri;
    String[] arreglo_precio={"Tipo de precio","Precio bajo","Precio regular","Precio alto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        sp_agri=(Spinner) findViewById(R.id.spinner_agricultor);
        sp_tipo=(Spinner) findViewById(R.id.spinner_tipo);
        sp_precio=(Spinner) findViewById(R.id.spinner_precio);
        ryc=(RecyclerView) findViewById(R.id.recyclerProdCLa);
        buscar=(EditText) findViewById(R.id.nombre_prodc);
        home = (ImageView) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idClasico")){
            codCla= bundle.getInt("idClasico");
        }
        /*if (bundle.containsKey("Arreglo")){
            arr=bundle.getString("Arreglo");*/
            //Gson gson= new Gson();
            //Type type= new TypeToken<ArrayList<Pedido>>(){}.getType();
            //lstPedido= gson.fromJson(arr,type);
            //lstPedido=(ArrayList<Pedido>) bundle.getSerializable("Arreglo");
        //}
        //lstProducto= new ArrayList<>();

        helper=new SQLite_OpenHelper(this);

        //lstProducto= new ArrayList<>();

        Cursor c1=helper.obtenerUsuarioAgriTotalesAgrupado();

        if (c1==null){
            arreglo_agri= new String[1];
            arreglo_agri[0]="Nombre Agricultor";
        }else{
            while (c1.moveToNext()) {

                if (i==0){
                    arreglo_agri= new String[c1.getCount()+1];
                    arreglo_agri[i]="Nombre Agricultor";
                }
                i=i+1;
                arreglo_agri[i]=c1.getString(0)+" "+c1.getString(1);


            }
        }

        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo_agri);
        sp_agri.setAdapter(adapter);

        ArrayAdapter adapter1= new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo_tipo);
        sp_tipo.setAdapter(adapter1);

        ArrayAdapter adapter2= new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo_precio);
        sp_precio.setAdapter(adapter2);

        //SQLiteDatabase db= helper.getReadableDatabase();
        //Cursor cursor= helper.mostrarProductos();
        /*Cursor cursory= db.rawQuery("SELECT * FROM REGISTRO",null);
        if (cursory.getCount()<=0){
            lstProducto= new ArrayList<>();

            ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

            adaptador= new AdapterProductosComprar(lstProducto,this);
            ryc.setAdapter(adaptador);
        }*/



        sp_agri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0 && sp_precio.getSelectedItemPosition()==0 && sp_tipo.getSelectedItemPosition()==0){
                    // filtro sin restriccion
                    listar();
                }else{
                    if (position==0 && sp_precio.getSelectedItemPosition()==0 && sp_tipo.getSelectedItemPosition()!=0){
                        //filtro con resticcion en solo tipo
                        //lstProducto= new ArrayList<>();
                        listarSoloTipo(sp_tipo.getSelectedItem().toString());

                    }else{
                        if (position==0 && sp_precio.getSelectedItemPosition()!=0 && sp_tipo.getSelectedItemPosition()==0){
                            // filtro con restriccion en solo precio ver 3 casos
                            if (sp_precio.getSelectedItemPosition()==1){
                                listarSoloPrecioBajo();
                            }else{
                                if (sp_precio.getSelectedItemPosition()==2){
                                    listarSoloPrecioRegular();
                                }else{
                                    listarSoloPrecioAlto();
                                }
                            }
                        }else{
                            if (position!=0 && sp_precio.getSelectedItemPosition()==0 && sp_tipo.getSelectedItemPosition()==0){
                                // filtro por solo agricultor por nombre
                                listarSoloAgri(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1]);
                            }else{
                                if (position!=0 && sp_precio.getSelectedItemPosition()!=0 && sp_tipo.getSelectedItemPosition()==0){
                                    // filtro de agricultor y ver las 3 comibanciones de precio
                                    if (sp_precio.getSelectedItemPosition()==1){
                                        listarDobleAgriPrecioBajo(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1]);
                                    }else{
                                        if (sp_precio.getSelectedItemPosition()==2){
                                            listarDobleAgriPrecioRegular(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1]);
                                        }else{
                                            listarDobleAgriPrecioAlto(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1]);
                                        }
                                    }
                                }else{
                                    if (position!=0 && sp_precio.getSelectedItemPosition()==0 && sp_tipo.getSelectedItemPosition()!=0){
                                        //flitro de agricultor y tipo
                                        listarDobleAgriTipo(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());
                                    }else{
                                        if (position==0 && sp_precio.getSelectedItemPosition()!=0 && sp_tipo.getSelectedItemPosition()!=0){
                                            //filtro precio con tipo , ver las 3 comibanciones del precio
                                            if (sp_precio.getSelectedItemPosition()==1){
                                                listarDobleTipoPrecioBajo(sp_tipo.getSelectedItem().toString());
                                            }else{
                                                if (sp_precio.getSelectedItemPosition()==2){
                                                    listarDobleTipoPrecioRegular(sp_tipo.getSelectedItem().toString());
                                                }else{
                                                    listarDobleTipoPrecioAlto(sp_tipo.getSelectedItem().toString());
                                                }
                                            }
                                        }else{
                                            if (position!=0 && sp_precio.getSelectedItemPosition()!=0 && sp_tipo.getSelectedItemPosition()!=0){
                                                //filtro de los 3 con combinaciones del precio
                                                if (sp_precio.getSelectedItemPosition()==1){
                                                    listarTripleAgriTipoPrecioBajo(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());

                                                }else{
                                                    if (sp_precio.getSelectedItemPosition()==2){
                                                        listarTripleAgriTipoPrecioRegular(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());

                                                    }else{

                                                        listarTripleAgriTipoPrecioAlto(parent.getSelectedItem().toString().split(" ")[0],parent.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());
                                                    }
                                                }
                                            }
                                            //ver caso del buscador por nombre
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0 && sp_precio.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()==0){
                    // filtro sin restriccion
                    listar();
                }else{
                    if (position==0 && sp_precio.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()!=0){

                        listarSoloAgri(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);

                    }else{
                        if (position==0 && sp_precio.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()==0){
                            // filtro con restriccion en solo precio ver 3 casos
                            if (sp_precio.getSelectedItemPosition()==1){
                                listarSoloPrecioBajo();
                            }else{
                                if (sp_precio.getSelectedItemPosition()==2){
                                    listarSoloPrecioRegular();
                                }else{
                                    listarSoloPrecioAlto();
                                }
                            }
                        }else{
                            if (position!=0 && sp_precio.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()==0){
                                listarSoloTipo(parent.getSelectedItem().toString());
                            }else{
                                if (position!=0 && sp_precio.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()==0){
                                    if (sp_precio.getSelectedItemPosition()==1){
                                        listarDobleTipoPrecioBajo(parent.getSelectedItem().toString());
                                    }else{
                                        if (sp_precio.getSelectedItemPosition()==2){
                                            listarDobleTipoPrecioRegular(parent.getSelectedItem().toString());
                                        }else{
                                            listarDobleTipoPrecioAlto(parent.getSelectedItem().toString());
                                        }
                                    }
                                }else{
                                    if (position!=0 && sp_precio.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()!=0){
                                        listarDobleAgriTipo(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],parent.getSelectedItem().toString());
                                    }else{
                                        if (position==0 && sp_precio.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()!=0){
                                            if (sp_precio.getSelectedItemPosition()==1){
                                                listarDobleAgriPrecioBajo(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);
                                            }else{
                                                if (sp_precio.getSelectedItemPosition()==2){
                                                    listarDobleAgriPrecioRegular(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);
                                                }else{
                                                    listarDobleAgriPrecioAlto(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);
                                                }
                                            }
                                        }else{
                                            if (position!=0 && sp_precio.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()!=0){
                                                //filtro de los 3 con combinaciones del precio
                                                if (sp_precio.getSelectedItemPosition()==1){
                                                    listarTripleAgriTipoPrecioBajo(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],parent.getSelectedItem().toString());

                                                }else{
                                                    if (sp_precio.getSelectedItemPosition()==2){
                                                        listarTripleAgriTipoPrecioRegular(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],parent.getSelectedItem().toString());

                                                    }else{

                                                        listarTripleAgriTipoPrecioAlto(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],parent.getSelectedItem().toString());
                                                    }
                                                }
                                            }
                                            //ver caso del buscador por nombre
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_precio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0 && sp_tipo.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()==0){
                    // filtro sin restriccion
                    listar();
                }else{
                    if (position==0 && sp_tipo.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()!=0){

                        listarSoloAgri(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);

                    }else{
                        if (position==0 && sp_tipo.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()==0){
                            // filtro con restriccion en solo precio ver 3 casos
                            listarSoloTipo(sp_tipo.getSelectedItem().toString());
                        }else{
                            if (position!=0 && sp_tipo.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()==0){
                                if (position==1){
                                    listarSoloPrecioBajo();
                                }else{
                                    if (position==2){
                                        listarSoloPrecioRegular();
                                    }else{
                                        listarSoloPrecioAlto();
                                    }
                                }
                            }else{
                                if (position!=0 && sp_tipo.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()==0){
                                    if (position==1){
                                        listarDobleTipoPrecioBajo(sp_tipo.getSelectedItem().toString());
                                    }else{
                                        if (position==2){
                                            listarDobleTipoPrecioRegular(sp_tipo.getSelectedItem().toString());
                                        }else{
                                            listarDobleTipoPrecioAlto(sp_tipo.getSelectedItem().toString());
                                        }
                                    }
                                }else{
                                    if (position!=0 && sp_tipo.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()!=0){
                                        if (position==1){
                                            listarDobleAgriPrecioBajo(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);
                                        }else{
                                            if (position==2){
                                                listarDobleAgriPrecioRegular(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);
                                            }else{
                                                listarDobleAgriPrecioAlto(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1]);
                                            }
                                        }

                                    }else{
                                        if (position==0 && sp_tipo.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()!=0){
                                            listarDobleAgriTipo(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());
                                        }else{
                                            if (position!=0 && sp_tipo.getSelectedItemPosition()!=0 && sp_agri.getSelectedItemPosition()!=0){
                                                //filtro de los 3 con combinaciones del precio
                                                if (position==1){
                                                    listarTripleAgriTipoPrecioBajo(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());

                                                }else{
                                                    if (position==2){
                                                        listarTripleAgriTipoPrecioRegular(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());

                                                    }else{

                                                        listarTripleAgriTipoPrecioAlto(sp_agri.getSelectedItem().toString().split(" ")[0],sp_agri.getSelectedItem().toString().split(" ")[1],sp_tipo.getSelectedItem().toString());
                                                    }
                                                }
                                            }
                                            //ver caso del buscador por nombre
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterProd(s.toString());

            }
        });
        if (sp_precio.getSelectedItemPosition()==0 && sp_tipo.getSelectedItemPosition()==0 && sp_agri.getSelectedItemPosition()==0){
            listar();
        }

        //ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        //adaptador= new AdapterProductosComprar(lstProducto,this);
        //ryc.setAdapter(adaptador);
        //adaptador.notifyDataSetChanged();





        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                //i.putExtra("Arreglo",arr);
                startActivity(i);
            }
        });
    }

    private void filterProd(String text){
        ArrayList<Producto> filtroProducto= new ArrayList<>();

        for (Producto objPro: lstProducto){
            if (objPro.getNombre().toLowerCase().contains(text.toLowerCase())){
                filtroProducto.add(objPro);
            }
        }

        adaptador.filterLisProducts(filtroProducto);


    }


    public void listar(){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursor= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 ORDER BY R.fecha_reg DESC",null);
        while (cursor.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursor.getInt(0));
            pro.setNombre(cursor.getString(1));
            pro.setPrecio(cursor.getDouble(2));
            pro.setCriterio_medida(cursor.getString(3));
            pro.setStock(cursor.getInt(4));
            byte[] imageBytes=cursor.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursor.getString(6));
            pro.setCodAgri(cursor.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));


        adaptador= new AdapterProductosComprar(lstProducto,this);

        ryc.setAdapter(adaptador);
        //adaptador.notifyDataSetChanged();

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });


    }

    public void listarSoloTipo(String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.tipo_agri LIKE '"+tipo+"' ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        //adaptador.notifyDataSetChanged();
        ryc.setAdapter(adaptador);
        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });


    }


    public void listarSoloPrecioBajo(){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND P.precio_prod<=7 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        //adaptador.notifyDataSetChanged();
        ryc.setAdapter(adaptador);
        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });


    }

    public void listarSoloPrecioRegular(){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND P.precio_prod>7 AND P.precio_prod<=15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        //adaptador.notifyDataSetChanged();
        ryc.setAdapter(adaptador);
        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });


    }
    public void listarSoloPrecioAlto(){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND P.precio_prod>15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        //adaptador.notifyDataSetChanged();
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });


    }

    public void listarSoloAgri(String nombre,String apellido){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));


        adaptador= new AdapterProductosComprar(lstProducto,this);

        ryc.setAdapter(adaptador);
        //adaptador.notifyDataSetChanged();

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });


    }

    public void listarDobleAgriPrecioBajo(String nombre,String apellido){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' AND P.precio_prod<=7 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        //adaptador.notifyDataSetChanged();
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });


    }

    public void listarDobleAgriPrecioRegular(String nombre,String apellido){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' AND P.precio_prod>7 AND P.precio_prod<=15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }

    public void listarDobleAgriPrecioAlto(String nombre,String apellido){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' AND P.precio_prod>15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }

    public void listarDobleAgriTipo(String nombre,String apellido,String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' AND U.tipo_agri LIKE '"+tipo+"' ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }

    public void listarDobleTipoPrecioBajo(String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.tipo_agri LIKE '"+tipo+"' AND P.precio_prod<=7 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }
    public void listarDobleTipoPrecioRegular(String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.tipo_agri LIKE '"+tipo+"' AND P.precio_prod>7 AND P.precio_prod<=15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }

    public void listarDobleTipoPrecioAlto(String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.tipo_agri LIKE '"+tipo+"' AND P.precio_prod>15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }

    public void listarTripleAgriTipoPrecioBajo(String nombre,String apellido,String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' AND U.tipo_agri LIKE '"+tipo+"' AND P.precio_prod<=7 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }

    public void listarTripleAgriTipoPrecioRegular(String nombre,String apellido,String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' AND U.tipo_agri LIKE '"+tipo+"' AND P.precio_prod>7 AND P.precio_prod<=15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }

    public void listarTripleAgriTipoPrecioAlto(String nombre,String apellido,String tipo){
        lstProducto= new ArrayList<>();
        SQLiteDatabase db= helper.getReadableDatabase();
        Producto pro=null;
        //Cursor cursor= helper.mostrarProductos();
        Cursor cursort= db.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 AND U.nombre_agri LIKE '"+nombre+"' AND U.apellido_agri LIKE '"+apellido+"' AND U.tipo_agri LIKE '"+tipo+"' AND P.precio_prod>15 ORDER BY R.fecha_reg DESC",null);
        while (cursort.moveToNext()){
            pro= new Producto();
            pro.setCodigo(cursort.getInt(0));
            pro.setNombre(cursort.getString(1));
            pro.setPrecio(cursort.getDouble(2));
            pro.setCriterio_medida(cursort.getString(3));
            pro.setStock(cursort.getInt(4));
            byte[] imageBytes=cursort.getBlob(5);
            Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
            pro.setImagen(objectBitmap);
            pro.setDescripcion(cursort.getString(6));
            pro.setCodAgri(cursort.getInt(7));
            pro.setCodCla(codCla);
            lstProducto.add(pro);
        }
        //ryc.setHasFixedSize(true);

        ryc.setLayoutManager(new LinearLayoutManager(ProductosClasicoActivity.this));

        adaptador= new AdapterProductosComprar(lstProducto,this);
        ryc.setAdapter(adaptador);

        adaptador.setOnClickListener((v) -> {
            //obs: recorrer el arreglo para comparar con el valor de la posicion del recycler
            //String cor= rvUsuario.getContentDescription(rvUsuario.getChildViewHolder(v.findViewById(R.id.idCorreo).toString()));
            /*String nom="";
            for (int i = 0; i < lstUsuario.size(); i++) {
                if (cor.equalsIgnoreCase(lstUsuario.get(i).getCorreo())){
                    nom= lstUsuario.get(i).getNombre();
                }
            }*/
            //Toast.makeText(getApplicationContext(),"Seleccion: "+cor,Toast.LENGTH_LONG).show();
            Producto prod=adaptador.obtenerPro(ryc.getChildAdapterPosition(v));
            if (prod!=null){
                //Toast.makeText(getApplicationContext(),"Seleccion: "+usur.getNombre(),Toast.LENGTH_LONG).show();
                Intent i= new Intent(getApplicationContext(),DetalleProductoClasicoActivity.class);
                i.putExtra("idProducto",prod.getCodigo());
                i.putExtra("idClasico",prod.getCodCla());
                i.putExtra("idAgricultor",prod.getCodAgri());
                //i.putExtra("Arreglo",arr);
                startActivity(i);
                //finish();//Para ya cerrar este Intent o Activity
            }



        });

    }
}
