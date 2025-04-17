package com.example.smarttasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class TelaAnalise extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_analise);

        recyclerView = findViewById(R.id.recyclerUrgencia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //tasks = UtilsBanco.carregarTarefas(this);

        Collections.sort(tasks, new TaskUrgenciaComparator());

        adapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(adapter);
    }

    public void voltarListagem(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
