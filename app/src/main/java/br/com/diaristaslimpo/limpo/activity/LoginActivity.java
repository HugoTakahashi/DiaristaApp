package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.diaristaslimpo.limpo.helper.LoginHelper;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.LoginTask;
import br.com.diaristaslimpo.limpo.to.LoginTo;
import br.com.diaristaslimpo.limpo.util.ValidationUtil;

public class LoginActivity extends AppCompatActivity {
    private LoginHelper helper;
    private LoginTo loginTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        helper = new LoginHelper(this);
        eventos();
    }

    private void eventos(){
        logar();
        cadastrar();
    }

    private void logar(){
        findViewById(R.id.login_botao_logar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper = new LoginHelper(LoginActivity.this);
                if (helper.validarCamposObrigatorios()) {

                    loginTo = helper.getLoginTo();

                    if (!ValidationUtil.isValidEmail(loginTo.getEmail())) {
                        MessageBox.showAlert(LoginActivity.this, "Atenção", "E-mail inválido");
                        return;
                    }

                    String json = helper.getLoginTo().toString();
                    new LoginTask(LoginActivity.this).execute(json);
                }
            }
        });
    }

    private void cadastrar(){
        findViewById(R.id.login_botao_cadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, FormularioDiaristaActivity.class);
                startActivity(it);
            }
        });
    }
}