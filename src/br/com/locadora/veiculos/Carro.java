package br.com.locadora.veiculos;

public class Carro extends Veiculo {

    public Carro(String placa, boolean disponivel, int km, String modelo, TipoCombustivel tipoCombustivel) {
        super(placa, disponivel, km, modelo, tipoCombustivel);
        setTipo(TipoVeiculo.CARRO);
    }

    @Override
    public String toString() {
        return "Placa: " + getPlaca() + " - " + "Disponivel: " + isDisponivel() + " - ";
    }

}
