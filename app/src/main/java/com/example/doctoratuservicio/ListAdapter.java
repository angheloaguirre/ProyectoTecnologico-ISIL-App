package com.example.doctoratuservicio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> lista;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter (List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.lista = itemList;
    }

    @Override
    public int getItemCount() { return lista.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(lista.get(position));
    }

    public void setItems(List<ListElement> items) { lista = items; }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView nombre, codigo, modalidad, precio;
        String cod = "";

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nombreDoctorTextView);
            codigo = itemView.findViewById(R.id.codigoTextView);
            modalidad = itemView.findViewById(R.id.modalidadTextView);
            precio = itemView.findViewById(R.id.precioConsultaTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    Intent intent = new Intent(context, DoctorSeleccionado.class);
                    intent.putExtra("codigo", cod);
                    context.startActivity(intent);
                }
            });
        }

        void bindData(final ListElement item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getNombre());
            cod = item.getCodigo();
            codigo.setText("Código: " + item.getCodigo());
            modalidad.setText(item.getModalidad());
            precio.setText(item.getPrecioConsulta());
        }
    }
}
