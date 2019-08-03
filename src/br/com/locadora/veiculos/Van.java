package br.com.locadora.veiculos;

public class Van extends Veiculo {

    public Van(String placa, int km, String marca, String modelo, TipoCombustivel tipoCombustivel, Opcionais[] opcionais) {
        super(placa, km, marca, modelo, tipoCombustivel, opcionais);
        setTipo(TipoVeiculo.VAN);
    }

    @Override
    public String toString() {
        return "Placa: " + getPlaca() + " - " + "Disponivel: " + isDisponivel() + " - ";
    }

}
