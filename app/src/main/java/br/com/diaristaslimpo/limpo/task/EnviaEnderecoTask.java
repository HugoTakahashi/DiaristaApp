package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.helper.MessageBox;
import br.com.diaristaslimpo.limpo.activity.SelecionarServicoActivity;

/**
 * Created by user on 24/04/2016.
 */
public class EnviaEnderecoTask extends AsyncTask<String, Void, String> { // linha 22
    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String json;

    public EnviaEnderecoTask(Context context) {

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
    protected String doInBackground(String... params) {// thread em segundaria
        String resp = "oi";
        try {
            json = params[0];
            requester = new ConectaWS();
            final JSONObject recebe = requester.doPostJsonObject("http://limpo-dev.sa-east-1.elasticbeanstalk.com/api/DiaristaEndereco/Cadastrar", json);


                dataBase = new DataBase(context);
                conn = dataBase.getWritableDatabase();
                ScriptSQL scriptSQL = new ScriptSQL(conn);

                scriptSQL.inserirEndereco(
                        String.valueOf(recebe.getInt("IdDiarista")),
                        String.valueOf(recebe.getInt("Id")),
                       // recebe.getString("IdentificacaoEndereco"),
                        recebe.getInt("Cep"),
                        recebe.getString("Logradouro"),
                        recebe.getInt("Numero"),
                        recebe.getString("Complemento"),
                        recebe.getString("Bairro"),
                        recebe.getString("Cidade")
                       // recebe.getString("PontoReferencia")

                );

                resp = null;



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            resp = "Endereço Invalido";
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) { // thread principal

        if (resposta == null) {
            dialog.dismiss();
            Intent it = new Intent(context, SelecionarServicoActivity.class);
            context.startActivity(it);

        } else {
            dialog.dismiss();
            onCancelled();
            MessageBox.show(context, "Erro ao efetuar cadastro do endereco", resposta);

        }


    }


}

