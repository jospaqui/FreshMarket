package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itm_proyectofinal.OpenHelper.SQLite_OpenHelper;

public class MiCuentaClasicoActivity extends AppCompatActivity {
    int codCla;
    TextView date,etDni,etNombre,etApellido,celular,correo,fech_reg,regresar;
    Button editar;
    ImageView home,icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta_clasico);
        etNombre=(TextView) findViewById(R.id.idnombre_ver);
        etApellido=(TextView) findViewById(R.id.idapellido_ver);
        etDni=(TextView) findViewById(R.id.idDNI_ver);
        date=(TextView) findViewById(R.id.idFecha_Nac_ver);
        celular=(TextView) findViewById(R.id.idCelular_ver);
        correo=(TextView) findViewById(R.id.idCorreo_ver);
        fech_reg=(TextView) findViewById(R.id.idfechaReg_ver);
        editar=(Button) findViewById(R.id.btn_editar_perfil_cla);
        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idClasico")){
            codCla= bundle.getInt("idClasico");
        }

        final SQLite_OpenHelper helper=new SQLite_OpenHelper(this);
        Cursor c= helper.obtenerUsuarioCla(codCla);

        while (c.moveToNext()) {
            etNombre.setText(c.getString(1));
            etApellido.setText(c.getString(2));
            etDni.setText(c.getString(3));
            date.setText(c.getString(4));
            celular.setText(c.getString(5));
            correo.setText(c.getString(6));
            fech_reg.setText(c.getString(8));
        }

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),HomeClasicoActivity.class);
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

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i= new Intent(getApplicationContext(),EditarMicuentaActivity.class);
                startActivity(i);*/

                Intent i= new Intent(getApplicationContext(),EditarCuentaClasicoActivity.class);
                i.putExtra("idClasico",codCla);
                startActivity(i);
            }
        });
    }
}