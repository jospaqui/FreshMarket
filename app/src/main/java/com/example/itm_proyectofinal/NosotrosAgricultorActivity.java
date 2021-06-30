package com.example.itm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NosotrosAgricultorActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView regresar;
    int codAgri;
    ImageView home,icon;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros_agricultor);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        regresar=(TextView) findViewById(R.id.btn_regresar_prod_home);
        home = (ImageView ) findViewById(R.id.home);
        icon = (ImageView) findViewById(R.id.icon);


        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("idAgricultor")){
            codAgri= bundle.getInt("idAgricultor");
        }

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
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng usil = new LatLng(-12.073352209632263, -76.95369998586453);
        mMap.addMarker(new MarkerOptions().position(usil).title("Marker in USIL"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(usil));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usil,18));
    }
}