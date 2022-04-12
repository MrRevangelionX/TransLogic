package com.jp.translogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class BodegaAsignar extends AppCompatActivity {
    EditText txtRequerimiento, txtTransportista;
    Button btnAsignarBodega;
    RequestQueue requestQueue;
    String user,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodega_asignar);
        requestQueue = Volley.newRequestQueue(this);

        txtRequerimiento = findViewById(R.id.txtRequerimiento);
        txtTransportista = findViewById(R.id.txtTransporte);
        btnAsignarBodega = findViewById(R.id.btnAsignarBodega);

        cargarPreferencias();

        cleanForm();

    }
    public void reqAsignarBodega(View view) {
        String url = MainActivity.HOST + "API/putCreation.php";
        String asignaReq = txtRequerimiento.getText().toString();
        String asignaTrans = txtTransportista.getText().toString();

        if(asignaReq.trim().isEmpty() || asignaTrans.trim().isEmpty()){
            Toast.makeText(this, "Los campos NO deben estar vacíos", Toast.LENGTH_SHORT).show();
        }else{
            JSONObject parametros = new JSONObject();

            try {
                parametros.put("nOrder",asignaReq);
                parametros.put("nTransporte",asignaTrans);
                parametros.put("uCreation", user);
            }catch (JSONException e){
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, parametros, response ->{
                try {
                    JSONArray jsonArray = response.getJSONArray("asignacion");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String retOrden = jsonObject.optString("orden_material");
                        String retTransporte = jsonObject.optString("transporte_asignado");
                        String retFecha = jsonObject.optString("fecha_asignado");

                        if(retOrden.equals("ERROR")){
                            Toast.makeText(this, "HUBO UN ERROR EN EL INGRESO DE LA INFORMACION \n" + retFecha , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "SE ASIGNO LA ORDEN: " + retOrden + "\n AL TRANSPORTE: " + retTransporte + "\n FECHA: " + retFecha, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                Toast.makeText(this, "NO ENTRO EN LA PETICION", Toast.LENGTH_SHORT).show();
            });
            requestQueue.add(jsonObjectRequest);
            cleanForm();
        }
    }

    public void cleanForm(){
        txtRequerimiento.setText("");
        txtTransportista.setText("");
        txtRequerimiento.requestFocus();
    }

    public void cargarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("credenciales", MODE_PRIVATE);

        user = preferencias.getString("USUARIO","No existe la informacion");
        name = preferencias.getString("NOMBRE","No existe la informacion");
    }
}