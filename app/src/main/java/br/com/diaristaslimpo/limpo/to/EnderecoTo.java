package br.com.diaristaslimpo.limpo.to;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import br.com.diaristaslimpo.limpo.util.MaskUtil;

/**
 * Created by Hugo on 20/11/2016.
 */

public class EnderecoTo implements Serializable {
    private int idDiarista;
    private String cep, logradouro, numero, complemento, bairro, cidade, uf;

    public int getIdDiarista() {
        return idDiarista;
    }

    public void setIdDiarista(int idDiarista) {
        this.idDiarista = idDiarista;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("IdDiarista", idDiarista);
            json.put("Cep", MaskUtil.unmask(cep));
            json.put("Logradouro", logradouro);
            json.put("Numero", numero);
            json.put("Complemento", complemento);
            json.put("Bairro", bairro);
            json.put("Cidade", cidade);
            json.put("Uf", "SP");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return String.valueOf(json);
    }
}