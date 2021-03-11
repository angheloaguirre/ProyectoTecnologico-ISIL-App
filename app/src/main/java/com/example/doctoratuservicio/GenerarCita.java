package com.example.doctoratuservicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class GenerarCita extends AppCompatActivity {

    Button btnGenerarCita;
    ImageButton ib13,ib10,ib4;
    ImageSlider imageSlider, imageSlider1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_cita);

        btnGenerarCita = (Button) findViewById(R.id.btnGenerarCita);
        btnGenerarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenerarCita.this, Filtros.class);
                GenerarCita.this.startActivity(intent);
            }
        });
        ib13 = (ImageButton) findViewById(R.id.imageButton13);
        ib13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerarCita.this, GenerarCita.class);
                GenerarCita.this.startActivity(intent);
            }
        });

        ib10 = (ImageButton) findViewById(R.id.imageButton10);
        ib10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerarCita.this, Creditos_Pantalla.class);
                GenerarCita.this.startActivity(intent);
            }
        });

        ib4 = (ImageButton) findViewById(R.id.imageButton4);
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerarCita.this, Inicio.class);
                GenerarCita.this.startActivity(intent);
            }
        });

        imageSlider = findViewById(R.id.imagSlider1);
        int[] imag = new int[]{
                R.drawable.doctor1,
                R.drawable.doctor2,
                R.drawable.doctor3,
        };

        imageSlider1 = findViewById(R.id.imagSlider2);
        int[] image = new int[]{
                R.drawable.tip1,
                R.drawable.tip2,
                R.drawable.tip3,
        };

        for (int i = 0; i < imag.length; i++) {
            flipperImages(imag[i]);
        }

        for (int i = 0; i < image.length; i++) {
            flipperImages2(image[i]);
        }
    }

    public void flipperImages(int image) {
        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(image);

        imageSlider.addView(iv);
    }

    public void flipperImages2(int image) {
        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(image);

        imageSlider1.addView(iv);
    }
}