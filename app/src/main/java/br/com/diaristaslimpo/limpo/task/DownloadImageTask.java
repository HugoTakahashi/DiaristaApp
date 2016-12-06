package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.diaristaslimpo.limpo.AsyncBitMapResponse;
import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.R;

/**
 * Created by Hugo on 26/11/2016.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private Context context;
    private ImageView imageView;
    private ProgressDialog dialog;

    public DownloadImageTask(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
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
    protected Bitmap doInBackground(String... param) {
        String url = context.getResources().getString(R.string.url_foto) + param[0]
                + context.getResources().getString(R.string.nome_foto);
        Bitmap bitmap = null;

        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result != null) {
            ImageView foto = (ImageView) imageView.findViewById(R.id.fotoDiarista);
            foto.setImageBitmap(result);
        }
        dialog.dismiss();
    }
}