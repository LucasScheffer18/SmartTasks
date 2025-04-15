package com.example.smarttasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTask;

        public TaskViewHolder(View itemView) {
            super(itemView);
            // O TextView "tvTask" é definido no layout do item (item_task.xml)
            tvTask = itemView.findViewById(R.id.tvTask);
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla o layout de cada item do RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        String texto = "Título: " + task.getTitulo() + "\n"
                + "Descrição: " + task.getDescricao() + "\n"
                + "Data: " + task.getData();
        holder.tvTask.setText(texto);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
