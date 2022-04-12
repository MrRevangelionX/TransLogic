package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtPass;
    Button btnEntrar;
    String uName, uPass, retName, retFullName;
    RequestQueue requestQueue;

    public static final String HOST = "http://http://168.243.90.35:8080/TransLogic/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnEntrar = findViewById(R.id.btnIngresar);
        requestQueue = Volley.newRequestQueue(this);

    }

    public void validateLogins(View view) throws JSONException {
        String url = MainActivity.HOST + "API/getLogin.php";
        uName = txtName.getText().toString();
        uPass = txtPass.getText().toString();

        if(uName.trim().isEmpty() || uPass.trim().isEmpty()){
            Toast.makeText(MainActivity.this, "Los campos NO deben estar vacíos", Toast.LENGTH_SHORT).show();
        }else {
            JSONObject parametros = new JSONObject();

            try {
                parametros.put("uID", uName);
                parametros.put("uPD", uPass);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Toast.makeText(MainActivity.this, "Checkeando Informacion de Usuario", Toast.LENGTH_LONG).show();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, parametros, response -> {
                try {
                    JSONArray jsonArray = response.getJSONArray("login");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        retName = jsonObject.optString("user");
                        retFullName = jsonObject.optString("name");
                        boolean retExiste = jsonObject.optBoolean("exist");

                        if(retExiste){

                            //Enter into MENU
                            Toast.makeText(MainActivity.this, "Bienvenido/a: " + retFullName, Toast.LENGTH_LONG).show();
                            guardarPreferencias();
                            Intent mnuMain = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(mnuMain);

                        }else{
                            Toast.makeText(MainActivity.this, "El usuario " + uName + " o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            requestQueue.add(jsonObjectRequest);
        }
    }

    public void guardarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("credenciales", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String Usuario = retName;
        String Nombre = retFullName;
        editor.putString("USUARIO",Usuario);
        editor.putString("NOMBRE", Nombre);
        editor.commit();
    }
}