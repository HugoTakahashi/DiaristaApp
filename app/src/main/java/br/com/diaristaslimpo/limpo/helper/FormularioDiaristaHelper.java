package br.com.diaristaslimpo.limpo.helper;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.FormularioDiaristaActivity;
import br.com.diaristaslimpo.limpo.to.FormularioDiaristaTo;
import br.com.diaristaslimpo.limpo.util.DateUtil;
import br.com.diaristaslimpo.limpo.util.MaskUtil;

/**
 * Created by Hugo on 14/11/2016.
 */

public class FormularioDiaristaHelper extends BaseHelper{
    private EditText nome, sobrenome, dataNascimento, cpf, email, senha, confirmacaoSenha, celular;
    private RadioButton masculino, feminino;
    private LinearLayout genero;
    private FormularioDiaristaTo to;

    public EditText getDataNascimento(){
        return this.dataNascimento;
    }

    public void setDataNascimento(int year, int month, int day){
        this.dataNascimento.setText(DateUtil.dateToString(year, month, day));
        to.setDataNascimento(DateUtil.dateToJson(year, month, day));
    }

    public FormularioDiaristaHelper(FormularioDiaristaActivity activity) {
        super.setContext(activity);

        nome = (EditText) activity.findViewById(R.id.formulario_diarista_nome);
        sobrenome = (EditText) activity.findViewById(R.id.formulario_diarista_sobrenome);
        dataNascimento = (EditText) activity.findViewById(R.id.formulario_diarista_data_nascimento);
        cpf = (EditText) activity.findViewById(R.id.formulario_diarista_cpf);
        email = (EditText) activity.findViewById(R.id.formulario_diarista_email);
        senha = (EditText) activity.findViewById(R.id.formulario_diarista_senha);
        confirmacaoSenha = (EditText) activity.findViewById(R.id.formulario_diarista_confirmar_senha);
        celular = (EditText) activity.findViewById(R.id.formulario_diarista_celular);
        masculino = (RadioButton) activity.findViewById(R.id.formulario_diarista_genero_masculino);
        feminino = (RadioButton) activity.findViewById(R.id.formulario_diarista_genero_feminino);
        genero = (LinearLayout) activity.findViewById(R.id.formulario_diarista_generos);

        to = new FormularioDiaristaTo();
        mask();
    }

    public FormularioDiaristaTo getFormularioClienteTo(){
        to.setNome(nome.getText().toString());
        to.setSobrenome(sobrenome.getText().toString());
        to.setCpf(cpf.getText().toString());
        to.setEmail(email.getText().toString());
        to.setSenha(senha.getText().toString());
        to.setConfirmacaoSenha(confirmacaoSenha.getText().toString());
        to.setCelular(celular.getText().toString());

        if(masculino.isChecked())
            to.setGenero("M");
        else
            to.setGenero("F");

        return to;
    }

    public boolean validarCamposObrigatorios(){
        validarPreenchimentoCampoObrigatorio(nome);
        validarPreenchimentoCampoObrigatorio(sobrenome);
        validarPreenchimentoCampoObrigatorio(dataNascimento);
        validarPreenchimentoCampoObrigatorio(cpf);
        validarPreenchimentoCampoObrigatorio(email);
        validarPreenchimentoCampoObrigatorio(senha);
        validarPreenchimentoCampoObrigatorio(confirmacaoSenha);
        validarPreenchimentoCampoObrigatorio(celular);

        return super.getIsValid();
    }

    public void preencheCampos(JSONObject json){
        try {
            nome.setText((String) json.get("Nome"));
            sobrenome.setText((String) json.get("Sobrenome"));
            cpf.setText((String) json.get("Cpf"));
            dataNascimento.setText((String) json.get("DataNascimentoFormatada"));
            to.setDataNascimento((String) json.get("DataNascimentoJson"));
            celular.setText((String) json.get("Celular"));
            email.setText((String) json.get("Email"));
            senha.setText((String) json.get("Senha"));
            confirmacaoSenha.setText((String) json.get("Senha"));
            String genero = (String) json.get("Genero");
            if(genero.equals("F"))
                feminino.setChecked(true);
            else
                masculino.setChecked(true);
        } catch(JSONException e){
        }

        escondeCampos();
    }

    private void escondeCampos() {
        genero.setVisibility(View.GONE);
        senha.setVisibility(View.GONE);
        confirmacaoSenha.setVisibility(View.GONE);
    }

    private void mask(){
        cpf.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.CPF, cpf));
        celular.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.TEL, celular));
    }
}