package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import br.com.diaristaslimpo.limpo.R;

/**
 * Created by Hugo on 26/11/2016.
 */

public class CalcularNotaDiaristaTask extends AsyncTask<String, Void, String> {
    private Context context;
    private TextView textView;
    private ProgressDialog dialog;

    public CalcularNotaDiaristaTask(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
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
    protected String doInBackground(String... param) {

        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_buscar_nota);

        JSONObject json = new ConectaWS().doGetJsonObject(url, param[0]);
        try {
            return json.getString("Nota");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "0";
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
        dialog.dismiss();
    }
}