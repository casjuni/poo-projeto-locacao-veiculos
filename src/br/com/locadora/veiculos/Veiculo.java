package br.com.locadora.veiculos;

import br.com.locadora.locacao.Locacao;

public class Veiculo {

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public Opcionais[] getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(Opcionais[] opcionais) {
        this.opcionais = opcionais;
    }

    private String placa;
    private boolean disponivel;
    private int km;
    private String marca;
    private String modelo;
    private TipoCombustivel combustivel;
    private TipoVeiculo tipo;
    private Opcionais[] opcionais;

    public Veiculo(String placa, int km, String marca, String modelo, TipoCombustivel combustivel, Opcionais[] opcionais) {
        setPlaca(placa);
        setDisponivel(true);
        setKm(km);
        setMarca(marca);
        setModelo(modelo);
        setTipoCombustivel(combustivel);
        setOpcionais(opcionais);
    }
    
    @Override
    public String toString() {
    	return "Placa "+placa+" km "+km+" Marca "+marca+" Modelo "+ modelo+" Combustível "+combustivel;
    }

}
