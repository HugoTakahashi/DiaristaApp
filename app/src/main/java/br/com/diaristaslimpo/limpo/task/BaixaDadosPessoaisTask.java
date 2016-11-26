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
import br.com.diaristaslimpo.limpo.helper.GeraJson;
import br.com.diaristaslimpo.limpo.helper.MessageBox;

/**
 * Created by user on 25/11/2016.
 */
public class BaixaDadosPessoaisTask extends AsyncTask<String, Void, String> {

private Context context;
private ProgressDialog dialog;
private ConectaWS requester = new ConectaWS();
private String url, json;
private String idDiarista;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String DataNascimento;
    private String Email;
    private String Celular;

    public BaixaDadosPessoaisTask(Context context) {
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
        GeraJson geraJson = new GeraJson();
        json = geraJson.jsonId(params[0]);
        try {
        url = context.getResources().getString(R.string.url_prefix) +
        context.getResources().getString(R.string.url_DadosPessoais);
        JSONObject response = requester.doPostJsonObject(url, json);

        idDiarista = String.valueOf(response.getInt("Id"));
            nome = response.getString("Nome");
            sobrenome = response.getString("Sobrenome");
            cpf = response.getString("CPF");
            DataNascimento = response.getString("DataNascimento");
            Email = response.getString("Email");
            Celular = response.getString("Ceular");


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
            intent.putExtra("nome",nome);
            intent.putExtra("sobrenome",sobrenome);
            intent.putExtra("cpf",cpf);
            intent.putExtra("DataNascimento",DataNascimento);
            intent.putExtra("Email",Email);
            intent.putExtra("Celular",Celular);
        context.startActivity(intent);
        } else {
        onCancelled();
        MessageBox.show(context,
        context.getResources().getString(R.string.erro_de_processamento),
        resposta);
        }
        }
}