package br.com.locadora.locacao;

import br.com.locadora.veiculos.Veiculo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Simulacao extends Locacao {

    private static final float IMPOSTOS = 0.05f;
    private static final float TARIFA_BASE = 70;
    private static int codigoSimulacao = 0;
    private Date dataInicial;
    private Date dataFinal;
    private float valorLocacao;
    private boolean simulacaoAtiva;
    private boolean operacaoEfetivada;
    private Veiculo veiculo;
    private String cpf;

    public Simulacao(Veiculo veiculo, String cpf, String dataLocacao, String dataDevolucao) {

        if(!veiculo.isDisponivel()) {
            System.out.println("Veículo não está disponível para locação.");
            return;
        }

        setVeiculos(veiculo);
        setCodigoSimulacao();
        setDataInicial(stringToDate(dataLocacao));
        setDataFinal(stringToDate(dataDevolucao));
        setSituacao(Situacao.PENDENTE);
        setCpf(cpf);

        Calculadora calculadora = new Calculadora();

        setValorLocacao(calculadora.CalcularValorLocacao(veiculo, getDataInicial(), getDataFinal()));
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    private String getCpf() {
        return cpf;
    }

    private void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static int getCodigoSimulacao() {
        return codigoSimulacao;
    }

    public static void setCodigoSimulacao() {
        Simulacao.codigoSimulacao = codigoSimulacao + 1;
    }

    @Override
    public float getValorLocacao() {
        return valorLocacao;
    }

    @Override
    public void setValorLocacao(float valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public boolean isOperacaoEfetivada() {
        return operacaoEfetivada;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculos(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public void setOperacaoEfetivada(boolean operacaoEfetivada) {
        if(operacaoEfetivada) {

            Date hoje = new Date();
            if(getDataInicial().before(hoje)) {
                this.operacaoEfetivada = false;
                System.out.println("Não foi possível efetivar a operação. A data de retirada " + getDataInicial() + " é anterior a data atual.");
            }
            else
            {
                this.operacaoEfetivada = operacaoEfetivada;
                setVeiculo(getVeiculo());
                setCliente(this.getCpf());
                setCodigoLocacao(getCodigoSimulacao());
                setDataRetirada(getDataInicial());
                setDataDevolucao(getDataFinal());
            }

        }
    }

    private Date stringToDate(String date) {
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
        return "********** SIMULAÇÃO **********" +
                "\nCódigo: " + getCodigoSimulacao() + " - Veículo: " + getVeiculo().getPlaca() +
                "\n - Cliente: " + getCpf() +
                "\n - Data retirada: " + getDataInicial() +
                "\n - Data devolução: " + getDataFinal() +
                "\n - Valor total: " + getValorLocacao() + "\n";
    }
}
