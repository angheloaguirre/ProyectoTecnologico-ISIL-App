package com.example.doctoratuservicio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.Calendar;

import ConnectionDB.RegisterRequest;
import ConnectionDB.VerificarUserRequest;

public class Registro extends AppCompatActivity {

    private Spinner spnTipoDocumento;
    EditText edtNombre, edtApePa, edtApeMa, edtContraseña, edtUsuario, edtFechaNac, edtCelular;
    Button btnRegistrar;
    CheckBox cbAceptar;
    String estadoCaja = "";
    private int dia,mes,ano;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Calendar C = Calendar.getInstance();
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);

        edtNombre = (EditText) findViewById(R.id.edtNombres);
        edtApePa = (EditText) findViewById(R.id.edtApelliPaterno);
        edtApeMa = (EditText) findViewById(R.id.edtApellidoMaterno);
        edtContraseña = (EditText) findViewById(R.id.edtContraseña);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtFechaNac = (EditText) findViewById(R.id.edtFechaNacimiento);

        edtFechaNac.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });

        /*edtFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( v == edtFechaNac){
                    final Calendar c= Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    ano=c.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(Registro.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            //String anio= Integer.toString(year);
                            if (year <= 1954) {
                                Toast.makeText(getApplicationContext(), "Escoja su año correcto", Toast.LENGTH_SHORT).show();
                            } else if (year >= 2003) {
                                Toast.makeText(getApplicationContext(), "Debe ser mayor de edad", Toast.LENGTH_SHORT).show();
                            }else  {
                                edtFechaNac.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }
                    }
                            ,dia,mes,ano);
                    datePickerDialog.show();
                }
            }
        });*/

        edtCelular = (EditText) findViewById(R.id.edtCelular);
        cbAceptar = (CheckBox) findViewById(R.id.cbAceptar);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrarse);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbAceptar.isChecked()) {
                    verificarExistenciaDeUsuario();
                } else {
                    Toast.makeText(Registro.this, "Por favor acepte compartir los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void colocar_fecha() {
        mMonthIni++;
        Calendar c1 = Calendar.getInstance();
        dia = (c1.get(Calendar.DATE));
        mes = (c1.get(Calendar.MONTH));
        mes++;
        ano = (c1.get(Calendar.YEAR));

        if (mYearIni <= 1954) {
            Toast.makeText(getApplicationContext(), "Escoja su año correcto", Toast.LENGTH_SHORT).show();
        } else if (mYearIni >= 2003) {
            Toast.makeText(getApplicationContext(), "Debe ser mayor de edad", Toast.LENGTH_SHORT).show();
        } else  {
            edtFechaNac.setText(mDayIni + "/" + mMonthIni + "/" + mYearIni);
        }
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    public void loguearCheckbox(View v) {
        estadoCaja = "Estado: " + (cbAceptar.isChecked() ? "Si" : "No");
    }

    public void registrar() {

        final String nombres = edtNombre.getText().toString();
        final String apellidopaterno = edtApePa.getText().toString();
        final String apellidomaterno = edtApeMa.getText().toString();
        final String contraseña = edtContraseña.getText().toString();
        final String usuario = edtUsuario.getText().toString();
        final String fechanac = edtFechaNac.getText().toString();
        final String numcelular = edtCelular.getText().toString();

        if (nombres.equals("") || apellidomaterno.equals("") || apellidopaterno.equals("") || contraseña.equals("") || usuario.equals("") || fechanac.equals("") || numcelular.equals("")) {
            Toast.makeText(Registro.this, "Por favor complete todas las casillas.", Toast.LENGTH_SHORT).show();
        } else {

            Response.Listener<String> respoListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonReponse = new JSONObject(response);
                        boolean success = jsonReponse.getBoolean("success");

                        if (success) {
                            Intent intent = new Intent(Registro.this, Inicio.class);
                            Registro.this.startActivity(intent);
                            Toast.makeText(Registro.this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
                            builder.setMessage("error de registro.")
                                    .setNegativeButton("Retry", null)
                                    .create().show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };


            RegisterRequest registerRequest = new RegisterRequest(nombres, apellidopaterno, apellidomaterno, contraseña, usuario, fechanac, numcelular, respoListener);
            RequestQueue queue = Volley.newRequestQueue(Registro.this);
            queue.add(registerRequest);

        }

    }

    public void verificarExistenciaDeUsuario() {

        String usuario;
        usuario = edtUsuario.getText().toString();

        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonReponse = new JSONObject(response);
                    boolean success = jsonReponse.getBoolean("success");

                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
                        builder.setMessage("El Usuario ya existe,\npor favor elija otro.")
                                .setNegativeButton("Reintentar", null)
                                .create().show();

                    } else {
                        registrar();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        VerificarUserRequest verificarUserRequest = new VerificarUserRequest(usuario, respoListener);
        RequestQueue queue = Volley.newRequestQueue(Registro.this);
        queue.add(verificarUserRequest);
    }

}

