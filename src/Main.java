import br.com.locadora.locacao.Locacao;
import br.com.locadora.locacao.Retirada;
import br.com.locadora.locacao.Simulacao;
import br.com.locadora.pessoa.Cliente;
import br.com.locadora.pessoa.Pessoa;
import br.com.locadora.veiculos.Carro;
import br.com.locadora.veiculos.Opcionais;
import br.com.locadora.veiculos.TipoCombustivel;
import br.com.locadora.veiculos.Veiculo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        Veiculo veiculo = new Carro("ELO1025",
                                    50000,
                                    "Honda",
                                    "Civic",
                                    TipoCombustivel.GASOLINA,
                                    new Opcionais[] {Opcionais.AR_CONDICIONADO, Opcionais.DIRECAO_HIDRAULICA});

        Veiculo veiculo2 = new Carro("ELO1025",
                50000,
                "Honda",
                "Civic",
                TipoCombustivel.GASOLINA,
                new Opcionais[] {Opcionais.AR_CONDICIONADO, Opcionais.DIRECAO_HIDRAULICA, Opcionais.BANCOS_COURO, Opcionais.VIDROS_ELETRICOS});

       // System.out.println(veiculo.toString());
        //System.out.println(veiculo2.toString());

        Date hoje = new Date();



        Locacao locacao = new Simulacao(veiculo, "12345678900","04/08/2019", "10/08/2019");
        ((Simulacao) locacao).setOperacaoEfetivada(true);
        //System.out.println(((Simulacao) locacao).exibirSimulacao());
        //System.out.println(locacao.exibirLocacao());
        locacao.realizarRetirada(hoje, 51000);
        System.out.println(locacao.exibirLocacao());
        locacao.realizarDevolucao(StringToDate("09/08/2019"), 52000);
        System.out.println(locacao.exibirLocacao());

    }

    private static Date StringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}