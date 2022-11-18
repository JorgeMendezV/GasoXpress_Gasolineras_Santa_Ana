package com.example.gasoxpress_gasolinerassantaana;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        txtLatitud = (EditText) findViewById(R.id.txtLatitud);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        img = (ImageView) findViewById(R.id.fotoGasolinera);

        // los spinner son una forma eficiente de seleccionar un valor de un conjunto.
        spinner = (Spinner) findViewById(R.id.spinner);

        // modelo
        List<String> gasolineras = new ArrayList<String>();

        // simplemente agregamos una serie de datos al array nada diferente.

        gasolineras.add(0,"Seleccione una gasolinera");
        gasolineras.add("UNO");
        gasolineras.add("Puma Gas Station");
        gasolineras.add("DSC");
        gasolineras.add("Texaco");
        gasolineras.add("KFC Gas Station");

        // adaptador
        // se puede utilizar arrayAdapter para ver datos de AdapterView, retorna
        // una vista por cada objeto en la collection que nosotros le proporcionamos.
        // tambien puede ser usado por listView o Spinner.
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.style_spinner,gasolineras);

        // theme is a set of values for resource attributes; TypedArrays
        // asigna el valor actual de un theme particular.
        dataAdapter.setDropDownViewResource(R.layout.style_spinner);
        spinner.setAdapter(dataAdapter);

        // sobre escribimos los metodos onItemSelected que nos permite manipular los datos seleccionado
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Seleccione una gasolinera")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // verificar la version del celular para asignar permisos

        if(Build.VERSION.SDK_INT >= 23){
            //
            System.out.println("Version de SDK" + Build.VERSION.SDK_INT);
            checkPermission();

        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    private void checkPermission() {
        //
        List<String> permission = new ArrayList<String>();
        String message = "";
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
            message = "\n permiso de localizacion";
            System.out.println("");
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
        if(!permission.isEmpty()){
            System.out.println(message);
            String[] parents = permission.toArray(new String[permission.size()]);
            if(Build.VERSION.SDK_INT >= 23){
                requestPermissions(parents, REQUEST_CODE_ASK_MULTIPLE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSION: {
                // structura de tipo de clave valor, vector unidimensional, solamente tengo valor y la clave asociada
                // bases de datos clave valor.

                Map<String, Integer> perms = new HashMap<>();
                // creamos un elemento que se llama permisos
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (location && storage) {
                    System.out.println("All permissions granted");

                } else if (location) {
                    System.out.println("Storage permission is required to store map tiles to reduce data usage and for offline usage.");
                } else if (storage) {
                    System.out.println("Location permission is required to show the user's location on map.");
                } else {
                    System.out.println("Storage permission is required to store map tiles to reduce data usage and for offline usage." +
                            "\nLocation permission is required to show the user's location on map.");

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void obtenerLatLng(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // obtener la ultima ubicacion conocida
        // getboering
        // getSpeed, el celular calcula la velocidad dependiendo del tiempo y el tramo recorrido

        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (loc != null) {
            txtLatitud.setText(String.valueOf(loc.getLatitude()));
            txtLongitud.setText(String.valueOf(loc.getLongitude()));
        }
    }

    // objeto de tipo view, este metodo tirara un error de tipo. Quien manejara el error sera quien envie el error.
    // quien manejara el objeto sera quien mandara a llamar el objeto.
    public void anexarFoto(View view) throws IOException {
        try {
            if(!(Build.VERSION.SDK_INT >= 30)){
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Gasolinera");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Foto tomada el " + System.currentTimeMillis());
                // getContentResolver insert 29 and below API error
                // Getting the error pre android Q (API 29) and my huawei API is 28 so... theres the problem
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, PICTURE_RESULT);
            } else {
                    Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    int code =0;
                    Uri output = FileProvider.getUriForFile(agregar.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            createImageFile());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                    startActivityForResult(intent, code);
                }
        } catch (Exception error){
            System.out.println("API VERSION: " + Build.VERSION.SDK_INT);
            System.out.println("Error de API <= 29 : " + error);
        }
    }

    // Logica AnexarFotos para API >= 30
    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (!(Build.VERSION.SDK_INT >= 30)) {
            if (requestCode == PICTURE_RESULT) {
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    img.setImageBitmap(thumbnail);
                    attachFileName = getRealPathFromUri(imageUri);
                    System.out.println(attachFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (requestCode==0) {
                if (resultCode==RESULT_OK) {
                    Bitmap imagen= BitmapFactory.decodeFile(attachFileName);
                    img.setImageBitmap(imagen);
                }
            }
        }
    }

    private String getRealPathFromUri(Uri imageUri) {
        String[] proj = { MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(imageUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // ----------------------------


    // Logica AnexarFotos para API <= 29
    // para crear un archivo de imagen en algunos dispositivos no compatibles.
    // versiones probadas no compatibles: API 29 Android 10 con Samsung y API 28 con android 9 en Huawei
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        attachFileName=image.getAbsolutePath();
        return image;
    }



    //Metodo para guardar los datos a la tabla de gasoxpress
    public void guardarDatos(View view) {
        abrirDB base= new abrirDB(this,"gasolinerasT",null,1);
        SQLiteDatabase bd= base.getWritableDatabase();
        String gasolinera=spinner.getSelectedItem().toString();
        String latitud=txtLatitud.getText().toString();
        String longitud=txtLongitud.getText().toString();
        String descrip=txtDescripcion.getText().toString();
        System.out.println(gasolinera);
        if (!gasolinera.equals("Seleccione una gasolinera")) {
            ContentValues registro = new ContentValues();
            registro.put("gasolinera", gasolinera);
            registro.put("latitud", latitud);
            registro.put("longitud", longitud);
            registro.put("descripcion", descrip);
            registro.put("foto", attachFileName);
            bd.insert("gasoxpress", null, registro);
            txtLongitud.setText("");
            txtLatitud.setText("");
            txtDescripcion.setText("");
            bd.close();
            Toast.makeText(this, "Se cargaron los datos correctamente",
                    Toast.LENGTH_LONG).show();
        } else {
            bd.close();
            Toast.makeText(this, "Debe seleccionar una gasolinera",
                    Toast.LENGTH_LONG).show();
        }

    }
}