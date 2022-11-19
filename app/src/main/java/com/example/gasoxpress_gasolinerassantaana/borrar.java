package com.example.gasoxpress_gasolinerassantaana;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class borrar extends AppCompatActivity {
    private List<Datos> items;
    //Metodo para borrar los datos a la tabla de gasoxpress
    public void deleteItem(View view, int i) {
        Datos item=items.get(i);
        abrirDB base= new abrirDB(this,"gasolinerasT",null,1);
        SQLiteDatabase db = base.getWritableDatabase();
        ContentValues id = new ContentValues();
        id.get("_id");
        db.delete("gasoxpress", id + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
        Toast.makeText(this,"Se elimino correctamente", Toast.LENGTH_SHORT).show();
        System.out.println("Intentando eliminar");
    }

    public void deleteItem(Object item) {
        listArray.remove(item);
        notifyDataSetChanged();

    }

}
