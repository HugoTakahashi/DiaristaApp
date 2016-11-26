package br.com.diaristaslimpo.limpo.to;

import java.io.Serializable;

/**
 * Created by Hugo on 14/11/2016.
 */

public class CadastroCompletoDiaristaTo implements Serializable {
    private FormularioDiaristaTo formularioDiaristaTo;
    private SelecionarServicoTo selecionarServicoTo;
    private EnderecoTo enderecoTo;

    public FormularioDiaristaTo getFormularioDiaristaTo() {
        return formularioDiaristaTo;
    }

    public void setFormularioDiaristaTo(FormularioDiaristaTo formularioDiaristaTo) {
        this.formularioDiaristaTo = formularioDiaristaTo;
    }

    public SelecionarServicoTo getSelecionarServicoTo() {
        return selecionarServicoTo;
    }

    public void setSelecionarServicoTo(SelecionarServicoTo selecionarServicoTo) {
        this.selecionarServicoTo = selecionarServicoTo;
    }

    public EnderecoTo getEnderecoTo() {
        return enderecoTo;
    }

    public void setEnderecoTo(EnderecoTo enderecoTo) {
        this.enderecoTo = enderecoTo;
    }
}