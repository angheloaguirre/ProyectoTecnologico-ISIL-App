package com.example.doctoratuservicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ConnectionDB.Doctor;
import ConnectionDB.ListaDoctoresRequest;

public class RecyclerCardView extends AppCompatActivity {

    Intent intent;
    ArrayList<Doctor> lista = new ArrayList<Doctor>();
    List<ListElement> elements;
    RecyclerView rv;
    ImageButton ib25,ib20,ib19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_card_view);
        rv = (RecyclerView) findViewById(R.id.listRecyclerView);

        ib25 = (ImageButton) findViewById(R.id.imageButton25);
        ib25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerCardView.this, GenerarCita.class);
                RecyclerCardView.this.startActivity(intent);
            }
        });

        ib20 = (ImageButton) findViewById(R.id.imageButton20);
        ib20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerCardView.this, Creditos_Pantalla.class);
                RecyclerCardView.this.startActivity(intent);
            }
        });

        ib19 = (ImageButton) findViewById(R.id.imageButton19);
        ib19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerCardView.this, Inicio.class);
                RecyclerCardView.this.startActivity(intent);
            }
        });

        intent = getIntent();
        buscarDoctores();
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
                    Toast.makeText(RecyclerCardView.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        ListaDoctoresRequest listaRequest = new ListaDoctoresRequest(intent.getStringExtra("especialidad"),
                intent.getStringExtra("distrito"), intent.getStringExtra("modalidad"), respoListener);
        RequestQueue queue = Volley.newRequestQueue(RecyclerCardView.this);
        queue.add(listaRequest);
    }

    private void cargarDatosDoctores(String respuesta) {

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

                } else lista.add(d);
            }

            init();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RecyclerCardView.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init() {
        elements = new ArrayList();

        for (int i = 0; i < lista.size(); i++) {
            ListElement e =  new ListElement();
            e.setColor("#775447");
            e.setNombre(lista.get(i).getNombres() + "\n" + lista.get(i).getApePa() + " " + lista.get(i).getApeMa());
            e.setCodigo(lista.get(i).getCodigo());
            e.setModalidad("Modalidad: " + lista.get(i).getModalidad());
            e.setPrecioConsulta("Consulta:\n" + lista.get(i).getPrecio());
            elements.add(e);
        }

        ListAdapter listAdapter = new ListAdapter(elements, this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(listAdapter);
    }
}