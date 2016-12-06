package br.com.diaristaslimpo.limpo.adapter;

/**
 * Created by user on 23/08/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.OrcamentoTo;
import br.com.diaristaslimpo.limpo.R;

public class AdapterOrcamento extends BaseAdapter{
    private LayoutInflater mInflater;
    private ArrayList<OrcamentoTo> itens;

    public AdapterOrcamento(Context context, ArrayList<OrcamentoTo> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount()
    {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public OrcamentoTo getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posção.
        OrcamentoTo item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.listview_orcamento, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.text)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.text2)).setText(String.valueOf(item.getValor()));
        ((TextView) view.findViewById(R.id.text3)).setText(String.valueOf(item.getStatus()));
        ((TextView) view.findViewById(R.id.text4)).setText(String.valueOf(item.getDataSolicitacao()));
        ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());

        return view;
    }
}
