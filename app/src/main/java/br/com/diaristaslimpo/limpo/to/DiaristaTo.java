package br.com.diaristaslimpo.limpo.to;

/**
 * Created by user on 25/09/2016.
 */
public class DiaristaTo {

    private int IdDiarista;
    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private int cpf;
    private String email;
    private int celular;
    private String genero;

    public int getIdDiarista() {
        return IdDiarista;
    }

    public void setIdDiarista(int idDiarista) {
        IdDiarista = idDiarista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


}
