package br.com.locadora.locacao;

import br.com.locadora.veiculos.Veiculo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Simulacao extends Locacao {

    private static int codigoSimulacao = 0;
    private boolean simulacaoAtiva;
    private boolean operacaoEfetivada;

    public Simulacao(Veiculo veiculo, String cpf, Date dataLocacao, Date dataDevolucao) {

        super(veiculo, cpf, dataLocacao, dataDevolucao);

        if (!veiculo.isDisponivel()) {
            System.out.println("Veiculo nao disponivel para locacao.");
            return;
        }

        setCodigoSimulacao();
        setSituacao(Situacao.PENDENTE);

        Calculadora calculadora = new Calculadora();

        setValorLocacao(calculadora.CalcularValorLocacao(veiculo, getDataRetirada(), getDataDevolucao()));
    }

    public static int getCodigoSimulacao() {
        return codigoSimulacao;
    }

    public static void setCodigoSimulacao() {
        Simulacao.codigoSimulacao = codigoSimulacao + 1;
    }

    public boolean isOperacaoEfetivada() {
        return operacaoEfetivada;
    }

    public void setOperacaoEfetivada(boolean operacaoEfetivada) {
        if(operacaoEfetivada) {

            Date hoje = new Date();
            if(getDataRetirada().before(hoje)) {
                this.operacaoEfetivada = false;
                System.out.println("Nao foi possivel efetivar a operacao. A data de retirada " + getDataRetirada() + " e anterior a data atual.");
            }
            else
            {
                this.operacaoEfetivada = operacaoEfetivada;
                setCodigoLocacao(getCodigoSimulacao());
            }

        }
    }

    public Date stringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String exibirSimulacao()
    {
        return "********** SIMULACAO **********" +
                "\nCodigo: " + getCodigoSimulacao() + " - Veiculo: " + getVeiculo().getPlaca() +
                "\n - Cliente: " + getCliente() +
                "\n - Data retirada: " + getDataRetirada() +
                "\n - Data devolucao: " + getDataDevolucao() +
                "\n - Valor total: " + getValorLocacao() + "\n";
    }
}
