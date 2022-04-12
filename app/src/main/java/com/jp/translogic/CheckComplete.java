package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class CheckComplete extends AppCompatActivity {

    RequestQueue requestQueue;

    EditText txtReq;
    Button btnCheck;

    String user, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_complete);
        txtReq = findViewById(R.id.txtRequerimiento);
        btnCheck = findViewById(R.id.btnCheckBodega);
        requestQueue = Volley.newRequestQueue(this);

        cleanForm();
        cargarPreferencias();

    }

    public void cleanForm(){
        txtReq.setText("");
        txtReq.requestFocus();
    }

    public void cargarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("credenciales", MODE_PRIVATE);

        user = preferencias.getString("USUARIO","No existe la informacion");
        name = preferencias.getString("NOMBRE","No existe la informacion");
    }

    public void reqCheckComplete(View view) {
        String url = "http://192.168.140.15:8080/Proyectos/TransLogic/API/putComplete.php";
        String requisicion = txtReq.getText().toString();

        if(requisicion.trim().isEmpty()){
            Toast.makeText(this, "Los campos NO deben estar vacíos", Toast.LENGTH_SHORT).show();
        }else {
            JSONObject parametros = new JSONObject();

            try {
                parametros.put("nOrder", requisicion);
                parametros.put("uCreation", user);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, parametros, response -> {
                try {
                    JSONArray jsonArray = response.getJSONArray("check");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String retReq = jsonObject.optString("orden_material");
                        String retFecha = jsonObject.optString("fecha_check");

                        if(!retReq.equals("ERROR")){
                            cleanForm();
                            Toast.makeText(this, "CHECKPOINT\n" + retFecha, Toast.LENGTH_SHORT).show();
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
}