package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.EnviaServicosTask;
import br.com.diaristaslimpo.limpo.to.CadastroCompletoDiaristaTo;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;
import br.com.diaristaslimpo.limpo.to.SelecionarServicoTo;

public class SelecionarServicoActivity extends AppCompatActivity {
    private ImageButton btLimpeza, btPassaRoupa, btLavarRoupa;
    private int ativo = Color.parseColor("#c60b2d"), inativo = Color.parseColor("#DCDCDC");
    private boolean limpeza,passarroupa,lavarroupa;
    private CadastroCompletoDiaristaTo to;
    private SelecionarServicoTo selecionarServicoTo = new SelecionarServicoTo();

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

        btLimpeza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (limpeza == false) {
                    limpeza = true;
                    btLimpeza.setBackgroundColor(ativo);
                } else {
                    limpeza = false;
                    btLimpeza.setBackgroundColor(inativo);
                }
                selecionarServicoTo.setLimpeza(limpeza);
            }
        });

        btPassaRoupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passarroupa == false) {
                    passarroupa= true;
                    btPassaRoupa.setBackgroundColor(ativo);
                } else {
                    passarroupa = false;
                    btPassaRoupa.setBackgroundColor(inativo);
                }
                selecionarServicoTo.setPassaRoupa(passarroupa);
            }
        });

        btLavarRoupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lavarroupa == false) {
                    lavarroupa = true;
                    btLavarRoupa.setBackgroundColor(ativo);
                } else {
                    lavarroupa = false;
                    btLavarRoupa.setBackgroundColor(inativo);
                }
                selecionarServicoTo.setLavaRoupa(lavarroupa);
            }
        });

        findViewById(R.id.btData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to.setSelecionarServicoTo(selecionarServicoTo);

                Intent intent = new Intent(SelecionarServicoActivity.this, CadastroEnderecoActivity.class);
                intent.putExtra("cadastroCompletoDiaristaTo", to);
                startActivity(intent);
            }
        });
    }
}