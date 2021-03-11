package com.example.doctoratuservicio;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ConnectionDB.DistritoRequest;
import ConnectionDB.Especialidad;
import ConnectionDB.ModalidadRequest;
import cz.msebera.android.httpclient.Header;

public class Filtros extends AppCompatActivity {

    private AsyncHttpClient cliente;

    Button btnDIstri;
    Spinner spnEsp, spnMod, spnDist;
    String espSelec = "";
    TextView text;
    ImageButton ib3,ib2,ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_prueba);

        btnDIstri = (Button) findViewById(R.id.btnDIstri);
        spnEsp = (Spinner) findViewById(R.id.spnEsp);
        spnDist = (Spinner) findViewById(R.id.spnDist);
        spnMod = (Spinner) findViewById(R.id.spnMod);

        text = (TextView) findViewById(R.id.txtEspeci);

        cliente = new AsyncHttpClient();
        llenarSpinnerEspecialidades();

        ib3 = (ImageButton) findViewById(R.id.imageButton3);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filtros.this, GenerarCita.class);
                Filtros.this.startActivity(intent);
            }
        });

        ib2 = (ImageButton) findViewById(R.id.imageButton2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filtros.this, Creditos_Pantalla.class);
                Filtros.this.startActivity(intent);
            }
        });

        ib = (ImageButton) findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filtros.this, Inicio.class);
                Filtros.this.startActivity(intent);
            }
        });

        spnEsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnMod.setAdapter(null);
                spnDist.setAdapter(null);
                String seleccion = spnEsp.getSelectedItem().toString();

                Response.Listener<String> respoListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonReponse = new JSONArray(response);
                            cargarSpinnerMod(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Filtros.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                ModalidadRequest modalidadRequest = new ModalidadRequest(seleccion, respoListener);
                RequestQueue queue = Volley.newRequestQueue(Filtros.this);
                queue.add(modalidadRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnDist.setAdapter(null);
                String seleccion = spnMod.getSelectedItem().toString();

                Response.Listener<String> respoListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonReponse = new JSONArray(response);
                            cargarSpinnerDist(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Filtros.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                DistritoRequest distritoRequest = new DistritoRequest(spnEsp.getSelectedItem().toString(), seleccion, respoListener);
                RequestQueue queue = Volley.newRequestQueue(Filtros.this);
                queue.add(distritoRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDIstri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String especialidad = spnEsp.getSelectedItem().toString();
                String distrito = spnDist.getSelectedItem().toString();

                //Intent intent = new Intent(Filtros.this, Doctores.class);
                Intent intent = new Intent(Filtros.this, RecyclerCardView.class);
                intent.putExtra("especialidad", spnEsp.getSelectedItem().toString());
                intent.putExtra("distrito", spnDist.getSelectedItem().toString());
                intent.putExtra("modalidad", spnMod.getSelectedItem().toString());
                Filtros.this.startActivity(intent);
            }
        });
    }

    private void llenarSpinnerEspecialidades() {
        String url = "http://192.168.0.3:8000/pruebaBD/ObtenerEsp.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarSpinnerEsp(new String (responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarSpinnerEsp(String respuesta) {
        ArrayList<Especialidad> lista = new ArrayList<Especialidad>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++) {
                Especialidad e = new Especialidad();
                e.setEspecialidad(jsonArreglo.getJSONObject(i).getString("especialidad"));
                lista.add(e);
            }
            ArrayAdapter<Especialidad> a = new ArrayAdapter<Especialidad>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spnEsp.setAdapter(a);
            espSelec = spnEsp.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Filtros.this, "Error al llenar Spinner", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarSpinnerDist(String repuesta) {
        ArrayList<String> lista = new ArrayList<String>();
        try {
            JSONArray jsonArreglo = new JSONArray(repuesta);
            for (int i = 0; i < jsonArreglo.length(); i++) {
                String d = "";
                d = jsonArreglo.getJSONObject(i).getString("distrito");
                lista.add(d);
            }
            ArrayAdapter<String> a = new ArrayAdapter<String>(Filtros.this, android.R.layout.simple_dropdown_item_1line, lista);
            spnDist.setAdapter(a);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Filtros.this, "Error al llenar Spinner", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarSpinnerMod(String repuesta) {
        ArrayList<String> lista = new ArrayList<String>();
        try {
            JSONArray jsonArreglo = new JSONArray(repuesta);
            for (int i = 0; i < jsonArreglo.length(); i++) {
                String d = "";
                d = jsonArreglo.getJSONObject(i).getString("modalidad");
                lista.add(d);
            }
            ArrayAdapter<String> a = new ArrayAdapter<String>(Filtros.this, android.R.layout.simple_dropdown_item_1line, lista);
            spnMod.setAdapter(a);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Filtros.this, "Error al llenar Spinner", Toast.LENGTH_SHORT).show();
        }
    }
}