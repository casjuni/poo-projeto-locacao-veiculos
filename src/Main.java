import br.com.locadora.locacao.Locacao;
//import br.com.locadora.locacao.Retirada;
import br.com.locadora.locacao.Simulacao;
import br.com.locadora.pessoa.Cliente;
import br.com.locadora.pessoa.Pessoa;
import br.com.locadora.veiculos.Carro;
import br.com.locadora.veiculos.Opcionais;
import br.com.locadora.veiculos.TipoCombustivel;
import br.com.locadora.veiculos.Veiculo;
import br.com.locadora.cadastro.CadastroVeiculos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Main {
	
	private static final int MAX = 100;
	private static String arquivo = "funcionarios.txt";
	private static Veiculo[] frota = new Veiculo[MAX];
	static CadastroVeiculos leituraEscrita = CadastroVeiculos.iniciaCadastroVeiculos();
	private static int indiceAtual = -1;

    public static void main(String[] args) {
/*
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
        }*/
    	
    		// carrega dados do arquivo para o vetor de funcionarios
    		carregaDados(arquivo);

    		int opcao = 0;
    		do {
    			opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção:\n" + "1. Cadastrar Veículo\n"
    					+ "2. Imprimir dados\n" + "3. Remover Veículo\n4. Sair"));
    			switch (opcao) {
    			case 1:
    				cadastraVeiculo();
    				break;
    			case 2:
    				imprimeDados();
    				break;
    			case 3:
    				// removeFuncionario();
    				break;
    			case 4:
    				System.out.println("Fim!");
    				break;

    			default:
    				System.out.println("Opção inválida!");
    				break;
    			}

    		} while (opcao != 4);

    	}

    	private static void carregaDados(String arquivo) {
    		try {
    			frota = leituraEscrita.leDados();
    		} catch (FileNotFoundException e) {
    			System.out.println("Erro: Arquivo não encontrado! " + e.getMessage());
    		} catch (IOException e) {
    			System.out.println("Erro durante a leitura do arquivo!" + e.getMessage());
    		}
    		// acerta o indiceAtual de acordo com o número de elementos no vetor
    		for (int i = 0; i < MAX && frota[i] != null; i++) {
    			indiceAtual++;
    		}
    	}

    	private static void imprimeDados() {
    		int i = 0;
    		System.out.println("------------------------");
    		while (i <= indiceAtual && frota[i] != null) {
    			System.out.println(frota[i].toString());
    			System.out.println("------------------------");
    			i++;
    		}
    	}

    	private static void cadastraVeiculo() {
    		if (indiceAtual + 1 < MAX) {
    			String placa = JOptionPane.showInputDialog("Digite a placa: ");
    			int km  = Integer.parseInt(JOptionPane.showInputDialog("Digite km: "));
    			String marca = JOptionPane.showInputDialog("Digite a marca: ");
    			String modelo = JOptionPane.showInputDialog("Digite o modelo: "); 
    			String c = JOptionPane.showInputDialog("Digite o tipo de combustivel: ");
    			TipoCombustivel combustivel = null;
    			Opcionais[] opcionais = {Opcionais.AR_CONDICIONADO, Opcionais.BANCOS_COURO};
    			
    			switch (c) {
				case "ETANOL":
					combustivel = TipoCombustivel.ETANOL;
					break;
				case "GASOLINA":
					combustivel = TipoCombustivel.GASOLINA;
					break;
				case "DIESEL":
					combustivel = TipoCombustivel.DIESEL;
					break;
				case "ELETRICO":
					combustivel = TipoCombustivel.ELETRICO;
					break;
				case "FLEX":
					combustivel = TipoCombustivel.FLEX;
			}
    			Veiculo novo = new Veiculo(placa, km, marca, modelo, combustivel, opcionais);
    			try {

    				leituraEscrita.salva(novo);
    				frota[++indiceAtual] = novo;
    		
    			} catch (IOException e) {
    				System.out.println("Erro no cadastro: " + e.getMessage());
    			}
    		} else {
    			System.out.println("Limite atingido!");
    		}
    	}
    
    
}