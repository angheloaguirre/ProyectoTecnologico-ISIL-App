package com.example.doctoratuservicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import ConnectionDB.Doctor;

public class Terminos_Pantalla extends AppCompatActivity {

    Button ok;
    Intent i;
    ImageButton ib14,ib11,ib9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos__pantalla);

        ib14 = (ImageButton) findViewById(R.id.imageButton14);
        ib14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terminos_Pantalla.this, GenerarCita.class);
                Terminos_Pantalla.this.startActivity(intent);
            }
        });

        ib11 = (ImageButton) findViewById(R.id.imageButton11);
        ib11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terminos_Pantalla.this, Creditos_Pantalla.class);
                Terminos_Pantalla.this.startActivity(intent);
            }
        });

        ib9 = (ImageButton) findViewById(R.id.imageButton9);
        ib9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terminos_Pantalla.this, Inicio.class);
                Terminos_Pantalla.this.startActivity(intent);
            }
        });

        i = getIntent();
        final String citaFecha = i.getStringExtra("fecha");
        final String cod = i.getStringExtra("codigo");
        final int pos = i.getIntExtra("hora", 0);
        ok = (Button) findViewById(R.id.btnOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terminos_Pantalla.this, DoctorSeleccionado.class);
                intent.putExtra("codigo", cod);
                intent.putExtra("hora", pos);
                DoctorSeleccionado.fecha = citaFecha;
                DoctorSeleccionado.aceptarCondiciones = true;
                startActivity(intent);
            }
        });
    }
}