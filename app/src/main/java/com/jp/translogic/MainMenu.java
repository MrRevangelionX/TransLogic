package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button btnMenuBodega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnMenuBodega = findViewById(R.id.btnBodega);
        btnMenuBodega.setOnClickListener(view -> loadMenuBodega());
    }

    public void loadMenuBodega() {
        Intent mnuBodega = new Intent(MainMenu.this,MenuBodega.class);
        startActivity(mnuBodega);
    }
}