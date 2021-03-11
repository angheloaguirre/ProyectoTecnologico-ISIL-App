package com.example.doctoratuservicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ConnectionDB.DistritoRequest;
import ConnectionDB.Doctor;
import ConnectionDB.ListaDoctoresRequest;

public class Doctores extends AppCompatActivity {

    TextView txt1;
    Intent intent;
    ArrayList<Doctor> lista = new ArrayList<Doctor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctores);

        txt1 = (TextView) findViewById(R.id.txt1);

        intent = getIntent();
        buscarDoctores();

        // A PARTIR DE ESTA LÍNEA  PROGRAMAR LO QUE VENGA
    }

    private void buscarDoctores() {

        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonReponse = new JSONArray(response);
                    cargarDatosDoctores(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Doctores.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        ListaDoctoresRequest listaRequest = new ListaDoctoresRequest(intent.getStringExtra("especialidad"),
                intent.getStringExtra("distrito"), intent.getStringExtra("modalidad"), respoListener);
        RequestQueue queue = Volley.newRequestQueue(Doctores.this);
        queue.add(listaRequest);
    }

    private void cargarDatosDoctores(String respuesta) {

        String textoDoctores = "";
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++) {
                Doctor d = new Doctor();
                d.setCodigo(jsonArreglo.getJSONObject(i).getString("codigo"));
                d.setNombres(jsonArreglo.getJSONObject(i).getString("nombres"));
                d.setApePa(jsonArreglo.getJSONObject(i).getString("apellidoPaterno"));
                d.setApeMa(jsonArreglo.getJSONObject(i).getString("apellidoMaterno"));
                d.setExp(jsonArreglo.getJSONObject(i).getString("añoexperiencia"));
                d.setPrecio(jsonArreglo.getJSONObject(i).getString("precio"));
                d.setModalidad(jsonArreglo.getJSONObject(i).getString("modalidad"));

                if (d.estaVacio()) {
                    textoDoctores += "El elemento " + i + " está vacío\n";
                } else lista.add(d);
            }

            for (int i = 0; i < lista.size(); i++) {
                textoDoctores += "Nombres: " + lista.get(i).getNombres() + "\nApellidos: " + lista.get(i).getApePa() + " " + lista.get(i).getApeMa()
                                    + "\nCódigo: " + lista.get(i).getCodigo() + "\nAños de experiencia: " + lista.get(i).getExp() +
                                    "\nModalidad: " + lista.get(i).getModalidad() + "\nConsulta: " + lista.get(i).getPrecio() + "\n\n";
            }

            txt1.setText(textoDoctores);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Doctores.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}