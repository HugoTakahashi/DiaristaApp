package br.com.diaristaslimpo.limpo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.CadastrarDiaristaTask;
import br.com.diaristaslimpo.limpo.to.CadastroCompletoDiaristaTo;
import br.com.diaristaslimpo.limpo.to.EnderecoTo;
import br.com.diaristaslimpo.limpo.util.CepUtil;
import br.com.diaristaslimpo.limpo.util.MaskUtil;

public class CadastroEnderecoActivity extends AppCompatActivity {

    private EditText cep, logradouro, numero, complemento, bairro, cidade;
    private CadastroCompletoDiaristaTo to;
    private EnderecoTo enderecoTo = new EnderecoTo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_endereco);

        cep = (EditText) findViewById(R.id.endereco_cep);
        logradouro = (EditText) findViewById(R.id.endereco_endereco);
        numero = (EditText) findViewById(R.id.endereco_numero);
        complemento = (EditText) findViewById(R.id.endereco_complemento);
        bairro = (EditText) findViewById(R.id.endereco_bairro);
        cidade = (EditText) findViewById(R.id.endereco_cidade);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        to = (CadastroCompletoDiaristaTo) bundle.getSerializable("cadastroCompletoDiaristaTo");

        cep.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.CEP, cep));
        cep.addTextChangedListener(CepUtil.buscarEnderecoPorCep(cep, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_endereco, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_endereco:

                AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Salvar ?");
                dialog.setMessage("Escolha sim para Salvar ou não para Cancelar");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enderecoTo.setBairro(bairro.getText().toString());
                        enderecoTo.setCidade(cidade.getText().toString());
                        enderecoTo.setCep(cep.getText().toString());
                        enderecoTo.setLogradouro(logradouro.getText().toString());
                        enderecoTo.setNumero(numero.getText().toString());
                        enderecoTo.setUf("SP");

                        to.setEnderecoTo(enderecoTo);

                        new CadastrarDiaristaTask(CadastroEnderecoActivity.this).execute(to);
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}