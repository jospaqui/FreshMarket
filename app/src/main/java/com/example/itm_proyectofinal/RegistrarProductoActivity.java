package com.example.itm_proyectofinal;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RegistrarProductoActivity extends AppCompatActivity {

    Button foto,agregar;
    EditText nombre,precio,descip,stock;
    TextView texto,regresar;
    Spinner medida;
    int codAgri;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imageFilePath;
    ImageView home,icon;
    private Bitmap imageToStore;
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;
    String rutaImagen;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        nombre=(EditText) findViewById(R.id.nombre_prodc);
        precio=(EditText) findViewById(R.id.precio_prod);
        descip=(EditText) findViewById(R.id.descripcion_prod);
        stock=(EditText) findViewById(R.id.stock_prod);
        medida=(Spinner) findViewById(R.id.unidaMedida_prod);
        foto=(Button) findViewById(R.id.btn_addPhotos);
        agregar=(Button) findViewById(R.id.btn_crear_prod);
        texto=(TextView) findViewById(R.id.text_img);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        final SQLite_OpenHelper helper=new SQLite_OpenHelper(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
            //bundle.remove("Mensaje");
        }
        //nombre.setText(""+codAgri);
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

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
                final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(RegistrarProductoActivity.this);

                alertOpciones.setTitle("Elija una opcion");
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(opciones[which].equals("Tomar Foto")){
                            abrirCamara();
                        }else{
                            if(opciones[which].equals("Elegir de Galeria")){
                                chooseImagen();

                            }
                        }


                    }
                });
                alertOpciones.show();
            }
        });
        /*if (imageToStore!=null){
            texto.setText("Imagen subida correctamente!!!");
        }else{
            texto.setText("");
        }*/

        /*foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().isEmpty() && !precio.getText().toString().isEmpty() && !stock.getText().toString().isEmpty() &&
                !descip.getText().toString().isEmpty() && medida.getSelectedItemPosition()!=0){
                    if (Integer.parseInt(stock.getText().toString()) >0 && Double.parseDouble(precio.getText().toString())>0){
                        if (imageToStore!=null){
                            Cursor c= helper.consultarProductoxNombre(nombre.getText().toString().toUpperCase(),codAgri);
                            String pr= descip.getText().toString().substring(0,1).toUpperCase();
                            String pr2=descip.getText().toString().substring(1).toLowerCase();
                            String total= pr+pr2;
                            if(c==null){
                                //agregar
                                objectByteArrayOutputStream= new ByteArrayOutputStream();
                                Bitmap resizeBitmap=Bitmap.createScaledBitmap(imageToStore,85,85,false); //posible redimension
                                resizeBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
                                //imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
                                imageInBytes=objectByteArrayOutputStream.toByteArray();
                                SQLiteDatabase db=helper.getWritableDatabase();
                                try{
                                    ContentValues values=new ContentValues();
                                    values.put("nombre_prod",nombre.getText().toString().toUpperCase());
                                    values.put("precio_prod",precio.getText().toString());
                                    values.put("criterio_medida_pro",medida.getSelectedItem().toString());
                                    values.put("stock_prod",stock.getText().toString());
                                    values.put("foto_prod",imageInBytes);//por el momento foto es STRING
                                    values.put("descrip_prod",total);
                                    //values.put("fecha_registro_agri",obtenerFechaConFormato("YYYY-MM-dd HH:mm:ss","America/Lima"));

                                    db.insert("PRODUCTO",null,values);
                                    //Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                                    //startActivity(i);

                                    //SE PUEDE UTILIZAR EL CURSOR O SINO MODIFICAR EL CURSOR TRAYENDO A TODOS Y IR HASTA EL ULTIMO REGISTRO Y CAPTURAR EL ID
                                    Cursor c2= helper.obtenerIdProducto();


                                    while (c2.moveToNext()){
                                        //nombre.setText(""+c2.getInt(0));
                                        SQLiteDatabase cd=helper.getWritableDatabase();
                                        try{
                                            ContentValues valuess=new ContentValues();
                                            valuess.put("id_us_agric",codAgri);//falta
                                            valuess.put("id_produc",c2.getInt(0));//ver si esta bien
                                            //valuess.put("fecha_reg",obtenerFechaConFormato("YYYY-MM-dd HH:mm:ss","America/Lima"));
                                            valuess.put("fecha_reg",obtenerFechaConFormato("dd-MM-YYYY HH:mm:ss","America/Lima"));

                                            cd.insert("REGISTRO",null,valuess);
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                    Toast.makeText(getApplicationContext(), "Se registro correctamente el producto",Toast.LENGTH_LONG).show();
                                    nombre.setText("");
                                    precio.setText("");
                                    descip.setText("");
                                    stock.setText("");
                                    medida.setSelection(0);//ver si esta bien
                                    texto.setText("");
                                    imageToStore=null;
                                    imageFilePath=null;
                                    //faltaria la opcion de dejar en blanco la foto
                                    nombre.requestFocus();



                                    //nombre.setText("");
                                    //precio.setText("");
                                    //descip.setText(""+c2.getInt(0));
                                    //stock.setText("");
                                    //medida.setSelection(0);
                            /*if (c2!=null){
                                SQLiteDatabase cd=helper.getWritableDatabase();
                                try{
                                    ContentValues valuess=new ContentValues();
                                    valuess.put("id_us_agric",codAgri);//falta
                                    valuess.put("id_produc",c2.getInt(0));//ver si esta bien
                                    valuess.put("fecha_reg",obtenerFechaConFormato("YYYY-MM-dd HH:mm:ss","America/Lima"));

                                    cd.insert("REGISTRO",null,valuess);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }*/
                                } catch (Exception e){
                                    e.printStackTrace();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), "Ya existe el producto con ese nombre",Toast.LENGTH_LONG).show();
                                nombre.setText("");
                                nombre.requestFocus();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Debe seleccionar una imagen!!!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Debe ingresar un stock o precio > 0",Toast.LENGTH_SHORT).show();
                        stock.setText("");
                        stock.requestFocus();
                    }



                }else{
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();

                    nombre.requestFocus();
                }
            }
        });




    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //   if (intent.resolveActivity(getPackageManager()) != null) {

        File imagenArchivo = null;

        try{
            imagenArchivo = crearImagen();
        }catch (IOException ex){
            Log.e("Error", ex.toString());
        }
        if(imagenArchivo !=null){
            Uri fotoUri = FileProvider.getUriForFile(this, "com.example.itm_proyectofinal.fileprovider",imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(intent, 1);
        }

    }

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen,".jpg",directorio);

        rutaImagen = imagen.getAbsolutePath();
        return  imagen;
    }


    public void chooseImagen(){
        try {
            Intent objecIntent= new Intent();
            objecIntent.setType("image/*");
            objecIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objecIntent,PICK_IMAGE_REQUEST);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK) {
                //    Bundle extras = data.getExtras();
                imageToStore = BitmapFactory.decodeFile(rutaImagen);
                texto.setText("Imagen subida correctamente!!!");
                //Uri imgUri=fotoUri
                //Bitmap imgBitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                //imgView.setImageBitmap(imgBitmap);
            }

            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageFilePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                texto.setText("Imagen subida correctamente!!!");
                //texto.setText(""+imageFilePath);

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

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
