package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.helper.MessageBox;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;

/**
 * Created by user on 24/04/2016.
 */
public class LoginTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester = new ConectaWS();
    private String url, json;
    private String idDiarista;
    private String nota;

    public LoginTask(Context context) {
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
        String resp = null;
        try {
            json = params[0];
            url = context.getResources().getString(R.string.url_prefix) +
                    context.getResources().getString(R.string.url_login_diarista);
            JSONObject response = requester.doPostJsonObject(url, json);

            idDiarista = String.valueOf(response.getInt("Id"));
           // nota = response.getString("Nota");

        } catch (IOException e) {
            resp = context.getResources().getString(R.string.erro_login_ou_senha_invalida);
            e.printStackTrace();
        } catch (JSONException e) {
            resp = context.getResources().getString(R.string.erro_login_ou_senha_invalida);
            e.printStackTrace();
        }

        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();

        if (resposta == null) {
            Intent intent = new Intent(context, InicialActivity.class);
            intent.putExtra("idDiarista",idDiarista);
            intent.putExtra("nota",nota);
            context.startActivity(intent);
        } else {
            onCancelled();
            MessageBox.show(context,
                    context.getResources().getString(R.string.erro_de_processamento),
                    resposta);
        }
    }
}