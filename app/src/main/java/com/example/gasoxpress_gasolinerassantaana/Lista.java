package com.example.gasoxpress_gasolinerassantaana;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{
    //Propiedades
    public int id, index;
    private List<Datos> items;
    private ListaAdapter adapter;
    private ListView list;

    public int getIndex() {return index;}

    public void setIndex(int index) { this.index = index;}

    public int obtenerId() {
        return id;
    }

    public void asignarId(int id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        list=(ListView) findViewById(R.id.lista);
        list.setOnItemClickListener(this);
        llenarDatos();
        list.setOnItemLongClickListener(this::onItemLongClick);
    }



    //El valor i, representa la posicion del elemento que nosotros hicimos touch
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        //lanzamos un parametro del id, el valor que enviamos es el codigo del resgistro que le hicimos touch
        Datos item=items.get(i);
        // Aqui se almacena el ID
        setIndex(i);
        System.out.println("onItemLongClick index: " + getIndex() + ", Id registro : " + item.getId());
        //lanzamos un parametro del id, el valor que enviamos es el codigo del resgistro que le hicimos touch
        try {
            abrirDB base= new abrirDB(this,"gasolinerasT",null,1);
            list.setLongClickable(true);
            list.setClickable(true);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    new AlertDialog.Builder(Lista.this)
                            .setTitle("Quieres eliminar la gasolinera de la lista?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SQLiteDatabase db = base.getWritableDatabase();
                                    System.out.println(item.getId());
                                    db.execSQL("DELETE FROM gasoxpress WHERE _id =" + item.getId());
                                    items.remove(getIndex());
                                    adapter.notifyDataSetChanged();
                                    finish(); startActivity(getIntent());
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                    return false;
                }
            });
        } catch (Exception error){
            System.out.println("Ocurrio un error al eliminar Item : " + error);
        }
        return false;
    }

    //El valor i, representa la posicion del elemento que nosotros hicimos touch
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Datos item=items.get(i);
        Intent m=new Intent(this,Mapa.class);
        // Aqui se almacena el ID
        setIndex(i);
        System.out.println("ID en onItemClick " + getIndex());
        //lanzamos un parametro del id, el valor que enviamos es el codigo del resgistro que le hicimos touch
        m.putExtra("id",Long.toString(item.getId()));
        startActivity(m);
    }

    public void llenarDatos() {
        items=new ArrayList<Datos>();
        abrirDB base=new abrirDB(this,"gasolinerasT",null,1);
        SQLiteDatabase bd=base.getReadableDatabase();
        String columns[]=new String[]{"_id","gasolinera","latitud","longitud","descripcion","foto"};
        Cursor c=bd.query("gasoxpress",columns,null,null,null,null,null,null);
        if (c.moveToFirst()) {
            do {
                asignarId(c.getInt(0));
                items.add(new Datos(c.getInt(0),c.getString(4),c.getString(1),c.getString(2),c.getString(3),c.getString(5)));
            } while (c.moveToNext());
        }
        adapter = new ListaAdapter(Lista.this,items);
        list.setAdapter(adapter);
        bd.close();
    }
}