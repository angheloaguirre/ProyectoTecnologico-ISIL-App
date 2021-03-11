package com.example.doctoratuservicio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ConnectionDB.LoginRequest;
import ConnectionDB.RegisterRequest;

public class Inicio extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText edtUsuario, edtContraseña;
    public static boolean sesionIniciada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtContraseña = (EditText) findViewById(R.id.edtContraseña);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtUsuario.getText().toString().equals("") || edtContraseña.getText().toString().equals("")) {
                    Toast.makeText(Inicio.this, "Por favor, complete todos los datos.", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                    edtUsuario.getText().clear();
                    edtContraseña.getText().clear();
                    sesionIniciada = true;
                }

            }
        });

        btnRegister = (Button) findViewById(R.id.btnReg);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(Inicio.this, Registro.class);
                startActivity(siguiente);
            }
        });

    }

    public void login() {

        String usuario, contraseña;
        usuario = edtUsuario.getText().toString();
        contraseña = edtContraseña.getText().toString();

        Response.Listener<String> respoListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonReponse = new JSONObject(response);
                    boolean success = jsonReponse.getBoolean("success");

                    if (success) {
                        Intent intent = new Intent(Inicio.this, GenerarCita.class);
                        Inicio.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                        builder.setMessage("Error de Inicio de Sesión:\nCorreo electrónico y/o contraseña\nincorrectos o no registrados.")
                                .setNegativeButton("Reintentar", null)
                                .create().show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Inicio.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(usuario, contraseña,  respoListener);
        RequestQueue queue = Volley.newRequestQueue(Inicio.this);
        queue.add(loginRequest);
    }

    @Override
    public void onBackPressed() { }
}