package com.example.gasoxpress_gasolinerassantaana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    // Que es onCreate?
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void salir(View view){
        System.exit(0);
    }

    // creamos una intencion para acceder a otro activity
    public void ejecutarAgregar(View view){
        Intent obj = new Intent(this,agregar.class);
        this.startActivity(obj);
    }

    public void ejecutarLista(View view){
        Intent obj = new Intent (this,Lista.class);
        this.startActivity(obj);
    }

    public void ejecutarAcercaDe(View view){
        Intent obj = new Intent(this,acercaDe.class);
        this.startActivity(obj);

    }

}