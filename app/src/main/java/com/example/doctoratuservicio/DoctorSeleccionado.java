package com.example.doctoratuservicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;

import ConnectionDB.CitaRequest;
import ConnectionDB.Doctor;
import ConnectionDB.DoctorRequest;
import ConnectionDB.ListaDoctoresRequest;
import ConnectionDB.RegisterRequest;
import ConnectionDB.VerificarCitaDisponibleRequest;

public class DoctorSeleccionado extends AppCompatActivity {

    public static boolean aceptarCondiciones = false;
    public static String citaFecha ="", fecha = "";
    Button btnAgendarCita;
    Intent intent;
    String cod = "";
    Doctor d;

    TextView txtNomApe, txtHorarios;
    ImageView imgDoc;
    Spinner spnHor;

    ImageButton ib5,ib7,ib8;

    EditText edtFech;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    private int dia, mes, ano;
    Calendar C = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_seleccionado);

        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        edtFech = (EditText) findViewById(R.id.edtFecha);

        edtFech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_ID);
            }
        });

        ib5 = (ImageButton) findViewById(R.id.imageButton5);
        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorSeleccionado.this, GenerarCita.class);
                DoctorSeleccionado.this.startActivity(intent);
            }
        });

        ib7 = (ImageButton) findViewById(R.id.imageButton7);
        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorSeleccionado.this, Creditos_Pantalla.class);
                DoctorSeleccionado.this.startActivity(intent);
            }
        });

        ib8 = (ImageButton) findViewById(R.id.imageButton8);
        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorSeleccionado.this, Inicio.class);
                DoctorSeleccionado.this.startActivity(intent);
            }
        });

        spnHor = (Spinner) findViewById(R.id.spnHorarios);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.horario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHor.setAdapter(adapter);

        txtNomApe = (TextView) findViewById(R.id.txtNombresApellidos);
        txtHorarios = (TextView) findViewById(R.id.txtHorarios);
        imgDoc = (ImageView) findViewById(R.id.imageDoctor);
        imgDoc.setColorFilter(Color.parseColor("#775447"), PorterDuff.Mode.SRC_IN);
        intent = getIntent();
        cod = intent.getStringExtra("codigo");
        int pos = intent.getIntExtra("hora", 0);
        spnHor.setSelection(pos);
        obtenerDatos();

        btnAgendarCita = (Button) findViewById(R.id.btnAgendarCita);
        btnAgendarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String horario = spnHor.getSelectedItem().toString();
                String fech = edtFech.getText().toString();
                fecha = fech;
                String cita = fech + horario;
                citaFecha = cita;
                abrirTerminos(citaFecha);
            }
        });

        edtFech.setText(fecha);
    }

    private void abrirTerminos(final String cita) {
        final String[] respuesta = {""};

        if (edtFech.getText().toString().equals("")) {
            Toast.makeText(DoctorSeleccionado.this, "Por favor complete todas las casillas.", Toast.LENGTH_SHORT).show();
        }
        else {
            Response.Listener<String> respoListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonReponse = new JSONArray(response);
                        respuesta[0] = jsonReponse.getJSONObject(0).getString("citas");
                        String resp = respuesta[0];

                        if (resp.equals(cita)) {
                            Toast.makeText(DoctorSeleccionado.this, "Ese horario ya está separado, por favor elija otro", Toast.LENGTH_SHORT).show();
                        } else {
                            if (aceptarCondiciones) {
                                aceptarCondiciones = false;
                                generarCita(citaFecha);
                            } else {
                                Intent intent = new Intent(DoctorSeleccionado.this, Terminos_Pantalla.class);
                                intent.putExtra("fecha", fecha);
                                intent.putExtra("codigo", cod);
                                intent.putExtra("hora", spnHor.getSelectedItemPosition());
                                startActivity(intent);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DoctorSeleccionado.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            };

            VerificarCitaDisponibleRequest verificarCita = new VerificarCitaDisponibleRequest(cod, respoListener);
            RequestQueue queue = Volley.newRequestQueue(DoctorSeleccionado.this);
            queue.add(verificarCita);
        }
    }

    private void generarCita(String cita) {
        Response.Listener<String> respoListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonReponse2= new JSONArray(response);
                    Toast.makeText(DoctorSeleccionado.this, "¡Cita generada!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e2) {
                    e2.printStackTrace();
                    Toast.makeText(DoctorSeleccionado.this, e2.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };


        CitaRequest citaRequest = new CitaRequest(cod, cita, respoListener2);
        RequestQueue queue2 = Volley.newRequestQueue(DoctorSeleccionado.this);
        queue2.add(citaRequest);
    }

    private void colocar_fecha() {
        mMonthIni++;
        Calendar c1 = Calendar.getInstance();
        dia = (c1.get(Calendar.DATE));
        mes = (c1.get(Calendar.MONTH));
        mes++;
        ano = (c1.get(Calendar.YEAR));
        if (mDayIni <= dia && mMonthIni <= mes && mYearIni <= ano) {
            Toast.makeText(getApplicationContext(),"Escoja fecha próxima\nLa fecha de la cita no debe pasar de 2 meses", Toast.LENGTH_SHORT).show();
        } else if (mMonthIni - mes <= 2 && mMonthIni - mes > -1) {
            edtFech.setText((mDayIni + "/") + mMonthIni + "/" + mYearIni + " ");
        } else {
            Toast.makeText(getApplicationContext(),"Escoja fecha próxima\nLa fecha de la cita no debe pasar de 2 meses", Toast.LENGTH_SHORT).show();
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

    public void obtenerDatos() {
        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonReponse = new JSONArray(response);
                    cargarDatosDoctores(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DoctorSeleccionado.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        DoctorRequest doctorRequest = new DoctorRequest(cod, respoListener);
        RequestQueue queue = Volley.newRequestQueue(DoctorSeleccionado.this);
        queue.add(doctorRequest);
    }

    private void cargarDatosDoctores(String respuesta) {

        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);

            d = new Doctor();
            d.setCodigo(jsonArreglo.getJSONObject(0).getString("codigo"));
            d.setNombres(jsonArreglo.getJSONObject(0).getString("nombres"));
            d.setApePa(jsonArreglo.getJSONObject(0).getString("apellidoPaterno"));
            d.setApeMa(jsonArreglo.getJSONObject(0).getString("apellidoMaterno"));
            d.setExp(jsonArreglo.getJSONObject(0).getString("añoexperiencia"));
            d.setPrecio(jsonArreglo.getJSONObject(0).getString("precio"));
            d.setModalidad(jsonArreglo.getJSONObject(0).getString("modalidad"));
            d.setEspecialidad(jsonArreglo.getJSONObject(0).getString("especialidad"));
            d.setNumCelular(jsonArreglo.getJSONObject(0).getString("numCelular"));
            d.setCorreo(jsonArreglo.getJSONObject(0).getString("correo"));
            d.setDireccion(jsonArreglo.getJSONObject(0).getString("direccion"));

            txtNomApe.setText(d.toString());

            String nomApe = d.getNombres() + "\n" + d.getApePa() + " " + d.getApeMa();
            String esp = "Especialidad: " + d.getEspecialidad();
            String cod = "CÓDIGO: " + d.getCodigo();
            String exp = "Años de experiencia: " + d.getExp();
            String dir = "Dirección: " + d.getDireccion();
            String cel = "Número de celular: " + d.getNumCelular();
            String moda = "Modalidad: " + d.getModalidad();
            String correo = "Correo electrónico: " + d.getCorreo();
            String precio = "Consulta: " + d.getPrecio();

            txtNomApe.setText(nomApe + "\n" + esp + "\n" + cod + "\n" + exp + "\n" + moda + "\n" + dir +"\n" + cel + "\n" + correo + "\n" + precio);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(DoctorSeleccionado.this, e.getMessage() + intent.getStringExtra("codigo"), Toast.LENGTH_LONG).show();
        }
    }
}