package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.to.CadastroCompletoDiaristaTo;

/**
 * Created by user on 24/04/2016.
 */
public class CadastrarDiaristaTask extends AsyncTask<CadastroCompletoDiaristaTo, Void, String> {
    private Context context;
    private ProgressDialog dialog;
    private int idDiarista;

    public CadastrarDiaristaTask(Context context) {
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
    protected String doInBackground(CadastroCompletoDiaristaTo... params) {
        String resp = null;

        try {
            CadastroCompletoDiaristaTo cadastroCompletoDiaristaTo = params[0];
            String urlCadastraDiarista = context.getResources().getString(R.string.url_prefix) +
                    context.getResources().getString(R.string.url_cadastrar_diarista);

            String urlCadastraServicoRealizado = context.getResources().getString(R.string.url_prefix) +
                    context.getResources().getString(R.string.url_cadastrar_servico);

            String urlCadastraEndereco = context.getResources().getString(R.string.url_prefix) +
                    context.getResources().getString(R.string.url_cadastrar_endereco);

            String jsonDiarista = cadastroCompletoDiaristaTo.getFormularioDiaristaTo().toString();

            JSONObject diaristaResponse = new ConectaWS().doPostJsonObject(urlCadastraDiarista, jsonDiarista);

            idDiarista = diaristaResponse.getInt("Id");
            cadastroCompletoDiaristaTo.getSelecionarServicoTo().setIdDiarista(idDiarista);
            cadastroCompletoDiaristaTo.getEnderecoTo().setIdDiarista(idDiarista);

            String jsonServico = cadastroCompletoDiaristaTo.getSelecionarServicoTo().toString();
            String jsonEndereco = cadastroCompletoDiaristaTo.getEnderecoTo().toString();

            new ConectaWS().doPostJsonObject(urlCadastraServicoRealizado, jsonServico);
            new ConectaWS().doPostJsonObject(urlCadastraEndereco, jsonEndereco);

        } catch (IOException e) {
            resp = "Erro";
            e.printStackTrace();
        } catch (JSONException e) {
            resp = "Erro";
            e.printStackTrace();
        }

        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();

        if (resposta == null) {
            Intent intent = new Intent(context, InicialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("idDiarista", String.valueOf(idDiarista));
            context.startActivity(intent);
        } else {
            onCancelled();
            MessageBox.show(context,
                    context.getResources().getString(R.string.erro_de_processamento),
                    resposta);
        }
    }
}