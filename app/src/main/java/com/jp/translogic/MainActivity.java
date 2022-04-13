package com.jp.translogic;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_SETTINGS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtPass;
    Button btnEntrar;
    String uName, uPass, retName, retFullName;
    RequestQueue requestQueue;

    //public static final String HOST = "http://192.168.140.15:8080/Proyectos/TransLogic/";
    public static final String HOST = "http://168.243.90.35:8080/TransLogic/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnEntrar = findViewById(R.id.btnIngresar);
        requestQueue = Volley.newRequestQueue(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {INTERNET,WRITE_EXTERNAL_STORAGE,WRITE_SETTINGS},100);
        }
        btnEntrar.setOnClickListener(view -> validateLogins());
    }

    public void validateLogins(){
        String url = MainActivity.HOST + "API/getLogin.php";
        uName = txtName.getText().toString();
        uPass = txtPass.getText().toString();

        if(uName.trim().isEmpty() || uPass.trim().isEmpty()){
            Toast.makeText(MainActivity.this, "Los campos NO deben estar vacíos", Toast.LENGTH_SHORT).show();
        }else {
            JSONObject data = new JSONObject();

            try {
                data.put("uID", uName);
                data.put("uPD", uPass);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jRequest;
            jRequest = new JsonObjectRequest(Request.Method.POST, url, data, response -> {
                try {
                    JSONArray jsonArray = response.getJSONArray("login");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        retName = jsonObject.optString("user");
                        retFullName = jsonObject.optString("name");
                        boolean retExiste = jsonObject.optBoolean("exist");
                        if(retExiste){
                            //Enter into MENU
                            guardarPreferencias();
                            Toast.makeText(MainActivity.this, "Bienvenido/a: " + retFullName, Toast.LENGTH_LONG).show();
                            Intent mnuMain = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(mnuMain);
                        }else{
                            Toast.makeText(MainActivity.this, "El usuario " + uName + " o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(MainActivity.this, "Ha ocurrido algun error mientras se consultaba", Toast.LENGTH_SHORT).show());
            requestQueue.add(jRequest);
        }
    }

    public void guardarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("credenciales", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String Usuario = retName;
        String Nombre = retFullName;
        editor.putString("USUARIO",Usuario);
        editor.putString("NOMBRE", Nombre);
        editor.apply();
    }
}