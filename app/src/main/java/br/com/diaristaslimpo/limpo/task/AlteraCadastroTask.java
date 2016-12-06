package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 24/04/2016.
 */
public class AlteraCadastroTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private String mensagem;
    private String idDiarista;

    public AlteraCadastroTask(Context context) {
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
                    + context.getResources().getString(R.string.url_alterar_dados);

            new ConectaWS().doPostJsonObject(url, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            mensagem = "";
        } catch (JSONException e) {
            e.printStackTrace();
            mensagem = "";
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean isValido) {
        dialog.dismiss();

        if (isValido) {
            Intent intent = new Intent(context, InicialActivity.class);
            intent.putExtra("idDiarista",idDiarista);
            context.startActivity(intent);
        }else{
            onCancelled();
            MessageBox.show(context, context.getResources().getString(R.string.erro_de_processamento), mensagem);
        }
    }
}