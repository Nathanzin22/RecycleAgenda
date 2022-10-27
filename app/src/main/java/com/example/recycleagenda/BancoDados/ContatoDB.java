package com.example.recycleagenda.BancoDados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recycleagenda.Entidades.Contato;

import java.util.List;

public class ContatoDB {

    private DBHelper db;
    private SQLiteDatabase conexao;

    public ContatoDB(DBHelper db) {
        this.db = db;
    }

    public void inserirContato(Contato contato) {
        conexao = db.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", contato.getNome());
        valores.put("telefone", contato.getTelefone());

        conexao.insertOrThrow("Agenda", null, valores);

        conexao.close();
    }

    public void editarContato(Contato contato) {
        conexao = db.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", contato.getNome());
        valores.put("telefone", contato.getTelefone());

        conexao.update("Agenda", valores, "id=?", new String[]{contato.getId().toString()});

        conexao.close();
    }

    public void remover(int id) {
        conexao = db.getWritableDatabase();

        conexao.delete("Agenda", "id=?", new String[]{id + ""});

        conexao.close();
    }

    public void listar(List listaDados) {
        listaDados.clear();
        conexao = db.getReadableDatabase();

        String colunas[] = {"id", "nome", "telefone"};
        Cursor query = conexao.query("Agenda", colunas, null, null, null, null, "nome");

        while (query.moveToNext()) {
            Contato contato = new Contato();

            contato.setId(Integer.parseInt(query.getString(0)));
            contato.setNome(query.getString(1));
            contato.setTelefone(query.getString(2));

            listaDados.add(contato);
        }

        conexao.close();
    }

}
