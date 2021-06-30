package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

public class AumentarProductoAgricultorActivity extends AppCompatActivity {
    Button aumentar;
    Spinner producto;
    TextView stockAct,medidaAct,stockFinal,medidaFinal,regresar;
    EditText stock_add;
    int i=0;
    String[] arreglo_producto;
    int codAgri,codPro;
    ImageView home,icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aumentar_producto_agricultor);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        aumentar=(Button) findViewById(R.id.btn_aumentar_prod);
        producto=(Spinner) findViewById(R.id.spinner_prod);
        stockAct=(TextView) findViewById(R.id.idstock_actual);
        medidaAct=(TextView) findViewById(R.id.idstock_actual_medida);
        stockFinal=(TextView) findViewById(R.id.idstock_final);
        medidaFinal=(TextView) findViewById(R.id.idstock_final_medida);
        stock_add=(EditText) findViewById(R.id.stock_prod_aumentar);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
            //bundle.remove("Mensaje");
        }

        final SQLite_OpenHelper helper=new SQLite_OpenHelper(this);

        Cursor c1=helper.consultarProductoxidAgri(codAgri);

        if (c1==null){
            arreglo_producto= new String[1];
            arreglo_producto[0]="Elija un producto";
        }else{
            while (c1.moveToNext()) {

                if (i==0){
                    arreglo_producto= new String[c1.getCount()+1];
                    arreglo_producto[i]="Elija un producto";
                }
                i=i+1;
                arreglo_producto[i]=c1.getString(2);


            }
        }



        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo_producto);
        producto.setAdapter(adapter);


        /*stockAct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        producto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    Cursor cp= helper.consultarProductoxNombre(parent.getSelectedItem().toString(),codAgri);
                    if(cp!=null){
                        while (cp.moveToNext()){
                            codPro=cp.getInt(1);
                            stockAct.setText(""+cp.getInt(3));
                            medidaAct.setText(cp.getString(4));

                        }
                    }
                    if (!stock_add.getText().toString().isEmpty()){
                        stockFinal.setText(""+(Integer.parseInt(stockAct.getText().toString())+Integer.parseInt(stock_add.getText().toString())));
                        medidaFinal.setText(medidaAct.getText().toString());
                    }

                    //parent.getSelectedItem().toString();
                }else{
                    stockAct.setText("");
                    medidaAct.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stock_add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    if (producto.getSelectedItemPosition()!=0){
                        stockFinal.setText(""+(Integer.parseInt(stockAct.getText().toString())+Integer.parseInt(s.toString())));
                        medidaFinal.setText(medidaAct.getText().toString());
                    }
                }else{
                    stockFinal.setText("");
                    medidaFinal.setText("");
                }



            }
        });



        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomeAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);
            }
        });


        aumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (producto.getSelectedItemPosition()!=0 && !stock_add.getText().toString().isEmpty()){
                    //Cursor c= helper.consultarProductoxNombre(producto.getSelectedItem().toString(),codAgri);
                    SQLiteDatabase db= helper.getReadableDatabase();
                    ContentValues values= new ContentValues();
                    values.put("stock_prod",stockFinal.getText().toString());

                    //which row to update based on the title
                    String selection="id_prod= ?";
                    String[] selectionArgs={""+codPro};
                    //Cursor c= db.query("PRODUCTO",projection,selection,selectionArgs,null,null,null);

                    db.update(
                            "PRODUCTO",
                            values,
                            selection,
                            selectionArgs
                    );
                    Toast.makeText(getApplicationContext(),"Producto agregado satisfactoriamente!!!",Toast.LENGTH_SHORT).show();
                    producto.setSelection(0);
                    stock_add.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();

                    //stock_add.requestFocus();
                }
            }
        });

    }
}