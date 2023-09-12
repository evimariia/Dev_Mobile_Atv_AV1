package com.example.lista_tarefas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista_tarefas.adaptador.Lista_adaptador;
import com.example.lista_tarefas.dao.Tarefadao;
import com.example.lista_tarefas.helper.SQLiteDataBaseHelper;
import com.example.lista_tarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {

    private RecyclerView tasksRecyclerView;
    private FloatingActionButton fab;

    private SQLiteDataBaseHelper db;
    private List<Tarefa> tList;
    private Lista_adaptador adaptador;
    private Tarefadao tarefadao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tarefadao = new Tarefadao(this);
        RecyclerView recyclerView = findViewById(R.id.tasksRecyclerView);
        fab = findViewById(R.id.fab);
        db = new SQLiteDataBaseHelper(MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adaptador = new Lista_adaptador(tarefadao, MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptador);

        // Inicialize a lista tList antes de usá-la
        tList = tarefadao.listarTarefas();
        if (tList != null) {
            Collections.reverse(tList);
            adaptador.setTask(tList);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InserirTarefa.newInstance().show(getSupportFragmentManager(), InserirTarefa.TAG);
            }
        });
    }



    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        // Atualize a lista tList quando o diálogo for fechado
        tList = tarefadao.listarTarefas();
        if (tList != null) {
            Collections.reverse(tList);
            adaptador.setTask(tList);
            adaptador.notifyDataSetChanged();

        }
    }
}
