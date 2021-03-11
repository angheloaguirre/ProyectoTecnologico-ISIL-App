package com.example.doctoratuservicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Creditos_Pantalla extends AppCompatActivity {

    ImageButton ib26,ib12,ib6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos__pantalla);

        ib26 = (ImageButton) findViewById(R.id.imageButton26);
        ib26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Creditos_Pantalla.this, GenerarCita.class);
                Creditos_Pantalla.this.startActivity(intent);
            }
        });

        ib12 = (ImageButton) findViewById(R.id.imageButton12);
        ib12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Creditos_Pantalla.this, Creditos_Pantalla.class);
                Creditos_Pantalla.this.startActivity(intent);
            }
        });

        ib6 = (ImageButton) findViewById(R.id.imageButton6);
        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Creditos_Pantalla.this, Inicio.class);
                Creditos_Pantalla.this.startActivity(intent);
            }
        });

    }
}