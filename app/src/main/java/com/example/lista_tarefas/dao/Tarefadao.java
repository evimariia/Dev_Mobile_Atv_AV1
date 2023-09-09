package com.example.lista_tarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lista_tarefas.helper.SQLiteDataBaseHelper;
import com.example.lista_tarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;

public class Tarefadao implements ITarefadao {

    private SQLiteDatabase objwrite;
    private SQLiteDatabase objRead;

    public Tarefadao(Context ctx){
        SQLiteDataBaseHelper db = new SQLiteDataBaseHelper(ctx);
        objwrite= db.getWritableDatabase();
        objRead= db.getReadableDatabase();
    }
    @Override
    public boolean inserir(Tarefa task) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TAREFAS", task.getTarefa());
            contentValues.put("STATUS", task.getStatus());

            long resultado = objwrite.insert("tabela_tarefa", null, contentValues);

            Log.i("DB INFO", "dados inseridos com sucesso ");

            return true; // Retorna true se a inserção foi bem-sucedida
        } catch (Exception ex) {
            Log.i("INFO DB", "falha na insercao dos dados " + ex.getMessage());
            return false; // Retorna false em caso de falha
        }
    }





    @Override
    public boolean atualizar(int id,String task) {

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TAREFAS", task);

            String[] whereArgs = {String.valueOf(id)};
            int resultado = objwrite.update("tabela_tarefa", contentValues, "ID=?", whereArgs);

            if (resultado > 0) {
                Log.i("DB INFO", "Tarefa atualizada com sucesso");
                return true;
            } else {
                Log.i("DB INFO", "Nenhuma tarefa foi atualizada (ID não encontrado)");
                return false;
            }
        } catch (Exception ex) {
            Log.i("INFO DB", "Falha na atualização dos dados " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean atualizarstatus(int id, String status) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("STATUS", status);

            String[] whereArgs = {String.valueOf(id)};
            int resultado = objwrite.update("tabela_tarefa", contentValues, "ID=?", whereArgs);

            if (resultado > 0) {
                // Atualização de status bem-sucedida
                Log.i("DB INFO", "Status da tarefa atualizado com sucesso");
                return true;
            } else {
                // Nenhuma linha foi atualizada, indica que a tarefa não existe
                Log.i("DB INFO", "Nenhuma tarefa foi atualizada (ID não encontrado)");
                return false;
            }
        } catch (Exception ex) {
            Log.i("INFO DB", "Falha na atualização de status da tarefa " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletar(int id) {
        try {
            String[] whereArgs = {String.valueOf(id)};
            int resultado = objwrite.delete("tabela_tarefa", "ID=?", whereArgs);

            if (resultado > 0) {
                // Exclusão bem-sucedida
                Log.i("DB INFO", "Tarefa excluída com sucesso");
                return true;
            } else {
                // Nenhuma linha foi excluída, indica que a tarefa não existe
                Log.i("DB INFO", "Nenhuma tarefa foi excluída (ID não encontrado)");
                return false;
            }
        } catch (Exception ex) {
            Log.i("INFO DB", "Falha na exclusão da tarefa " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Tarefa> listarTarefas() {
        List<Tarefa> lista_tarefa = new ArrayList<>();
        try {
            Cursor cursor = objRead.query("tabela_tarefa", null, null, null, null, null, null);
            int iid = cursor.getColumnIndexOrThrow("ID");
            int itarefa = cursor.getColumnIndexOrThrow("TAREFAS");
            int istatus = cursor.getColumnIndexOrThrow("STATUS");


            cursor.moveToFirst();
            do {
                if (cursor.getCount() == 0) {
                    break;

                }
                Tarefa task = new Tarefa();
                task.setId(Integer.valueOf(cursor.getString(iid)));
                task.setTarefa(cursor.getString(itarefa));
                task.setStatus(Integer.parseInt(cursor.getString(istatus)));

                lista_tarefa.add(task);
            } while (cursor.moveToNext());
            Log.i("INFO DB", "sucesso na listagem ");

        } catch (Exception ex) {
            Log.i("INFO DB", "falha ao listar " + ex.getMessage());
            return null;
        }
        return lista_tarefa;
    }

    }







