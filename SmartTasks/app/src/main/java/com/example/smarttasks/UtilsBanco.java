package com.example.smarttasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UtilsBanco {
    public static List<Task> carregarTarefas(Context context) {
        List<Task> lista = new ArrayList<>();
        SQLiteDatabase bancoDados = context.openOrCreateDatabase("agenda", Context.MODE_PRIVATE, null);

        Cursor cursor = bancoDados.rawQuery("SELECT id, titulo, descricao, data FROM tarefa", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String descricao = cursor.getString(2);
                String data = cursor.getString(3);
                lista.add(new Task(id, titulo, descricao, data));
            } while (cursor.moveToNext());
        }

        cursor.close();
        bancoDados.close();
        return lista;
    }
}
