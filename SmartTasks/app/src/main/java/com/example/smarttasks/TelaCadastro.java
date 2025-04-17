package com.example.smarttasks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TelaCadastro extends AppCompatActivity {

EditText titulo, descricao, edtData;
Date data;
SQLiteDatabase bancoDados;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_tela_cadastro);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    });

    titulo = findViewById(R.id.titulo);
    descricao = findViewById(R.id.descricao);
    // Supondo que o componente com id "data" seja um EditText no layout
    edtData = findViewById(R.id.data);

    // Impede que o EditText abra o teclado e configura o clique para abrir o calendário
    edtData.setFocusable(false);
    edtData.setClickable(true);
    edtData.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            abrirCalendario();
        }
    });

    }

private void abrirCalendario() {
    // Obtém a data atual para definir o valor padrão do DatePickerDialog
    final Calendar calendar = Calendar.getInstance();
    int ano = calendar.get(Calendar.YEAR);
    int mes = calendar.get(Calendar.MONTH);
    int dia = calendar.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(
            TelaCadastro.this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                    // Atualiza o calendário com a data escolhida
                    calendar.set(anoSelecionado, mesSelecionado, diaSelecionado);
                    // Define a variável "data" com a data escolhida
                    data = calendar.getTime();

                    // Formata a data para o padrão dd/MM/yyyy
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    edtData.setText(sdf.format(data));
                }
            },
            ano, mes, dia
    );
    datePickerDialog.show();
}

public void cadastrarTarefa(View v){

    String tituloTexto = titulo.getText().toString();
    String descricaoTexto = descricao.getText().toString();
    String dataTexto = edtData.getText().toString();

    if (TextUtils.isEmpty(tituloTexto)) {
        Toast.makeText(this, "O titulo é obrigatório", Toast.LENGTH_SHORT).show();
        return;
    }

    if (TextUtils.isEmpty(descricaoTexto)) {
        Toast.makeText(this, "A descrição é obrigatória", Toast.LENGTH_SHORT).show();
        return;
    }

    if (TextUtils.isEmpty(dataTexto)) {
        Toast.makeText(this, "A data é obrigatória", Toast.LENGTH_SHORT).show();
        return;
    }


    if (!TextUtils.isEmpty(titulo.getText().toString())) {
        try {
            // Formato para comparar apenas a data
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date dataAtual = sdf.parse(sdf.format(new Date()));
            Date dataSelecionada = sdf.parse(dataTexto);

            if (dataSelecionada.before(dataAtual)) {
                Toast.makeText(this, "A data deve ser hoje ou posterior", Toast.LENGTH_SHORT).show();
                return;
            }

            bancoDados = openOrCreateDatabase("agenda", MODE_PRIVATE, null);
            String sql = "INSERT INTO tarefa(titulo, descricao, data) VALUES (?,?,?)";
            SQLiteStatement add = bancoDados.compileStatement(sql);
            add.bindString(1, titulo.getText().toString());
            add.bindString(2, descricao.getText().toString());

            // Formata a data para o padrão desejado antes de gravar no banco
            String dataFormatada = sdf.format(data);
            add.bindString(3, dataFormatada);

            Log.d(TAG, "Inseriu no banco de dados!!! ");
            add.executeInsert();

            finish();
            Intent i = new Intent(this, TelaListagem.class);
            startActivity(i);

        } catch (Exception e) {
            Log.e(TAG, "Erro ao cadastrar tarefa: " + e.getMessage());
        }
    }
}

public void voltarListagem(View v){
    Intent i = new Intent(this, MainActivity.class);
    startActivity(i);
}

}