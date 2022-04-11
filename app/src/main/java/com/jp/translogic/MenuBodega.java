package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MenuBodega extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bodega);
        Intent mnuLogin = getIntent();
        String uName = mnuLogin.getStringExtra(MainActivity.USER);
        String uFull = mnuLogin.getStringExtra(MainActivity.NAME);
    }
}