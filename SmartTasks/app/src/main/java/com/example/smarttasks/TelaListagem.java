package com.example.smarttasks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TelaListagem extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public RecyclerView recyclerTasks;
    public List<com.example.smarttasks.Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_listagem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerTasks = findViewById(R.id.listaDeTarefas);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));

        mostrarTarefas();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mostrarTarefas();
    }

    public void mostrarTarefas(){
        try {
            tasks = new ArrayList<>();
            bancoDados = openOrCreateDatabase("agenda", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, titulo, descricao, data FROM tarefa", null);

            if (cursor.moveToFirst()){
                do {
                    int id = cursor.getInt(0);
                    String titulo = cursor.getString(1);
                    String descricao = cursor.getString(2);
                    String data = cursor.getString(3);
                    tasks.add(new Task(id, titulo, descricao, data));
                } while (cursor.moveToNext());
            }
            cursor.close();
            bancoDados.close();

            TaskAdapter adapter = new TaskAdapter(tasks);
            recyclerTasks.setAdapter(adapter);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    public void telaCadastro(View v){
        Intent i = new Intent(this, TelaCadastro.class);
        startActivity(i);
    }

    public void voltarTela(View v){
        Intent i = new Intent(this, TelaListagem.class);
        startActivity(i);
    }
}