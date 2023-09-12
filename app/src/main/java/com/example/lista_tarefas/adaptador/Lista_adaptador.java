package com.example.lista_tarefas.adaptador;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista_tarefas.InserirTarefa;
import com.example.lista_tarefas.MainActivity;
import com.example.lista_tarefas.R;
import com.example.lista_tarefas.dao.Tarefadao;
import com.example.lista_tarefas.helper.SQLiteDataBaseHelper;
import com.example.lista_tarefas.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Lista_adaptador extends RecyclerView.Adapter<Lista_adaptador.MyViewHolder> {

    private List<Tarefa> tList;
    private MainActivity activity;
    private Tarefadao tarefadao;


    public Lista_adaptador(Tarefadao tarefadao, MainActivity activity){
        this.activity = activity;
        this.tarefadao = tarefadao;
        this.tList = new ArrayList<>();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Tarefa item = tList.get(position);
        holder.mCheckBox.setText(item.getTarefa());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deletTask(holder.getAbsoluteAdapterPosition());
                return true;
            }
        });
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tarefadao.atualizarstatus(item.getId(), String.valueOf(1));
                }else
                    tarefadao.atualizarstatus(item.getId(), String.valueOf(0));
            }
        });

    }
    public boolean toBoolean(int num){
        return num !=0;
    }
    public Context getContext(){
        return activity;

    }
    public void setTask(List<Tarefa>tList){
        this.tList=tList;
        notifyDataSetChanged();
    }

    public void deletTask(int position){
        Tarefa item = tList.get(position);
       boolean deletd = tarefadao.deletar(item.getId());
       if (deletd){
           tList.remove(position);
           notifyItemRemoved(position);

       }

    }
    public void editItem(int position){
        Tarefa item = tList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("ID",item.getId());
        bundle.putString("TAREFA",item.getTarefa());
        InserirTarefa tarefa = new InserirTarefa();
        tarefa.setArguments(bundle);
        tarefa.show(activity.getSupportFragmentManager(), tarefa.getTag());
    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.todoCheckBox);

        }
    }
}
