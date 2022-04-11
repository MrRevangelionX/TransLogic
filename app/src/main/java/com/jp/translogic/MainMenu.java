package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent mnuLogin = getIntent();
        String uName = mnuLogin.getStringExtra(MainActivity.USER);
        String uFull = mnuLogin.getStringExtra(MainActivity.NAME);

    }

    public void loadBodega(View view) {
        Intent mnuBodega = new Intent(MainMenu.this,MenuBodega.class);
        startActivity(mnuBodega);
    }
}