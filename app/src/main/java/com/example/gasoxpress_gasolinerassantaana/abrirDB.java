package com.example.gasoxpress_gasolinerassantaana;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class abrirDB extends SQLiteOpenHelper {

    // SQL Lite no tiene tantos tipos de datos para almacenar.
    // se encargara de crear la base de datos si no existe.
    public abrirDB(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE gasoxpress(_id INTEGER PRIMARY KEY AUTOINCREMENT, gasolinera text, latitud text, longitud text, descripcion text, foto text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnter, int versionNuev){
        db.execSQL("drop table if exists gasoxpress");
        db.execSQL("CREATE TABLE gasoxpress(_id INTEGER PRIMARY KEY AUTOINCREMENT, gasolinera text, latitud text, longitud text, descripcion text, foto text)");
    }

}
