package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.activity.LoginActivity;

/**
 * Created by user on 24/04/2016.
 */
public class DesativarContaTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private String mensagemRetorno = "";
    private String idDiarista;

    public DesativarContaTask(Context context) {
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
    protected Boolean doInBackground(String... params) {
        idDiarista = params[0];
        Boolean isValido = false;

        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_diarista_desativar);

        JSONObject json = new ConectaWS().doGetJsonObject(url, idDiarista);

        try {
            isValido = (Boolean) json.get("IsValido");
            mensagemRetorno = (String) json.get("Mensagem");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isValido;
    }

    @Override
    protected void onPostExecute(Boolean sucesso) {
        dialog.dismiss();
        if (sucesso) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        } else {
            onCancelled();
            MessageBox.show(context,"Erro - Desativar",mensagemRetorno);
        }
    }
}