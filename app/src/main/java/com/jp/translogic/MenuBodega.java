package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuBodega extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bodega);

    }

    public void loadAsignarBodega(View view) {
        Intent frmAsignarBodega = new Intent(MenuBodega.this,BodegaAsignar.class);
        startActivity(frmAsignarBodega);
    }

    public void loadCheckBodega(View view) {
        Intent frmCheckBodega = new Intent(MenuBodega.this, CheckBodega.class);
        startActivity(frmCheckBodega);
    }

    public void loadCheckLocation(View view) {
        Intent frmCheckLocation = new Intent(MenuBodega.this, CheckLocation.class);
        startActivity(frmCheckLocation);
    }

    public void loadCheckComplete(View view) {
        Intent frmCheckComplete = new Intent(MenuBodega.this, CheckComplete.class);
        startActivity(frmCheckComplete);
    }
}