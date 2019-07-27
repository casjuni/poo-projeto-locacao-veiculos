package br.com.locadora.veiculos;

public class Van extends Veiculo {

    public Van(String placa, boolean disponivel, int km, String modelo, TipoCombustivel tipoCombustivel) {
        super(placa, disponivel, km, modelo, tipoCombustivel);
        setTipo(TipoVeiculo.VAN);
    }

    @Override
    public String toString() {
        return "Placa: " + getPlaca() + " - " + "Disponivel: " + isDisponivel() + " - ";
    }

}
