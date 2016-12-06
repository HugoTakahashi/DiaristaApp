package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.AsyncJsonObjectResponse;
import br.com.diaristaslimpo.limpo.R;

/**
 * Created by user on 24/04/2016.
 */
public class BuscarServicoTask extends AsyncTask<String, Void, JSONObject> {
    private Context context;
    private ProgressDialog dialog;
    public AsyncJsonObjectResponse delegate = null;

    public BuscarServicoTask(Context context) {
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
    protected JSONObject doInBackground(String... params) {
        String id = params[0];
        String url = context.getResources().getString(R.string.url_prefix)
                + context.getResources().getString(R.string.url_buscar_servico);

        return new ConectaWS().doGetJsonObject(url, id);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        delegate.processFinish(jsonObject);
        dialog.dismiss();
    }
}