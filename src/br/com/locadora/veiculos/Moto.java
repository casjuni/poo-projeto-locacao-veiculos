package br.com.locadora.veiculos;

public class Moto extends Veiculo{

    public Moto(String placa, boolean disponivel, int km, String modelo, TipoCombustivel tipoCombustivel)
    {
        super(placa, disponivel, km, modelo, tipoCombustivel);
        setTipo(TipoVeiculo.MOTO);
    }

    @Override
    public String toString()
    {
        return "Placa: " + getPlaca() + " - " + "Disponivel: " + isDisponivel() + " - ";
    }

}
