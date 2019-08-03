package br.com.locadora.locacao;

import br.com.locadora.veiculos.Veiculo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calculadora {

    private static final float IMPOSTOS = 0.05f;
    private static final float TARIFA_BASE = 70;

    public float CalcularValorLocacao(Veiculo veiculo, Date dataInicial, Date dataFinal)
    {
        Calendar calendarInicial = new GregorianCalendar();
        calendarInicial.setTime(dataInicial);
        Calendar calendarFinal = new GregorianCalendar();
        calendarFinal.setTime(dataFinal);

        int dias = (int)((calendarFinal.getTimeInMillis() - calendarInicial.getTimeInMillis()) / (24*60*60*1000));

        int qtdeOpcionais = veiculo.getOpcionais().length;

        return ( dias * (qtdeOpcionais * 0.6f * TARIFA_BASE)) + (dias * (qtdeOpcionais * TARIFA_BASE)) * IMPOSTOS;
    }

    public float CalcularValorMulta(Veiculo veiculo, Date dataFinal, Date dataDevolucao)
    {
        Calendar calendarInicial = new GregorianCalendar();
        calendarInicial.setTime(dataFinal);
        Calendar calendarFinal = new GregorianCalendar();
        calendarFinal.setTime(dataDevolucao);

        int diasAtraso = (int)((calendarFinal.getTimeInMillis() - calendarInicial.getTimeInMillis()) / (24*60*60*1000));

        return (diasAtraso * (0.2f * TARIFA_BASE));
    }

}
