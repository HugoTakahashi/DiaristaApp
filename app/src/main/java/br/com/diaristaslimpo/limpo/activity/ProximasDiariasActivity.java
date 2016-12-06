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

import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.adapter.ProximasDiariasAdapter;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.ProximasDiariasTask;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;

public class ProximasDiariasActivity extends AppCompatActivity implements AsyncResponse {
    private ListView listView;
    private ProximasDiariasAdapter listViewAdapter;
    private ArrayList<MinhaSolicitacaoTo> items;
    private ProximasDiariasTask asyncTask  = new ProximasDiariasTask(this);
    private String idDiarista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximas_diarias);

        listView = (ListView) findViewById(R.id.listview_proximas_diarias);
        items = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProximasDiariasActivity.this,DetalheProximaDiariaActivity.class);
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

        listViewAdapter = new ProximasDiariasAdapter(this, items);
        listView.setAdapter(listViewAdapter);
    }
}
