package com.example.smarttasks;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        criaBancoDados();

    }

    public void criaBancoDados(){
        bancoDados = openOrCreateDatabase("agenda", MODE_PRIVATE,null);
        try {

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefa(" + "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ", titulo VARCHAR" +
                    ", descricao VARCHAR" +
                    ",data DATE)");
            bancoDados.close();
        } catch (Exception e) {
        }
    }

    public void telaCadastrar(View v){
        Intent i = new Intent(this, TelaCadastro.class);
        startActivity(i);
    }

    public void telaListagem(View v){
        Intent a = new Intent(this, TelaListagem.class);
        startActivity(a);
    }

    public void telaCompartilha(View v){
        String url = "https://presencial.ifrs.edu.br/";
        Intent b = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(b);
    }

    public void telaAnalise(View v){
        Intent c = new Intent(this, TelaAnalise.class);
        startActivity(c);
    }

    public void telaSobre(View v){
        Intent d = new Intent(this, TelaSobre.class);
        startActivity(d);
    }
}