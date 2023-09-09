package com.example.lista_tarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lista_tarefas.model.Tarefa;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "my_database.db";
    private static final String TABLE_NAME ="tabela_tarefa";
    private static final String COL_1 ="ID";
    private static final String COL_2 ="TAREFAS";
    private static final String COL_3 ="STATUS";

    public SQLiteDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, TAREFAS TEXT, STATUS INTEGER)");
            Log.i("INFO DB", "tabela criada ");
        }catch (Exception ex){
            Log.i("INFO DB", "erro na criação" + ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);


    }


}
