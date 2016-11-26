package br.com.diaristaslimpo.limpo.to;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Hugo on 20/11/2016.
 */

public class SelecionarServicoTo implements Serializable{
    private int id, idDiarista;
    private boolean limpeza, passaRoupa, lavaRoupa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDiarista() {
        return idDiarista;
    }

    public void setIdDiarista(int idDiarista) {
        this.idDiarista = idDiarista;
    }

    public boolean isLimpeza() {
        return limpeza;
    }

    public void setLimpeza(boolean limpeza) {
        this.limpeza = limpeza;
    }

    public boolean isPassaRoupa() {
        return passaRoupa;
    }

    public void setPassaRoupa(boolean passaRoupa) {
        this.passaRoupa = passaRoupa;
    }

    public boolean isLavaRoupa() {
        return lavaRoupa;
    }

    public void setLavaRoupa(boolean lavaRoupa) {
        this.lavaRoupa = lavaRoupa;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("Id", id);
            json.put("IdDiarista", idDiarista);
            json.put("Limpeza", limpeza);
            json.put("PassarRoupa", passaRoupa);
            json.put("LavarRoupa", lavaRoupa);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return String.valueOf(json);
    }
}