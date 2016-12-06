package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;

/**
 * Created by user on 24/04/2016.
 */
public class AlteraServicoTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private String idDiarista;

    public AlteraServicoTask(Context context) {
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
        try {
            String json = params[0];
            idDiarista = params[1];

            String url = context.getResources().getString(R.string.url_prefix)
                    + context.getResources().getString(R.string.url_alterar_servico);
            new ConectaWS().doPostJsonObject(url, json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean resposta) {
        dialog.dismiss();

        if(resposta == true) {
            Intent intent = new Intent(context, InicialActivity.class);
            intent.putExtra("idDiarista",idDiarista);
            context.startActivity(intent);
        }
    }
}