package br.com.diaristaslimpo.limpo.adapter;

/**
 * Created by user on 23/08/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;
import br.com.diaristaslimpo.limpo.R;

public class MinhasSolicitacoesAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<MinhaSolicitacaoTo> items;

    public MinhasSolicitacoesAdapter(Context context, ArrayList<MinhaSolicitacaoTo> items)
    {
        //Itens que preencheram o listview
        this.items = items;
        //responsavel por pegar o Layout do item.
        inflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return items.size();
    }

    public MinhaSolicitacaoTo getItem(int position)
    {
        return items.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posção.
        MinhaSolicitacaoTo item = items.get(position);
        //infla o layout para podermos preencher os dados
        view = inflater.inflate(R.layout.listview_minhas_solicitacoes, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.solicitacao_nome)).setText(item.getNomeCliente());
        ((TextView) view.findViewById(R.id.solicitacao_data)).setText(String.valueOf(item.getDataDiaria()));
        ((TextView) view.findViewById(R.id.solicitacao_endereco)).setText(item.getEndereco());

        return view;
    }
}