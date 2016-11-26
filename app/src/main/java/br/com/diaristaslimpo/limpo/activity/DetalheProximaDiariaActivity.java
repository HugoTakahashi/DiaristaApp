package br.com.diaristaslimpo.limpo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.helper.DetalheProximaDiariaHelper;
import br.com.diaristaslimpo.limpo.task.CancelarDiariaTask;
import br.com.diaristaslimpo.limpo.task.FinalizarSolicitacaoTask;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;

public class DetalheProximaDiariaActivity extends AppCompatActivity {
    private MinhaSolicitacaoTo to;
    private DetalheProximaDiariaHelper helper;
    private String idDiarista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_proxima_diaria);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idDiarista = (String) bundle.get("idDiarista");

        to = (MinhaSolicitacaoTo) bundle.getSerializable("solicitacao");
        helper = new DetalheProximaDiariaHelper(this);
        helper.preenche(to);

        findViewById(R.id.detalhe_proxima_diaria_mapa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itMapa = new Intent(Intent.ACTION_VIEW);
                itMapa.setData(Uri.parse("geo:0:0?q="+ to.getEndereco()));
                startActivity(itMapa);
            }
        });

        findViewById(R.id.detalhe_proxima_diaria_terminei).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinalizarSolicitacaoTask(DetalheProximaDiariaActivity.this)
                        .execute(Integer.toString(to.getIdSolicitacao()),idDiarista, to.getNomeCliente(), String.valueOf(to.getIdCliente()));
            }
        });

        findViewById(R.id.detalhe_proxima_diaria_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DetalheProximaDiariaActivity.this).create();
                dialog.setTitle("Atenção");
                dialog.setIcon(android.R.drawable.stat_notify_error);
                dialog.setMessage("Tem certeza que quer cancelar a diária?");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new CancelarDiariaTask(DetalheProximaDiariaActivity.this)
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