package br.com.diaristaslimpo.limpo.helper;

import android.widget.TextView;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.DetalheSolicitacaoActivity;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;

/**
 * Created by Hugo on 13/11/2016.
 */

public class DetalheSolicitacaoHelper {
    private TextView nomeCliente, dataDiaria, horaInicio, servicos, observacoes, valor, endereco;

    public DetalheSolicitacaoHelper(DetalheSolicitacaoActivity activity){
        nomeCliente = (TextView) activity.findViewById(R.id.detalhe_solicitacao_nome);
        dataDiaria = (TextView) activity.findViewById(R.id.detalhe_solicitacao_data);
        horaInicio = (TextView) activity.findViewById(R.id.detalhe_solicitacao_hora_inicio);
        servicos = (TextView) activity.findViewById(R.id.detalhe_solicitacao_servicos);
        observacoes = (TextView) activity.findViewById(R.id.detalhe_solicitacao_observacoes);
        valor = (TextView) activity.findViewById(R.id.detalhe_solicitacao_valor);
        endereco = (TextView) activity.findViewById(R.id.detalhe_solicitacao_endereco);
    }

    public void preenche(MinhaSolicitacaoTo to){
        nomeCliente.setText(to.getNomeCliente());
        dataDiaria.setText(to.getDataDiaria());
        horaInicio.setText(to.getPeriodoDiaria());
        servicos.setText(to.getServico());
        observacoes.setText(to.getObservacao());
        valor.setText(to.getValor());
        endereco.setText(to.getEndereco());
    }
}