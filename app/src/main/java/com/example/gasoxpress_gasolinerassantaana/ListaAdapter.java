package com.example.gasoxpress_gasolinerassantaana;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
//Clase encargada de dibujar el diseno de la lista
public class ListaAdapter extends ArrayAdapter<Datos> {
    private final Activity context;
    private final List<Datos> datos;

    public ListaAdapter(Activity context, List<Datos> datos) {
        super(context,R.layout.activity_row,datos);
        this.context=context;
        this.datos=datos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_row,null,true);
        TextView txtTitle=(TextView) rowView.findViewById(R.id.titulo);
        TextView txtSubtitle=(TextView) rowView.findViewById(R.id.descripcion);
        TextView txtubicacion = (TextView) rowView.findViewById(R.id.ubicacion);
        ImageView img= (ImageView) rowView.findViewById(R.id.imagen);
        Datos item=datos.get(position);
        txtTitle.setText(item.getGasolinera());
        txtSubtitle.setText(item.getDescripcion());
        txtubicacion.setText(item.getLatitud()+""+item.getLongitud());
        if (item.getGasolinera().equals("UNO")) {
            img.setImageResource(R.drawable.logo_uno);
        } else if (item.getGasolinera().equals("Puma Gas Station")) {
            img.setImageResource(R.drawable.logo_puma);
        } else if (item.getGasolinera().equals("DSC")) {
            img.setImageResource(R.drawable.logo_dsc);
        } else if (item.getGasolinera().equals("Texaco")){
            img.setImageResource(R.drawable.logo_texaco);
        }else if (item.getGasolinera().equals("KFC Gas Station")){
            img.setImageResource(R.drawable.logo_ckf);
        }else if (item.getGasolinera().equals("Seleccione una gasolinera")){

        }
        return rowView;
    }
}
