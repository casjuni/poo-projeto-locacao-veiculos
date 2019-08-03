package br.com.locadora.veiculos;

public class Moto extends Veiculo{

    public Moto(String placa, int km, String marca, String modelo, TipoCombustivel tipoCombustivel)
    {
        super(placa, km, marca, modelo, tipoCombustivel, null);
        setTipo(TipoVeiculo.MOTO);
    }

    @Override
    public String toString()
    {
        return "Placa: " + getPlaca() + " - " + "Disponivel: " + isDisponivel() + " - ";
    }

}
