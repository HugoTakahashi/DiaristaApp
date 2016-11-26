package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.task.MinhasSolicitacoesTask;
import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;
import br.com.diaristaslimpo.limpo.adapter.MinhasSolicitacoesAdapter;
import br.com.diaristaslimpo.limpo.R;

public class MinhasSolicitacoesActivity extends AppCompatActivity implements AsyncResponse {
    private ListView listView;
    private ArrayList<MinhaSolicitacaoTo> items;
    private MinhasSolicitacoesAdapter listViewAdapter;
    private MinhasSolicitacoesTask asyncTask  = new MinhasSolicitacoesTask(this);
    private String idDiarista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_solicitacoes);

        listView = (ListView) findViewById(R.id.lstDiarista);
        items = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MinhasSolicitacoesActivity.this,DetalheSolicitacaoActivity.class);
                intent.putExtra("solicitacao", items.get(position));
                intent.putExtra("idDiarista",idDiarista);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDiarista = (String) bundle.get("idDiarista");

        asyncTask.delegate = this;
        asyncTask.execute(String.valueOf(idDiarista));
    }

    @Override
    public void processFinish(JSONArray jsonArray){
        try {
            for(int i=0;i < jsonArray.length();i++) {
                MinhaSolicitacaoTo to = new MinhaSolicitacaoTo();
                JSONObject json = jsonArray.getJSONObject(i);
                to.jsonParaTo(to, json);
                items.add(to);
            }
        } catch(JSONException e){
        }

        listViewAdapter = new MinhasSolicitacoesAdapter(this, items);
        listView.setAdapter(listViewAdapter);
    }
}