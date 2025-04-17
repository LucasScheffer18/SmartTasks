package com.example.smarttasks;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private int id;
    private String titulo;
    private String descricao;
    private String data;
    private String prioridade;

    public Task(int id, String titulo, String descricao, String data) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        descricao = in.readString();
        data = in.readString();
        prioridade = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public int getId() { return id; }
    public String getTitulo() {
        return titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getData() {
        return data;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(prioridade);
    }
}
