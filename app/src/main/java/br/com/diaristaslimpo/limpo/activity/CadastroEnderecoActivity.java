package br.com.diaristaslimpo.limpo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.AsyncJsonObjectResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.AlteraEnderecoTask;
import br.com.diaristaslimpo.limpo.task.BuscarEnderecoTask;
import br.com.diaristaslimpo.limpo.task.CadastrarDiaristaTask;
import br.com.diaristaslimpo.limpo.to.CadastroCompletoDiaristaTo;
import br.com.diaristaslimpo.limpo.to.EnderecoTo;
import br.com.diaristaslimpo.limpo.util.CepUtil;
import br.com.diaristaslimpo.limpo.util.MaskUtil;

public class CadastroEnderecoActivity extends AppCompatActivity implements AsyncJsonObjectResponse {
    private EditText cep, logradouro, numero, complemento, bairro, cidade;
    private CadastroCompletoDiaristaTo to = new CadastroCompletoDiaristaTo();
    private EnderecoTo enderecoTo = new EnderecoTo();
    private BuscarEnderecoTask asyncTask = new BuscarEnderecoTask(this);
    private String idDiarista;

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
        idDiarista = bundle.getString("idDiarista");
        if (!TextUtils.isEmpty(idDiarista)){
            asyncTask.delegate = this;
            asyncTask.execute(String.valueOf(idDiarista));
        }

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

                if(validarPreenchimentoCamposObrigatorios()) {
                    AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.setTitle("Salvar ?");
                    dialog.setMessage("Escolha sim para Salvar ou não para Cancelar");

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            enderecoTo = new EnderecoTo();
                            enderecoTo.setBairro(bairro.getText().toString());
                            enderecoTo.setCidade(cidade.getText().toString());
                            enderecoTo.setCep(cep.getText().toString());
                            enderecoTo.setLogradouro(logradouro.getText().toString());
                            enderecoTo.setNumero(numero.getText().toString());
                            enderecoTo.setComplemento(complemento.getText().toString());
                            enderecoTo.setUf("SP");

                            if (!TextUtils.isEmpty(idDiarista)) {
                                enderecoTo.setIdDiarista(Integer.parseInt(idDiarista));
                                new AlteraEnderecoTask(CadastroEnderecoActivity.this)
                                        .execute(enderecoTo.toString(), idDiarista);
                            } else {
                                to.setEnderecoTo(enderecoTo);
                                new CadastrarDiaristaTask(CadastroEnderecoActivity.this)
                                        .execute(to);
                            }
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(JSONObject json) {
        try {
            cep.setText((String) json.get("Cep"));
            logradouro.setText((String) json.get("Logradouro"));
            numero.setText((String) json.get("Numero"));
            bairro.setText((String) json.get("Bairro"));
            cidade.setText((String) json.get("Cidade"));
            if(!json.isNull("Complemento"))
                complemento.setText((String) json.get("Complemento"));
        } catch(JSONException e){
        }
    }

    public boolean validarPreenchimentoCamposObrigatorios(){
        boolean cepPreenchido = true;
        boolean numeroPreenchido = true;

        if(cep.getText().toString().matches("")){
            cep.setError("Campo obrigatório");
            cepPreenchido = false;
        }

        if(numero.getText().toString().matches("")){
            numero.setError("Campo obrigatório");
            cepPreenchido = false;
        }

        return cepPreenchido && numeroPreenchido;
    }
}