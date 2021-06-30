package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EditarCuentaClasicoActivity extends AppCompatActivity {

    Button editar;
    int codCla;
    ImageView home,icon;
    TextView regresar;
    EditText celular,correo,ps1,ps2;
    public static final String clave="gdsawr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta_clasico);

        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        editar=(Button) findViewById(R.id.btn_editar_agri);
        celular=(EditText) findViewById(R.id.etUserPhone_agri_editar);
        correo=(EditText) findViewById(R.id.et_userMail_agri_editar);
        ps1=(EditText) findViewById(R.id.et_userPassword_agri_editar);
        ps2=(EditText) findViewById(R.id.et_Re_userPassword_agri_editar);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);



        final SQLite_OpenHelper helper=new SQLite_OpenHelper(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idClasico")){
            codCla= bundle.getInt("idClasico");
        }
        Cursor c= helper.obtenerUsuarioCla(codCla);
        while (c.moveToNext()) {
            celular.setText(c.getString(5));
            correo.setText(c.getString(6));

            //tipo.setText(c.getString(9));
            //correo.setText(c.getString(10));
        }



        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MiCuentaClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MiCuentaClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });

        //ANALIZAR SI SE DEBE OBLIGATORIAMENTE COMPLETAR TODOS LOS CAMPOS O ALGUNOS SON OPCIONALES ???

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!celular.getText().toString().isEmpty() && !correo.getText().toString().isEmpty() && !ps1.getText().toString().isEmpty()
                        && !ps2.getText().toString().isEmpty()){
                    if (celular.getText().toString().length() != 9) {
                        Toast.makeText(getApplicationContext(), "El numero de celular solo tiene 9 numeros, verificar sus datos", Toast.LENGTH_LONG).show();
                        celular.setText("");
                        celular.requestFocus();
                    }else{
                        if (ps1.getText().toString().equals(ps2.getText().toString())){
                            //verificacion del correo
                            // observacion en este caso cuando se verifica el correo ya existe ne caso no modifique el correo

                            Cursor c= helper.consultarClasicoxCorreoModificado(codCla,correo.getText().toString());
                            if (c==null){
                                //SQLiteDatabase db=helper.getWritableDatabase();
                                SQLiteDatabase db= helper.getReadableDatabase();
                                //realizar la modificacion

                                try {
                                    String pass_encr= encriptar(ps1.getText().toString(),clave);
                                    ContentValues values= new ContentValues();
                                    values.put("celular_cla",celular.getText().toString());
                                    values.put("coreo_cla",correo.getText().toString());
                                    values.put("password_cla",pass_encr);

                                    //which row to update based on the title
                                    String selection="id_us_cla= ?";
                                    String[] selectionArgs={""+codCla};
                                    //Cursor c= db.query("PRODUCTO",projection,selection,selectionArgs,null,null,null);

                                    db.update(
                                            "USUARIO_CLASICO",
                                            values,
                                            selection,
                                            selectionArgs
                                    );
                                    Toast.makeText(getApplicationContext(), "Se modifico correctamente sus datos!!!",Toast.LENGTH_LONG).show();
                                    celular.setText("");
                                    correo.setText("");
                                    ps1.setText("");
                                    ps2.setText("");
                                    //tipo.setSelection(0);

                                } catch (Exception e){
                                    e.printStackTrace();
                                }


                            }else{
                                Toast.makeText(getApplicationContext(), "Ya existe el correo ingresado",Toast.LENGTH_LONG).show();
                                correo.setText("");
                                correo.requestFocus();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"No coincide las contraseñas ingresadas",Toast.LENGTH_SHORT).show();
                            ps2.setText("");
                            ps2.requestFocus();
                        }

                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                    celular.requestFocus();
                }






                //Toast.makeText(getApplicationContext(), "Se modifico sus datos!!!",Toast.LENGTH_LONG).show();
                //nombre.setText("");
                //nombre.requestFocus();
                /*Intent i= new Intent(getApplicationContext(),MiCuentaAgricultorActivity.class);
                i.putExtra("idAgricultor",codAgri);
                startActivity(i);*/
            }
        });
    }

    private String encriptar(String datos, String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;
    }
    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
}