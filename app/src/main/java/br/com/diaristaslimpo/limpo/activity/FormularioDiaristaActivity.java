package br.com.diaristaslimpo.limpo.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import org.json.JSONObject;

import java.util.Calendar;

import br.com.diaristaslimpo.limpo.AsyncJsonObjectResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.helper.FormularioDiaristaHelper;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.task.AlteraCadastroTask;
import br.com.diaristaslimpo.limpo.task.BuscarDiaristaTask;
import br.com.diaristaslimpo.limpo.to.CadastroCompletoDiaristaTo;
import br.com.diaristaslimpo.limpo.to.FormularioDiaristaTo;
import br.com.diaristaslimpo.limpo.util.ValidationUtil;

public class FormularioDiaristaActivity extends AppCompatActivity implements AsyncJsonObjectResponse {
    private FormularioDiaristaTo formularioClienteTo;
    private FormularioDiaristaHelper helper;
    private String idDiarista;
    private BuscarDiaristaTask asyncTask = new BuscarDiaristaTask(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_diarista);

        helper = new FormularioDiaristaHelper(this);

        ExibeDataListener listener = new ExibeDataListener();
        helper.getDataNascimento().setOnClickListener(listener);
        helper.getDataNascimento().setOnFocusChangeListener(listener);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        try{
            idDiarista = bundle.getString("idDiarista");
            if (!TextUtils.isEmpty(idDiarista)){
                asyncTask.delegate = this;
                asyncTask.execute(String.valueOf(idDiarista));
            }
        } catch (Exception ex){}
    }

    private void exibeData(){
        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new SelecionaDataListener(), ano - 18, mes, dia);
        calendar.set(ano - 18, mes, dia);
        pickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        pickerDialog.show();
    }

    private class ExibeDataListener implements View.OnClickListener,View.OnFocusChangeListener {

        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                exibeData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            helper.setDataNascimento(year, monthOfYear, dayOfMonth);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_cadastro1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_cadastro:
                helper = new FormularioDiaristaHelper(this);
                if(helper.validarCamposObrigatorios()){
                    formularioClienteTo = helper.getFormularioClienteTo();

                    if (!(formularioClienteTo.getSenha().equals(formularioClienteTo.getConfirmacaoSenha()))){
                        MessageBox.show(this,"Atenção","Senhas não estão iguais");
                        break;
                    }

                    if(!ValidationUtil.isValidCPF(formularioClienteTo.getCpf())){
                        MessageBox.show(this,"Atenção","CPF inválido");
                        break;
                    }

                    if(!ValidationUtil.isValidEmail(formularioClienteTo.getEmail())){
                        MessageBox.show(this,"Atenção","E-mail inválido");
                        break;
                    }

                    if (!TextUtils.isEmpty(idDiarista)){
                        String dataNascimento = helper.getDataNascimento().getText().toString();
                        String[] data = dataNascimento.split("/");
                        String dataFormatada = data[2] + "-" + data[1] + "-" + data[0];
                        formularioClienteTo.setDataNascimento(dataFormatada);
                        formularioClienteTo.setId(Integer.parseInt(idDiarista));
                        new AlteraCadastroTask(FormularioDiaristaActivity.this)
                                .execute(formularioClienteTo.toString(), idDiarista);
                    } else{
                        String dataNascimento = helper.getDataNascimento().getText().toString();
                        String[] data = dataNascimento.split("/");
                        String dataFormatada = data[2] + "-" + data[1] + "-" + data[0];
                        formularioClienteTo.setDataNascimento(dataFormatada);

                        CadastroCompletoDiaristaTo to = new CadastroCompletoDiaristaTo();
                        formularioClienteTo.setDataNascimento(dataFormatada);
                        to.setFormularioDiaristaTo(formularioClienteTo);

                        Intent intent = new Intent(FormularioDiaristaActivity.this, SelecionarServicoActivity.class);
                        intent.putExtra("cadastroCompletoDiaristaTo", to);
                        startActivity(intent);
                    }
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(JSONObject json) {
        helper.preencheCampos(json);
    }
}