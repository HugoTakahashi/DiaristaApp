package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.R;

/**
 * Created by user on 24/04/2016.
 */
public class MinhasSolicitacoesTask extends AsyncTask<String, Void, JSONArray> {
    private Context context;
    private ProgressDialog dialog;
    public AsyncResponse delegate = null;

    public MinhasSolicitacoesTask(Context context) {
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
    protected JSONArray doInBackground(String... params) {
        String id = params[0];
        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_solicitacao_lista_por_diarista_e_pendente_aceitacao);

        return new ConectaWS().doGetJsonArray(url, id);
    }

    @Override
    protected void onPostExecute(JSONArray resposta) {
        delegate.processFinish(resposta);
        dialog.dismiss();
    }
}