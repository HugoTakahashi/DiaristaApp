package br.com.diaristaslimpo.limpo.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.DiaristaTo;
import br.com.diaristaslimpo.limpo.to.ObjEndereco;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;
import br.com.diaristaslimpo.limpo.to.OrcamentoTo;
import br.com.diaristaslimpo.limpo.R;

/**
 * Created by user on 09/04/2016.
 */
public class ScriptSQL {
    private SQLiteDatabase conn;

    public ScriptSQL(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void inserirLogin(String id, String login, String tipousuario) {


        ContentValues values = new ContentValues();
        values.put("idUsuario", id);
        values.put("Login", login);
        values.put("logado", tipousuario);
        conn.insertOrThrow("login", null, values);

    }

    public void inserirDiarista(String idDiarista, String nome, String sobrenome, String datanasc, int cpf, String email, int celular, String genero) {


        ContentValues values = new ContentValues();
        values.put("IdDiarista", idDiarista);
        values.put("Nome", nome);
        values.put("Sobrenome", sobrenome);
        values.put("DataNascimento",datanasc);
        values.put("Cpf",cpf);
        values.put("Email",email);
        values.put("Celular",celular);
        values.put("Genero",genero);
        conn.insertOrThrow("diarista", null, values);

    }

    public void alterarCliente(String id, String nome, String sobrenome, String datanasc,int cpf,String email,int celular) {


        ContentValues values = new ContentValues();
        values.put("Nome", nome);
        values.put("Sobrenome", sobrenome);
        values.put("DataNascimento",datanasc);
        values.put("Cpf",cpf);
        values.put("Email",email);
        values.put("Celular",celular);
        conn.update("diarista", values, "IdDiarista>?", new String[]{id});

    }

    public void inserirEndereco(String idDiarista,String idEndereco, int cep, String logradouro, int numero,String complemento,String bairro,String cidade) {


        ContentValues values = new ContentValues();
        values.put("IdDiarista", idDiarista);
        values.put("IdEndereco",idEndereco);
       // values.put("IdentificacaoEndereco", nomeEndereco);
        values.put("Cep", cep);
        values.put("Logradouro", logradouro);
        values.put("Cidade", cidade);
        values.put("Numero",numero);
        values.put("Complemento",complemento);
        values.put("Bairro",bairro);
      //  values.put("Pontoreferencia", pontoreferencia);
        conn.insertOrThrow("endereco", null, values);

    }

    public void alterarEndereco(String IdEndereco,String nomeEndereco,int cep, String endereco, int numero,String complemento,String bairro,String cidade,String estado, String pontoreferencia) {


        ContentValues values = new ContentValues();
        values.put("IdentificacaoEndereco", nomeEndereco);
        values.put("Cep", cep);
        values.put("Endereco", endereco);
        values.put("Numero",numero);
        values.put("Complemento",complemento);
        values.put("Bairro",bairro);
        values.put("Cidade",cidade);
        values.put("Estado",estado);
        values.put("Pontoreferencia",pontoreferencia);
        conn.update("endereco", values, "IdEndereco>?", new String[]{IdEndereco});

    }

    public int isLogin(Context context) {
        int login = 0;
        Cursor cursor = conn.query("Login", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                login = (int) cursor.getLong(cursor.getColumnIndex("logado"));

                /*Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndex(Contato.ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndex(Contato.NOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndex(Contato.TELEFONE)));
                contato.setTipoTelefone(cursor.getString(cursor.getColumnIndex(Contato.TIPOTELEFONE)));
                contato.setEmail(cursor.getString(cursor.getColumnIndex(Contato.EMAIL)));
                contato.setTipoEmail(cursor.getString(cursor.getColumnIndex(Contato.TIPOEMAIL)));
                contato.setEndereco(cursor.getString(cursor.getColumnIndex(Contato.ENDERECO)));
                contato.setTipoEndereco(cursor.getString(cursor.getColumnIndex(Contato.TIPOENDERECO)));
                contato.setDatasEspeciais(new Date(cursor.getLong(cursor.getColumnIndex(Contato.DATASESP))));
                contato.setTiposDatasEspeciais(cursor.getString(cursor.getColumnIndex(Contato.TIPOSDATASESP)));
                contato.setGrupos(cursor.getString(cursor.getColumnIndex(Contato.GRUPOS)));
                adpContatos.add(contato);*/
            } while (cursor.moveToNext());
        }
        return login;
    }

    public void logof() {
        conn.delete("Login", null, null);
        conn.delete("diarista",null,null);
        conn.delete("endereco",null,null);
        limpaListaSolicitacao();
    }

    public void limpaListaSolicitacao(){
        conn.delete("listaSolicitacao",null,null);
    }

    public ArrayList<ObjEndereco> selectEndereço(){
        ArrayList<ObjEndereco> array = new ArrayList<ObjEndereco>();
        ObjEndereco obj = new ObjEndereco();
        Cursor cursor = conn.rawQuery("select * from endereco",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();


            do{
                obj.setIdEndereco(cursor.getString(cursor.getColumnIndex("IdEndereco")));
                obj.setEndereco(cursor.getString(cursor.getColumnIndex("Logradouro")));
                obj.setBairro(cursor.getString(cursor.getColumnIndex("Bairro")));
                obj.setCep(cursor.getString(cursor.getColumnIndex("Cep")));
                obj.setIdentificacaoEndereco(cursor.getString(cursor.getColumnIndex("IdentificacaoEndereco")));
                obj.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
                obj.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));
                obj.setCidade(cursor.getString(cursor.getColumnIndex("Cidade")));
                obj.setPontoreferencia(cursor.getString(cursor.getColumnIndex("Pontoreferencia")));
                obj.setIconeRid(R.mipmap.iconapp);
                array.add(obj);
                obj = new ObjEndereco();
            }while (cursor.moveToNext());

        }
        return array;
    }

    public String[] carregaLogin() {
        String[] dados = new String[1];
        Cursor cursor = conn.rawQuery("select * from Login",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                dados[0] = cursor.getString(cursor.getColumnIndex("Login"));
            } while (cursor.moveToNext());
        }
        return dados;
    }
    public int retornaIdDiarista(){
        int id=0;
        Cursor cursor = conn.rawQuery("select * from diarista",null);
        if (cursor.getCount() >0){
            cursor.moveToFirst();

                id = cursor.getInt(cursor.getColumnIndex("IdDiarista"));

        }
        return id;

    }

    public void gravaListaDiarista(int idDiarista, String nome, String cep, int rate, int distacia) {
        ContentValues values = new ContentValues();
        values.put("idDiarista", idDiarista);
        values.put("nome",nome);
        values.put("rate", rate);
        values.put("cep", cep);
        values.put("distancia", distacia);
        conn.insertOrThrow("listadiaristas", null, values);

    }

    public ArrayList<DiaristaTo> selectDiarista(){
        ArrayList<DiaristaTo> array = new ArrayList<DiaristaTo>();
        DiaristaTo obj = new DiaristaTo();
        Cursor cursor = conn.rawQuery("select * from diarista",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();

            do{
                obj.setIdDiarista(cursor.getInt(cursor.getColumnIndex("IdDiarista")));
                obj.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
                obj.setSobrenome(cursor.getString(cursor.getColumnIndex("Sobrenome")));
                obj.setDataNascimento(cursor.getString(cursor.getColumnIndex("DataNascimento")));
                obj.setCpf(cursor.getInt(cursor.getColumnIndex("Cpf")));
                obj.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                obj.setCelular(cursor.getInt(cursor.getColumnIndex("Celular")));
                obj.setGenero(cursor.getString(cursor.getColumnIndex("Genero")));
                array.add(obj);
                obj = new DiaristaTo();
            }while (cursor.moveToNext());

        }
        return array;
    }

   public ArrayList<MinhaSolicitacaoTo> selectListaSolicitacoes(){
        ArrayList<MinhaSolicitacaoTo> array = new ArrayList<MinhaSolicitacaoTo>();
        MinhaSolicitacaoTo obj = new MinhaSolicitacaoTo();
        Cursor cursor = conn.rawQuery("select * from listaSolicitacao",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();

            do{

                obj.setIdCliente(cursor.getInt(cursor.getColumnIndex("IdCliente")));
                obj.setIdSolicitacao(cursor.getInt(cursor.getColumnIndex("IdSolicitacao")));
                obj.setNomeCliente(cursor.getString(cursor.getColumnIndex("NomeCliente")));
                obj.setDataDiaria(cursor.getString(cursor.getColumnIndex("DataDiaria")));
                obj.setEndereco(cursor.getString(cursor.getColumnIndex("Endereco")));
                obj.setObservacao(cursor.getString(cursor.getColumnIndex("Observacao")));
                array.add(obj);
                obj = new MinhaSolicitacaoTo();
            }while (cursor.moveToNext());

        }
        return array;
    }

    public void insertHistorico(String idSolicitacao, String nome, double valor, String status, String dataSolicitacao) {
        ContentValues values = new ContentValues();
        values.put("IdSolicitacao", idSolicitacao);
        values.put("nome",nome);
        values.put("valor", valor);
        values.put("status", status);
        values.put("dataSolicitacao",dataSolicitacao);
        conn.insertOrThrow("historico", null, values);

    }

    public ArrayList<OrcamentoTo> selectListaHistorico(){
        ArrayList<OrcamentoTo> array = new ArrayList<OrcamentoTo>();
        OrcamentoTo obj = new OrcamentoTo();
        Cursor cursor = conn.rawQuery("select * from historico",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();

            do{
                obj.setCodSolicitacao(cursor.getInt(cursor.getColumnIndex("IdSolicitacao")));
                obj.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                obj.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                obj.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                obj.setDataSolicitacao(cursor.getString(cursor.getColumnIndex("dataSolicitacao")));
                array.add(obj);
                obj = new OrcamentoTo();
            }while (cursor.moveToNext());

        }
        return array;
    }

    public void insereServicos(int limpeza, int passarRoupa, int lavarRoupa) {

        ContentValues values = new ContentValues();
        values.put("Limpeza", limpeza);
        values.put("PassarRoupa",passarRoupa);
        values.put("LavarRoupa", lavarRoupa);
        conn.insertOrThrow("servico", null, values);

    }
}
