package com.example.gasoxpress_gasolinerassantaana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // el metodo findID devuelve un objeto, es necesario hacer casting.
        txtLongitud = (EditText) findViewById(R.id.txtLongitud);
        txtLongitud = (EditText) findViewById(R.id.txtLatitud);
        txtLongitud = (EditText) findViewById(R.id.txtDescripcion);
        img = (ImageView) findViewById(R.id.fotoGasolinera);

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

        // verificar la version del celular para asignar permisos

        if(Build.VERSION.SDK_INT >= 23){
            //
            System.out.println("Version de SDK" + Build.VERSION.SDK_INT);
            checkPermission();

        }
        //
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    private void checkPermission() {
        //
        List<String> permission = new ArrayList<String>();
        String message = "";
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
            message = "\n permiso de localizacion";
        }

        //
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.CAMERA);
            message = "\n permiso de usar camara";
        }

        //
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            message = "\n permiso de almacenamiento de imagen";
        }

        //
        if (!permission.isEmpty()){
            System.out.println(message);
            //
            String[] parents = permission.toArray(new String[permission.size()]);
            if (Build.VERSION.SDK_INT >= 23){
                //
                requestPermissions(parents, REQUEST_CODE_ASK_MULTIPLE_PERMISSION);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            // si el valor es 124
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSION: {
                // estructura de tipo clave valor, vector unidimensional, solamente se tiene valor y clave asociada.
                // base de datos tipo clave valor.
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                //
                for (int i = 0; i < permissions.length; i++){
                    perms.put(permissions[i], grantResults[i]);
                }
                //
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                //
                if (location && storage){
                    System.out.println("All permission granted");
                } else if (storage) {
                    System.out.println("Storage permission is required to store map tile to reduce data usage and for offline usage");

                }else if (location) {
                    System.out.println("Location permission is required to show the users location on map");

                } else {
                    System.out.println("Storage permission is required to store map tile to reduce data usage and for offline usage" +
                            "\nLocation permission is required to show the users location on map");
                }
            }
            break;
            //
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}