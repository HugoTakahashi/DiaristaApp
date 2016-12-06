package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.AvaliacaoTask;

public class AvaliacaoActivity extends AppCompatActivity {

    RatingBar nota;
    EditText obs;
    TextView nomeCliente;
    private String idSolicitacao;
    private String idCliente, idDiarista;
    private int notaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_avalicao);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idSolicitacao = (String) bundle.get("idSolicitacao");
        idCliente = (String)bundle.get("idCliente");
        idDiarista = (String)bundle.get("idDiarista");

        nota = (RatingBar) findViewById(R.id.ratingBar2);
        obs = (EditText) findViewById(R.id.avalicao_obs);
        nomeCliente = (TextView) findViewById(R.id.textView18);
        nomeCliente.setText(bundle.getString("NomeCliente"));

        findViewById(R.id.bt_avaliar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarPreenchimentoObrigatorio()) {
                    notaint = (int) nota.getRating();
                    new AvaliacaoTask(AvaliacaoActivity.this).execute(String.valueOf(notaint), obs.getText().toString(),
                            idSolicitacao, idCliente, idDiarista);
                }
            }
        });
    }

    private boolean validarPreenchimentoObrigatorio(){
        if(obs.getText().toString().matches("")){
            obs.setError("Campo obrigatório");
            return false;
        }

        return true;
    }
}