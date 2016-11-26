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

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;

public class ProximasDiariasAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<MinhaSolicitacaoTo> itens;

    public ProximasDiariasAdapter(Context context, ArrayList<MinhaSolicitacaoTo> itens) {
        this.itens = itens;
        inflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return itens.size();
    }

    public MinhaSolicitacaoTo getItem(int position)
    {
        return itens.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        MinhaSolicitacaoTo item = itens.get(position);
//        view = mInflater.inflate(R.layout.listview_orcamento, null);
//
//        ((TextView) view.findViewById(R.id.text)).setText(item.getNome());
//        ((TextView) view.findViewById(R.id.text2)).setText(String.valueOf(item.getValor()));
//        ((TextView) view.findViewById(R.id.text3)).setText(String.valueOf(item.getStatus()));
//        ((TextView) view.findViewById(R.id.text4)).setText(String.valueOf(item.getDataSolicitacao()));
//        ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());

        view = inflater.inflate(R.layout.listview_minhas_solicitacoes, null);
        ((TextView) view.findViewById(R.id.solicitacao_nome)).setText(item.getNomeCliente());
        ((TextView) view.findViewById(R.id.solicitacao_data)).setText(String.valueOf(item.getDataDiaria()));
        ((TextView) view.findViewById(R.id.solicitacao_endereco)).setText(item.getEndereco());
        ((ImageView) view.findViewById(R.id.imagem_personalizada)).setImageResource(R.drawable.proxima_diaria);
        return view;
    }
}