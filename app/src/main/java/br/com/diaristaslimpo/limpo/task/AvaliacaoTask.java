package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.helper.GeraJson;
import br.com.diaristaslimpo.limpo.helper.MessageBox;

/**
 * Created by user on 24/04/2016.
 */
public class AvaliacaoTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester = new ConectaWS();
    private String url, json;
    private String idDiarista;

    public AvaliacaoTask(Context context) {
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
        idDiarista = params[4];
        GeraJson geraJson = new GeraJson();
        String json = geraJson.jsonEnviaAvaliacao(params[0],params[1],params[2],params[3]);
        try {
            url = context.getResources().getString(R.string.url_prefix) +
                    context.getResources().getString(R.string.url_avalicao);
            requester.doPost(url, json);


        } catch (IOException e) {

            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return "ok" ;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();

        if (resposta == "ok") {
            Intent intent = new Intent(context, InicialActivity.class);
            intent.putExtra("idDiarista",idDiarista);
            context.startActivity(intent);
        } else {
            onCancelled();
            MessageBox.show(context,
                    context.getResources().getString(R.string.erro_de_processamento),
                    resposta);
        }
    }
}