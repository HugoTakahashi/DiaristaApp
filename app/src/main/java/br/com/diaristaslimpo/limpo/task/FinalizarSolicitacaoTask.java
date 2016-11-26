package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.AvaliacaoActivity;

/**
 * Created by user on 25/11/2016.
 */
public class FinalizarSolicitacaoTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog dialog;
    private String idDiarista;
    private String NomeCliente;
    private String idSolicitacao;
    private String idCliente;

    public FinalizarSolicitacaoTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                context.getResources().getString(R.string.aguarde),
                context.getResources().getString(R.string.em_processamento),
                true,
                true);
    }

    @Override
    protected String doInBackground(String... params) {
        idSolicitacao = params[0];
        idDiarista = params[1];
        NomeCliente = params[2];
        idCliente = params[3];
        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_solicitacao_finalizar);

        new ConectaWS().doGet(url, idSolicitacao);
        return "OK";
    }

    @Override
    protected void onPostExecute(String response) {
        dialog.dismiss();
        Intent intent = new Intent(context, AvaliacaoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("idCliente", idCliente);
        intent.putExtra("idDiarista", idDiarista);
        intent.putExtra("NomeCliente", NomeCliente);
        intent.putExtra("idSolicitacao", idSolicitacao);
        context.startActivity(intent);
        Toast.makeText(context,
                context.getResources().getString(R.string.mensagem_aceitar_solicitacao),
                Toast.LENGTH_LONG)
                .show();
    }
}
