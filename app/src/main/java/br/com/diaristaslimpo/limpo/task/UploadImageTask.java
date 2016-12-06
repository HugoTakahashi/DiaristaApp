package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import br.com.diaristaslimpo.limpo.R;

/**
 * Created by Hugo on 26/11/2016.
 */

public class UploadImageTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;

    public UploadImageTask(Context context) {
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
    protected Boolean doInBackground(String... param) {
        String idDiarista = param[0];
        String imagem = param[1];
        String url = context.getResources().getString(R.string.url_prefix) +
            context.getResources().getString(R.string.url_upload_foto);

        String imagemAntiga = imagem;
        String imagemNova = imagemAntiga.replaceAll(System.getProperty("line.separator"), "");

        try {

            JSONObject json = new JSONObject();
            json.put("Id", idDiarista);
            json.put("Cliente", false);
            json.put("Foto", true);
            json.put("Imagem", imagemNova);

            String jsonFormatado = String.valueOf(json).replaceAll(System.getProperty("line.separator"), "").replace("\\", "");

            new ConectaWS().doPost(url, jsonFormatado);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        dialog.dismiss();
    }
}