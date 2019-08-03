package br.com.locadora.locacao;

import br.com.locadora.veiculos.Veiculo;

import java.util.Date;

public class Locacao{

    private static int codigoLocacao = 0;
    private int kmInicial;
    private int kmFinal;
    private Date dataRetirada;
    private Date dataDevolucao;
    private float valorLocacao;
    private Situacao situacao;
    private Veiculo veiculo;
    private String cliente;

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Locacao()
    {

    }

    public int getCodigoLocacao() {
        return codigoLocacao;
    }

    protected void setCodigoLocacao(int codigo) {
        this.codigoLocacao = codigo;
    }

    private Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public int getKmInicial() {
        return kmInicial;
    }

    public boolean setKmInicial(int kmInicial) {
        if(kmInicial != 0)
            this.kmInicial = kmInicial;
        return true;
    }

    public int getKmFinal() {
        return kmFinal;
    }

    public boolean setKmFinal(int kmFinal) {
        if(kmFinal <= getKmInicial()) {
            System.out.println("Km final não pode ser menor ou igual o km inicial");
            return false;
        }
        else
            this.kmFinal = kmFinal;

        return true;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public boolean setDataRetirada(Date dataRetirada) {
        Calculadora calculadora = new Calculadora();

        if(getDataRetirada() != null) {
            if (dataRetirada.after(getDataRetirada()) || dataRetirada.before(getDataRetirada())) {
                setValorLocacao(calculadora.CalcularValorLocacao(getVeiculo(), dataRetirada, getDataDevolucao()));
                return true;
            }
        }

        this.dataRetirada = dataRetirada;
        return true;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean setDataDevolucao(Date dataFinal) {

        Calculadora calculadora = new Calculadora();

        if(dataFinal.before(getDataRetirada())) {
            System.out.println("Data de retirada não pode ser menor que a data de devolução");
            return false;
        }

        if(getDataDevolucao() != null) {
            if (dataFinal.before(getDataDevolucao())) {
                setValorLocacao(calculadora.CalcularValorLocacao(getVeiculo(), getDataRetirada(), dataFinal));
            } else if (dataFinal.after(getDataDevolucao())) {
                setValorLocacao(getValorLocacao() + calculadora.CalcularValorLocacao(getVeiculo(), getDataDevolucao(), dataFinal));
                setValorLocacao(getValorLocacao() + calculadora.CalcularValorMulta(getVeiculo(), getDataDevolucao(), dataFinal));
            }
        }

        this.dataDevolucao = dataFinal;
        return true;
    }

    public float getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(float valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public void realizarRetirada(Date dataRetirada, int kmInicial) {
        if(dataRetirada != null && kmInicial != 0) {
            if(!setDataRetirada(dataRetirada))
                return;

            if(!setKmInicial(kmInicial))
                return;

            getVeiculo().setDisponivel(false);
            setSituacao(Situacao.ATIVO);
        }
        else
            System.out.println("Dados não podem ser nulos");
    }

    public void realizarDevolucao(Date dataDevolucao, int kmFinal) {
        if(!getSituacao().equals(Situacao.ATIVO)) {
            System.out.println("O veículo ainda não foi retirado");
            return;
        }

        if(dataDevolucao != null && kmFinal != 0) {
            if(!setDataDevolucao(dataDevolucao))
                return;

            if(!setKmFinal(kmFinal))
                return;

            setSituacao(Situacao.FINALIZADO);
        }
        else
            System.out.println("Dados não podem ser nulos");
    }

    public String exibirLocacao()
    {
        return "********** LOCAÇÃO **********" +
                "\nCódigo: " + getCodigoLocacao() + " - Veículo: " + getVeiculo().getPlaca() +
                "\n - Cliente: " + getCliente() +
                "\n - Situação: " + getSituacao() +
                "\n - Data retirada: " + (getDataRetirada() == null ? "Veículo não retirado" : getDataRetirada()) +
                "\n - Data devolução: " + (getDataDevolucao() == null ? "Veículo não devolvido" : getDataDevolucao()) +
                "\n - Km inicial: " + (getKmInicial() == 0 ? "Veículo não retirado" : getKmInicial()) +
                "\n - Km final: " + (getKmFinal() == 0 ? "Veículo não devolvido" : getKmFinal()) +
                "\n - Valor total: " + (getValorLocacao() == 0 ? "Valor não calculado" : getValorLocacao()) + "\n";
    }
}
