package br.com.diaristaslimpo.limpo.helper;

import android.widget.TextView;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.DetalheProximaDiariaActivity;
import br.com.diaristaslimpo.limpo.to.MinhaSolicitacaoTo;

/**
 * Created by Hugo on 13/11/2016.
 */

public class DetalheProximaDiariaHelper {
    private TextView nomeCliente, dataDiaria, horaInicio, servicos, valor, endereco;

    public DetalheProximaDiariaHelper(DetalheProximaDiariaActivity activity){
        nomeCliente = (TextView) activity.findViewById(R.id.detalhe_proxima_diaria_nome);
        dataDiaria = (TextView) activity.findViewById(R.id.detalhe_proxima_diaria_data);
        horaInicio = (TextView) activity.findViewById(R.id.detalhe_proxima_diaria_hora_inicio);
        servicos = (TextView) activity.findViewById(R.id.detalhe_proxima_diaria_servicos);
        valor = (TextView) activity.findViewById(R.id.detalhe_proxima_diaria_valor);
        endereco = (TextView) activity.findViewById(R.id.detalhe_proxima_diaria_endereco);
    }

    public void preenche(MinhaSolicitacaoTo to){
        nomeCliente.setText(to.getNomeCliente());
        dataDiaria.setText(to.getDataDiaria());
        horaInicio.setText(to.getPeriodoDiaria());
        servicos.setText(to.getServico());
        valor.setText(to.getValor());
        endereco.setText(to.getEndereco());
    }
}