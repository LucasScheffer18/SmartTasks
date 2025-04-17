package com.example.smarttasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private Context context;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
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

        String texto = task.getTitulo();

        holder.tvTask.setText(texto);

        holder.btnVer.setOnClickListener(v -> {
            // Enviando o objeto Task inteiro para a TelaDetalhes
            Intent intent = new Intent(context, TelaDetalhes.class);
            intent.putExtra("task", task); // Envia o objeto Task via Parcelable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}