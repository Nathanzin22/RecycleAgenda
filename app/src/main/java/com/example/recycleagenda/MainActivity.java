package com.example.recycleagenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recycleagenda.Agenda.Adapter;
import com.example.recycleagenda.Entidades.Contato;
import com.example.recycleagenda.BancoDados.ContatoDB;
import com.example.recycleagenda.BancoDados.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText campoNome;
    EditText campoTelefone;
    Button botaoSalvar;
    Button botaoCancelar;

    List<Contato> dadosContatos;
    RecyclerView listagemContatos;
    Adapter adapter;

    ContatoDB contatoDB;

    Contato contato;

    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);

        DBHelper dbHelper = new DBHelper(MainActivity.this);
        contatoDB = new ContatoDB(dbHelper);

        campoNome = findViewById(R.id.campoNome);
        campoTelefone = findViewById(R.id.campoTelefone);
        botaoSalvar = findViewById(R.id.botaoSalvar);
        botaoCancelar = findViewById(R.id.botaoCancelar);
        listagemContatos = findViewById(R.id.listagemContatos);

        dadosContatos = new ArrayList<>();
        contatoDB.listar(dadosContatos);
        adapter = new Adapter(dadosContatos);
        listagemContatos.setLayoutManager(linearLayoutManager);
        listagemContatos.setAdapter(adapter);

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contato = new Contato();

                campoNome.setText("");
                campoTelefone.setText("");

            }
        });
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (campoNome.getText().toString().isEmpty() || campoTelefone.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Contato Inválido!", Toast.LENGTH_SHORT).show();
                } else {

                    contato = new Contato();

                    contato.setNome(campoNome.getText().toString());
                    contato.setTelefone(campoTelefone.getText().toString());
                    contatoDB.inserirContato(contato);

                    //Atualizar lista.
                    contatoDB.listar(dadosContatos);
                    adapter.notifyDataSetChanged();

                    //Resetar os campos.
                    contato = null;
                    campoNome.setText("");
                    campoTelefone.setText("");

                    Toast.makeText(MainActivity.this, "Salvado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}