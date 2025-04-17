package com.example.smarttasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TelaDetalhes extends AppCompatActivity {

    private TextView tvTitulo, tvDescricao, tvData, tvPrioridade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_detalhes);

        String origem = getIntent().getStringExtra("origem");

        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescricao = findViewById(R.id.tvDescricao);
        tvData = findViewById(R.id.tvData);
        tvPrioridade = findViewById(R.id.tvPrioridade);

        // Recebendo o objeto Task via Parcelable
        Task task = getIntent().getParcelableExtra("task");

        if (task != null) {
            tvTitulo.setText("Título: " + task.getTitulo());
            tvDescricao.setText("Descrição: " + task.getDescricao());
            tvData.setText("Data: " + task.getData());

            String prioridade = calcularPrioridade(task.getData());
            tvPrioridade.setText("Prioridade: " + prioridade);
        }
    }

    public void voltarTela(View v) {
        String origem = getIntent().getStringExtra("origem");
        Intent i;
        if ("TelaAnalise".equals(origem)) {
            i = new Intent(this, TelaAnalise.class);
        } else {
            i = new Intent(this, TelaListagem.class);
        }
        startActivity(i);
    }


    private String calcularPrioridade(String dataStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date dataTarefa = sdf.parse(dataStr);
            Date hoje = new Date();

            long diffMillis = dataTarefa.getTime() - hoje.getTime();
            long diffDias = diffMillis / (1000 * 60 * 60 * 24);

            if (diffDias <= 0) {
                return "Alta";
            } else if (diffDias <= 3) {
                return "Média";
            } else {
                return "Baixa";
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "Desconhecida";
        }
    }
}
