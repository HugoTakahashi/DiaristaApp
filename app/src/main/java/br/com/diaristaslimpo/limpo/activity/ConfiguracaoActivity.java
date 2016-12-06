package br.com.diaristaslimpo.limpo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.DesativarContaTask;

/**
 * Created by Hugo on 29/11/2016.
 */

public class ConfiguracaoActivity extends AppCompatActivity {
    private Button btDesativar;
    private String idDiarista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDiarista = (String) bundle.get("idDiarista");

        btDesativar = (Button) findViewById(R.id.bt_desativar);
        btDesativar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ConfiguracaoActivity.this).create();
                dialog.setTitle("Deseja desativar sua conta ?");
                dialog.setMessage("Escolha sim para desativar ou não para Cancelar");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DesativarContaTask(ConfiguracaoActivity.this).execute(idDiarista);
                    }

                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                dialog.show();

            }
        });
    }
}
