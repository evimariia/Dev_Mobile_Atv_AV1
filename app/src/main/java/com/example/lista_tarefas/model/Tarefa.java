package com.example.lista_tarefas.model;

public class Tarefa {
    private String tarefa;
    private int id,status;

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }
}
