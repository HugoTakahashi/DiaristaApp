package br.com.diaristaslimpo.limpo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.helper.DetalheSolicitacaoHelper;
import br.com.diaristaslimpo.limpo.task.AceitarSolicitacaoTask;
import br.com.diaristaslimpo.limpo.task.RecusarSolicitacaoTask;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;

public class DetalheSolicitacaoActivity extends AppCompatActivity {
    private MinhaSolicitacaoTo to;
    private DetalheSolicitacaoHelper helper;
    private String idDiarista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_solicitacao);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idDiarista = (String) bundle.get("idDiarista");
        to = (MinhaSolicitacaoTo) bundle.getSerializable("solicitacao");
        helper = new DetalheSolicitacaoHelper(this);
        helper.preenche(to);

        findViewById(R.id.detalhe_solicitacao_mapa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itMapa = new Intent(Intent.ACTION_VIEW);
                itMapa.setData(Uri.parse("geo:0:0?q="+ to.getEndereco()));
                startActivity(itMapa);
            }
        });

        findViewById(R.id.detalhe_solicitacao_aceitar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DetalheSolicitacaoActivity.this).create();
                dialog.setTitle("Atenção");
                dialog.setIcon(android.R.drawable.ic_menu_save);
                dialog.setMessage("Deseja realmente aceitar esta diária?");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AceitarSolicitacaoTask(DetalheSolicitacaoActivity.this)
                                .execute(Integer.toString(to.getIdSolicitacao()), idDiarista, to.getDataDiariaJson());
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.nao), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });

        findViewById(R.id.detalhe_solicitacao_recusar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DetalheSolicitacaoActivity.this).create();
                dialog.setTitle("Atenção");
                dialog.setIcon(android.R.drawable.ic_menu_delete);
                dialog.setMessage("Deseja realmente recusar esta diária?");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new RecusarSolicitacaoTask(DetalheSolicitacaoActivity.this)
                                .execute(Integer.toString(to.getIdSolicitacao()), idDiarista);
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.nao), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });
    }
}