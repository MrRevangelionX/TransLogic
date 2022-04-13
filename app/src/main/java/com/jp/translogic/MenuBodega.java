package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuBodega extends AppCompatActivity {

    Button btnAsignaBodega, btnCheckBodega, btnCheckLocation, btnCheckComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bodega);
        btnAsignaBodega = findViewById(R.id.btnAsignarBodega);
        btnAsignaBodega.setOnClickListener(view -> loadAsignarBodega());

        btnCheckBodega = findViewById(R.id.btnCheckBodega);
        btnCheckBodega.setOnClickListener(view -> loadCheckBodega());

        btnCheckLocation = findViewById(R.id.btnCheckLocation);
        btnCheckLocation.setOnClickListener(view -> loadCheckLocation());

        btnCheckComplete = findViewById(R.id.btnCheckComplete);
        btnCheckComplete.setOnClickListener(view -> loadCheckComplete());

    }

    public void loadAsignarBodega() {
        Intent frmAsignarBodega = new Intent(MenuBodega.this,BodegaAsignar.class);
        startActivity(frmAsignarBodega);
    }

    public void loadCheckBodega() {
        Intent frmCheckBodega = new Intent(MenuBodega.this, CheckBodega.class);
        startActivity(frmCheckBodega);
    }

    public void loadCheckLocation() {
        Intent frmCheckLocation = new Intent(MenuBodega.this, CheckLocation.class);
        startActivity(frmCheckLocation);
    }

    public void loadCheckComplete() {
        Intent frmCheckComplete = new Intent(MenuBodega.this, CheckComplete.class);
        startActivity(frmCheckComplete);
    }
}