package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;

/**
 * Created by user on 24/04/2016.
 */
public class RecebeHistoricoTask extends AsyncTask<String, Void, String> { // linha 22
    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester;
    private DataBase dataBase;
    private SQLiteDatabase conn;


    public RecebeHistoricoTask(Context context) {

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
            String json = params[0];

            requester = new ConectaWS();

            final JSONArray recebe = requester.doPostJsonArray("http://limpo-dev.sa-east-1.elasticbeanstalk.com/api/Cliente/Cadastrar", json);

            dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();

            ScriptSQL scriptSQL = new ScriptSQL(conn);
            for (int i=0;i<recebe.length();i++) {
                JSONObject json2 = recebe.getJSONObject(i);
                scriptSQL.insertHistorico(
                        json2.getString("IdSolicitacao"),
                        json2.getString("Nome"),
                        json2.getDouble("Valor"),
                        json2.getString("Status"),
                        json2.getString("DataSolicitacao")

                );
            }

            resp = null;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            resp = "Dados invalidos ou já cadastrados";
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) { // thread principal

        if (resposta == null) {
            dialog.dismiss();



        }else{
            dialog.dismiss();
            onCancelled();
            MessageBox.show(context,"Erro ao efetuar Alteração dos Dados",resposta);

        }





    }


}

