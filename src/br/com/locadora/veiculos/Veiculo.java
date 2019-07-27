package br.com.locadora.veiculos;

import br.com.locadora.locacao.Locacao;

public class Veiculo extends Locacao {

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public TipoCombustivel getTipoCombustivel() {
        return combustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.combustivel = tipoCombustivel;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculo tipo) {
        this.tipo = tipo;
    }

    private String placa;
    private boolean disponivel;
    private int km;
    public String marca;
    private String modelo;
    private TipoCombustivel combustivel;
    private TipoVeiculo tipo;

    public Veiculo(String placa, boolean disponivel, int km, String modelo, TipoCombustivel combustivel) {
        setPlaca(placa);
        setDisponivel(disponivel);
        setKm(km);
        setModelo(modelo);
        setTipoCombustivel(combustivel);
    }

}
