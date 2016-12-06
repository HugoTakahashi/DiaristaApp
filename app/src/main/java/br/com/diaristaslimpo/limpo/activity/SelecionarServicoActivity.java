package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.AsyncJsonObjectResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.AlteraEnderecoTask;
import br.com.diaristaslimpo.limpo.task.AlteraServicoTask;
import br.com.diaristaslimpo.limpo.task.BuscarEnderecoTask;
import br.com.diaristaslimpo.limpo.task.BuscarServicoTask;
import br.com.diaristaslimpo.limpo.task.EnviaServicosTask;
import br.com.diaristaslimpo.limpo.to.CadastroCompletoDiaristaTo;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;
import br.com.diaristaslimpo.limpo.to.SelecionarServicoTo;

public class SelecionarServicoActivity extends AppCompatActivity implements AsyncJsonObjectResponse {
    private ImageButton btLimpeza, btPassaRoupa, btLavarRoupa;
    private int ativo = Color.parseColor("#c60b2d"), inativo = Color.parseColor("#DCDCDC");
    private boolean limpeza,passarroupa,lavarroupa;
    private CadastroCompletoDiaristaTo to;
    private SelecionarServicoTo selecionarServicoTo = new SelecionarServicoTo();
    private BuscarServicoTask asyncTask = new BuscarServicoTask(this);
    private String idDiarista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_servico);

        btLimpeza = (ImageButton) findViewById(R.id.bt_limpeza);
        btPassaRoupa = (ImageButton) findViewById(R.id.bt_passarroupa);
        btLavarRoupa = (ImageButton) findViewById(R.id.bt_lavarroupa);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        to = (CadastroCompletoDiaristaTo) bundle.getSerializable("cadastroCompletoDiaristaTo");
        idDiarista = bundle.getString("idDiarista");
        if (!TextUtils.isEmpty(idDiarista)){
            asyncTask.delegate = this;
            asyncTask.execute(String.valueOf(idDiarista));
        }

        btLimpeza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustaLimpeza();
            }
        });

        btPassaRoupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustaPassaRoupa();
            }
        });

        btLavarRoupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustaLavaRoupa();
            }
        });

        findViewById(R.id.btData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(idDiarista)){
                    selecionarServicoTo.setIdDiarista(Integer.parseInt(idDiarista));
                    new AlteraServicoTask(SelecionarServicoActivity.this)
                            .execute(selecionarServicoTo.toString(), idDiarista);
                } else{
                    to.setSelecionarServicoTo(selecionarServicoTo);
                    Intent intent = new Intent(SelecionarServicoActivity.this, CadastroEnderecoActivity.class);
                    intent.putExtra("cadastroCompletoDiaristaTo", to);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void processFinish(JSONObject json) {
        try {
            limpeza = (boolean) json.get("Limpeza");
            passarroupa = (boolean) json.get("PassarRoupa");
            lavarroupa = (boolean) json.get("LavarRoupa");
        } catch(JSONException e){
        }
        selecionarServicoTo = new SelecionarServicoTo();

        if (lavarroupa == true) {
            btLavarRoupa.setBackgroundColor(ativo);
        } else {
            btLavarRoupa.setBackgroundColor(inativo);
        }
        selecionarServicoTo.setLavaRoupa(lavarroupa);

        if (limpeza == true) {
            btLimpeza.setBackgroundColor(ativo);
        } else {
            btLimpeza.setBackgroundColor(inativo);
        }
        selecionarServicoTo.setLimpeza(limpeza);

        if (passarroupa == true) {
            btPassaRoupa.setBackgroundColor(ativo);
        } else {
            btPassaRoupa.setBackgroundColor(inativo);
        }
        selecionarServicoTo.setPassaRoupa(passarroupa);
    }

    private void ajustaLimpeza(){
        if (limpeza == false) {
            limpeza = true;
            btLimpeza.setBackgroundColor(ativo);
        } else {
            limpeza = false;
            btLimpeza.setBackgroundColor(inativo);
        }
        selecionarServicoTo.setLimpeza(limpeza);
    }

    private void ajustaPassaRoupa(){
        if (passarroupa == false) {
            passarroupa= true;
            btPassaRoupa.setBackgroundColor(ativo);
        } else {
            passarroupa = false;
            btPassaRoupa.setBackgroundColor(inativo);
        }
        selecionarServicoTo.setPassaRoupa(passarroupa);
    }

    private void ajustaLavaRoupa(){
        if (lavarroupa == false) {
            lavarroupa = true;
            btLavarRoupa.setBackgroundColor(ativo);
        } else {
            lavarroupa = false;
            btLavarRoupa.setBackgroundColor(inativo);
        }
        selecionarServicoTo.setLavaRoupa(lavarroupa);
    }
}