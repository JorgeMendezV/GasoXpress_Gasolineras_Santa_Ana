package com.example.gasoxpress_gasolinerassantaana;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class agregar extends AppCompatActivity {

    // no podemos crear valores similares en los ID dentro del XML.
    private EditText txtLongitud, txtLatitud, txtDescripcion;
    // Displays image resources, for example Bitmap or Drawable resources.
    // ImageView is also commonly used to apply tints to an image and handle image scaling.
    private ImageView img;
    // A view that displays one child at a time and lets the user pick among them
    private Spinner spinner;
    // nos permite guardar donde esta almacenado...
    private String attachFileName = "";
    // This class is used to store a set of values that the ContentResolver can process.
    private ContentValues values;
    // Uniform Resource Identifier (URI)
    private Uri imageUri;
    //
    private static final int PICTURE_RESULT = 122;
    // Objeto para almacenar imagenes
    private Bitmap thumbnail;
    // This class provides access to the system location services.
    private LocationManager locationManager;


    // logica para permisos
    // 124 este valor no cambiara
    // es el valor que devolvera si se aceptan todos los permisos
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSION = 124;


    // este metodo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // los spinner son una forma eficiente de seleccionar un valor de un conjunto.
        spinner = (Spinner) findViewById(R.id.spinner);

        // modelo
        List<String> gasolineras = new ArrayList<String>();
        // simplemente agregamos una serie de datos al array nada diferente.
        gasolineras.add("UNO");
        gasolineras.add("Puma Gas Station");
        gasolineras.add("DSC");
        gasolineras.add("Texaco");
        gasolineras.add("KFC Gas Station");

        // adaptador
        // se puede utilizar arrayAdapter para ver datos de AdapterView, retorna
        // una vista por cada objeto en la collection que nosotros le proporcionamos.
        // tambien puede ser usado por listView o Spinner.
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,gasolineras);

        // theme is a set of values for resource attributes; TypedArrays
        // asigna el valor actual de un theme particular.
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(dataAdapter);
    }



}