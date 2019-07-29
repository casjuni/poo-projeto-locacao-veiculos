package br.com.locadora;

import br.com.locadora.veiculos.Carro;
import br.com.locadora.veiculos.TipoCombustivel;
import br.com.locadora.veiculos.Veiculo;

public class Main {

    public static void main(String[] args) {

        Veiculo veiculo = new Carro("ELO1025", true, 5000, "Civic", TipoCombustivel.GASOLINA);
        System.out.println(veiculo.toString());
    }
}
