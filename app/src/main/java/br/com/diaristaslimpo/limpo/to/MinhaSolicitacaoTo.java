package br.com.diaristaslimpo.limpo.to;

import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by user on 18/09/2016.
 */
public class MinhaSolicitacaoTo implements Serializable{

    private int idCliente;
    private int idSolicitacao;
    private String NomeCliente;
    private String dataDiaria;
    private String Endereco;
    private String Servico;
    private String Observacao;
    private String Valor;
    private String PeriodoDiaria;
    private String dataDiariaJson;

    public MinhaSolicitacaoTo(){}

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(int idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public String getNomeCliente() {
        return NomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        NomeCliente = nomeCliente;
    }

    public String getDataDiaria() {
        return dataDiaria;
    }

    public void setDataDiaria(String dataDiaria) {
        this.dataDiaria = dataDiaria;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getPeriodoDiaria() {
        return PeriodoDiaria;
    }

    public void setPeriodoDiaria(String periodoDiaria) {
        PeriodoDiaria = periodoDiaria;
    }

    public String getServico() {
        return Servico;
    }

    public void setServico(String servico) {
        Servico = servico;
    }

    public String getDataDiariaJson() {
        return dataDiariaJson;
    }

    public void setDataDiariaJson(String dataDiariaJson) {
        this.dataDiariaJson = dataDiariaJson;
    }

    public MinhaSolicitacaoTo jsonParaTo(MinhaSolicitacaoTo to, JSONObject json){
        try {
            to.setIdCliente((Integer) json.get("IdCliente"));
            to.setIdSolicitacao((Integer) json.get("IdSolicitacao"));
            to.setNomeCliente((String) json.get("Nome"));
            to.setDataDiaria((String) json.get("DataDiaria"));
            to.setEndereco((String) json.get("Endereco"));
            to.setServico((String) json.get("Servicos"));
            to.setValor((String) json.get("Valor"));
            to.setPeriodoDiaria((String) json.get("PeriodoDiaria"));
            to.setDataDiariaJson((String) json.get("DataDiariaJson"));
            if(!json.isNull("Observacao"))
                to.setObservacao((String) json.get("Observacao"));

        } catch(Exception ex) {
        }

        return to;
    }
}