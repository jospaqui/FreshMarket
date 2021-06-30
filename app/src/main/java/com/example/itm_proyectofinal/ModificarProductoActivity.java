package com.example.itm_proyectofinal;

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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ModificarProductoActivity extends AppCompatActivity {
    Button modificar,foto;
    EditText nombre,precio,descip,stock;
    Spinner medida;
    int codAgri,codProd;
    TextView texto,regresar;
    ImageView home,icon;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    String rutaImagen;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifcar_producto);

        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        nombre=(EditText) findViewById(R.id.nombre_prodc_modificar);
        precio=(EditText) findViewById(R.id.precio_prod_modificar);
        descip=(EditText) findViewById(R.id.descripcion_prod_modificar);
        stock=(EditText) findViewById(R.id.stock_prod_modificar);
        medida=(Spinner) findViewById(R.id.unidaMedida_prod_modificar);
        foto=(Button) findViewById(R.id.btn_addPhotos_modificar);
        modificar=(Button) findViewById(R.id.btn_crear_prod_modificar);
        texto=(TextView) findViewById(R.id.text_img);
        home = (ImageView) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);

        final SQLite_OpenHelper helper=new SQLite_OpenHelper(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
            //bundle.remove("Mensaje");
        }
        if (bundle.containsKey("idProducto")){
            codProd= bundle.getInt("idProducto");
            //bundle.remove("Mensaje");
        }
        Cursor c= helper.consultarProductos(codProd);
        while (c.moveToNext()){
            nombre.setText(c.getString(1));
            precio.setText(""+c.getDouble(2));
            for (int i = 0; i <medida.getCount() ; i++) {
                if (medida.getItemAtPosition(i).toString().equals(c.getString(3))){
                    medida.setSelection(i);
                    break;
                }
            }
            stock.setText(""+c.getInt(4));
            //foto
            descip.setText(c.getString(6));
        }



        //nombre.setText(""+codProd);
        //descip.setText(""+codAgri);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),VerProductosAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),VerProductosAgricultorActivity.class);
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
                final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(ModificarProductoActivity.this);

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

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().isEmpty() && !precio.getText().toString().isEmpty() && !stock.getText().toString().isEmpty() &&
                        !descip.getText().toString().isEmpty() && medida.getSelectedItemPosition()!=0){
                    if (Integer.parseInt(stock.getText().toString()) >0 && Double.parseDouble(precio.getText().toString())>0){
                        if (imageToStore!=null){
                            Cursor c= helper.consultarProductoxNombreDiferente(nombre.getText().toString().toUpperCase(),codAgri,codProd);
                            String pr= descip.getText().toString().substring(0,1).toUpperCase();
                            String pr2=descip.getText().toString().substring(1).toLowerCase();
                            String total= pr+pr2;
                            if(c==null){
                                objectByteArrayOutputStream= new ByteArrayOutputStream();
                                Bitmap resizeBitmap=Bitmap.createScaledBitmap(imageToStore,85,85,false); //posible redimension
                                resizeBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
                                //imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
                                imageInBytes=objectByteArrayOutputStream.toByteArray();
                                SQLiteDatabase db= helper.getReadableDatabase();
                        /*String[] projection={
                                "id_prod",
                                "nombre_prod",
                                "precio_prod",
                                "criterio_medida_pro",
                                "stock_prod",
                                "foto_prod",
                                "descrip_prod"
                        };*/
                                //new value for column
                                ContentValues values= new ContentValues();
                                values.put("nombre_prod",nombre.getText().toString().toUpperCase());
                                values.put("precio_prod",precio.getText().toString());
                                values.put("criterio_medida_pro",medida.getSelectedItem().toString());
                                values.put("stock_prod",stock.getText().toString());
                                values.put("foto_prod",imageInBytes);//por el momento foto es STRING
                                values.put("descrip_prod",total);

                                //which row to update based on the title
                                String selection="id_prod= ?";
                                String[] selectionArgs={""+codProd};
                                //Cursor c= db.query("PRODUCTO",projection,selection,selectionArgs,null,null,null);

                                db.update(
                                        "PRODUCTO",
                                        values,
                                        selection,
                                        selectionArgs
                                );
                                //Toast.makeText(getApplicationContext(),"Registro modificado satisfactoriamente!!!!",Toast.LENGTH_LONG).show();
                                //nombre.setText("");
                                //precio.setText("");
                                //descip.setText("");
                                //stock.setText("");
                                //medida.setSelection(0);//ver si esta bien
                                //faltaria la opcion de dejar en blanco la foto
                                //nombre.requestFocus();

                                Intent i= new Intent(getApplicationContext(),VerProductosAgricultorActivity.class);
                                i.putExtra("idAgricultor",codAgri);
                                startActivity(i);



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


                            }else{
                                Toast.makeText(getApplicationContext(), "Ya existe el producto con ese nombre",Toast.LENGTH_LONG).show();
                                nombre.setText("");
                                nombre.requestFocus();
                            }
                        }else{
                            Cursor c= helper.consultarProductoxNombreDiferente(nombre.getText().toString().toUpperCase(),codAgri,codProd);
                            String pr= descip.getText().toString().substring(0,1).toUpperCase();
                            String pr2=descip.getText().toString().substring(1).toLowerCase();
                            String total= pr+pr2;
                            if(c==null){
                                //objectByteArrayOutputStream= new ByteArrayOutputStream();
                                //imageToStore.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);
                                //imageInBytes=objectByteArrayOutputStream.toByteArray();
                                SQLiteDatabase db= helper.getReadableDatabase();
                                ContentValues values= new ContentValues();
                                values.put("nombre_prod",nombre.getText().toString().toUpperCase());
                                values.put("precio_prod",precio.getText().toString());
                                values.put("criterio_medida_pro",medida.getSelectedItem().toString());
                                values.put("stock_prod",stock.getText().toString());
                                //values.put("foto_prod",imageInBytes);//por el momento foto es STRING
                                values.put("descrip_prod",total);

                                //which row to update based on the title
                                String selection="id_prod= ?";
                                String[] selectionArgs={""+codProd};
                                //Cursor c= db.query("PRODUCTO",projection,selection,selectionArgs,null,null,null);

                                db.update(
                                        "PRODUCTO",
                                        values,
                                        selection,
                                        selectionArgs
                                );

                                Intent i= new Intent(getApplicationContext(),VerProductosAgricultorActivity.class);
                                i.putExtra("idAgricultor",codAgri);
                                startActivity(i);



                            }else{
                                Toast.makeText(getApplicationContext(), "Ya existe el producto con ese nombre",Toast.LENGTH_LONG).show();
                                nombre.setText("");
                                nombre.requestFocus();
                            }


                            //Toast.makeText(getApplicationContext(),"Debe seleccionar una imagen!!!",Toast.LENGTH_SHORT).show();
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
}
