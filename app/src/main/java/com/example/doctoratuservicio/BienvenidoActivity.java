package com.example.doctoratuservicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class BienvenidoActivity extends AppCompatActivity {

    Button btnSaltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        ImageSlider imageSlider = findViewById(R.id.flipper_bienvenida);
            int[] imag = new int[]{
                R.drawable.bienvenidos,
                R.drawable.doctores,
                R.drawable.cita,
        };

        btnSaltar = (Button) findViewById(R.id.btnSaltar);
        btnSaltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BienvenidoActivity.this, Inicio.class);
                BienvenidoActivity.this.startActivity(intent);
            }
        });
    }
}
