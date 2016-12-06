package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.OrcamentoTo;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.adapter.AdapterOrcamento;
import br.com.diaristaslimpo.limpo.task.RecebeHistoricoTask;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;

public class HistoricoActivity extends AppCompatActivity {

    private ListView lstOrcamento;
    private ArrayList<OrcamentoTo> itens;
    private AdapterOrcamento adapterListView;
    private DataBase dataBase;
    private SQLiteDatabase conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_historico);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);

        lstOrcamento = (ListView) findViewById(R.id.lstOrcamento);
        itens = new ArrayList<>();
        String json = String.valueOf(scriptSQL.retornaIdDiarista());
        new RecebeHistoricoTask(this).execute(json);


        ArrayList<OrcamentoTo> obj = scriptSQL.selectListaHistorico();
        adapterListView = new AdapterOrcamento(this, obj);
        lstOrcamento.setAdapter(adapterListView);


        lstOrcamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent it = new Intent(HistoricoActivity.this,DetalheHistoricoActivity.class);
                startActivity(it);

            }

        });

    }
    }