package br.com.diaristaslimpo.limpo.helper;

import android.widget.EditText;
import android.widget.RadioButton;

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

    private void mask(){
        cpf.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.CPF, cpf));
        celular.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.TEL, celular));
    }
}