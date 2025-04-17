package com.example.smarttasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private Context context;
    private boolean mostrarPrioridade;
    public TaskAdapter(List<Task> tasks, boolean mostrarPrioridade) {
        this.tasks = tasks;
        this.mostrarPrioridade = mostrarPrioridade;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTask;
        public Button btnVer;

        public TaskViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            btnVer = itemView.findViewById(R.id.btnDetalhes);
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext(); // pega o contexto pra usar depois
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.tvTask.setText(task.getTitulo());

        View barra = holder.itemView.findViewById(R.id.barraRisco);

        if (mostrarPrioridade) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date dataAtual = new Date();
                Date dataTarefa = sdf.parse(task.getData());

                long diferenca = dataTarefa.getTime() - dataAtual.getTime();
                long dias = TimeUnit.MILLISECONDS.toDays(diferenca);

                if (dias <= 1) {
                    barra.setBackgroundColor(Color.RED);
                } else if (dias <= 3) {
                    barra.setBackgroundColor(Color.YELLOW);
                } else {
                    barra.setBackgroundColor(Color.GREEN);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            barra.setVisibility(View.GONE);
        }

        holder.btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(context, TelaDetalhes.class);
            intent.putExtra("task", task);

            // Define a origem com base em mostrarPrioridade
            if (mostrarPrioridade) {
                intent.putExtra("origem", "TelaAnalise"); // exemplo de nome, pode ser qualquer string que você queira
            } else {
                intent.putExtra("origem", "TelaListagem"); // ou "MainActivity", depende da sua tela principal
            }

            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }
}