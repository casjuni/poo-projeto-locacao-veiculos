package br.com.locadora.veiculos;

public class Carro extends Veiculo {

    public Carro(String placa, int km, String marca, String modelo, TipoCombustivel tipoCombustivel, Opcionais[] opcionais) {
        super(placa, km, marca, modelo, tipoCombustivel, opcionais);
        setTipo(TipoVeiculo.CARRO);
    }

    @Override
    public String toString() {
        return "Placa: " + getPlaca() + " - " + "Disponivel: " + isDisponivel() + " - Modelo: " + getModelo() + "";
    }

}
