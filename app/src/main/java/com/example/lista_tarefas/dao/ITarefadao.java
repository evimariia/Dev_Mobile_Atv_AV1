package com.example.lista_tarefas.dao;

import com.example.lista_tarefas.model.Tarefa;

import java.util.List;

public interface ITarefadao {
    public boolean inserir(Tarefa task);
    public boolean atualizar(int id,String task);

    public boolean atualizarstatus(int id,String status);

    public boolean deletar(int id);

    List<Tarefa> listarTarefas();
}
