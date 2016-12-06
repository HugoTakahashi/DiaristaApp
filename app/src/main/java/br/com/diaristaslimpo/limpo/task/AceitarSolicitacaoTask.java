package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 24/04/2016.
 */
public class AceitarSolicitacaoTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private String idDiarista;
    private String mensagem = "";

    public AceitarSolicitacaoTask(Context context) {
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
        boolean isValido;
        String id = params[0];
        idDiarista = params[1];
        String data = params[2];

        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_solicitacao_aceitar);

        JSONObject json = new JSONObject();
        try {
            json.put("IdSolicitacao", id);
            json.put("IdDiarista", idDiarista);
            json.put("DataDiaria", data);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        try {
            JSONObject response = new ConectaWS().doPostJsonObject(url, String.valueOf(json));
            isValido = response.getBoolean("IsValido");
            mensagem = response.getString("Mensagem");
        } catch (IOException e) {
            e.printStackTrace();
            isValido = false;
        } catch (JSONException e) {
            e.printStackTrace();
            isValido = false;
        }

        return isValido;
    }

    @Override
    protected void onPostExecute(Boolean isValido) {
        dialog.dismiss();

        if(isValido){
            Intent intent = new Intent(context, InicialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("idDiarista",idDiarista);
            context.startActivity(intent);
            Toast.makeText(context,
                    context.getResources().getString(R.string.mensagem_aceitar_solicitacao),
                    Toast.LENGTH_LONG)
                    .show();
        } else {
            onCancelled();
            MessageBox.show(context, "Mensagem", mensagem);
        }
    }
}