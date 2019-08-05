
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.locadora.veiculos.*;
import br.com.locadora.pessoa.*;
import br.com.locadora.locacao.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JOptionPane;

import arquivos.*;

import exception.*;


@SuppressWarnings("unused")
public class Main 
{

	private static String codigo_padrao = "bccmelhorcurso"; // senha pra se cadastrar como funcionario
	
	private static final int MAX_FUNCS = 10;
	private static final int MAX_CLIENTES = 1000;
	private static final int MAX_VEICULOS = 100;
	private static final int MAX_LOCACOES = 1000;
	
	private static int ContagemFunc = -1;
	private static int ContagemCliente = -1;
	private static int ContagemVeh = -1;
	private static int ContagemSimulacao = -1;

	private static Funcionario[] funcionarios = new Funcionario[MAX_FUNCS];
	private static Cliente[] clientes = new Cliente[MAX_CLIENTES];
	private static Veiculo[] frota = new Veiculo[MAX_VEICULOS];
	private static Simulacao[] simulacoes = new Simulacao[MAX_LOCACOES];
	private static Locacao[] locacoes = new Locacao[MAX_LOCACOES];
	
	private static String arquivo_funcs = "funcionarios.txt";
	private static String arquivo_clientes = "clientes.txt";
    private static String arquivo_locacao = "locacao.txt";
    private static String arquivo_veiculos = "veiculos.txt";
	
	private static ArquivoFunc FuncFile = new ArquivoFunc(arquivo_funcs);
	private static ArquivoCliente ClienteFile = new ArquivoCliente(arquivo_clientes);
    private static ArquivoLocacao LocacaoFile = new ArquivoLocacao(arquivo_locacao);
    private static ArquivoVeiculo VehFile = new ArquivoVeiculo(arquivo_veiculos);

	
	
    public static void main(String[] args) throws Exception  {

    	carregarFuncs();
    	carregarClientes();
    	carregarVeiculos();
        carregarLocacoes();
    	
		int opcao;
		
		do 
		{
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Seja bem vindo a Locadora de veiculos\nDigite uma opcao:\n" + "1. Sou cliente\n"
					+ "2. Sou funcionario\n3. Sair"));
			switch (opcao) 
			{
				case 1:
					opcoesCliente();
					break;
				case 2:
					opcoesFuncionario();
					break;
				case 3:
					System.out.println("Voce decidiu sair.");
					break;
	
				default:
					System.out.println("Opção inválida!");
					break;
			}
		}	while (opcao != 3);
	}
   
//_______________________________  SISTEMA DE CLIENTE ____________________________________ //
    
    private static void opcoesCliente() throws Exception
    {
    	int escolha = 0;
    	escolha = Integer.parseInt(JOptionPane.showInputDialog("Digite uma opcao:\n" + "1. Desejo logar na minha conta\n"
				+ "2. Desejo cadastrar uma conta\n3. Voltar"));
		
    	do 
    	{
			switch(escolha)
			{
				case 1:
				{
					logarCliente();
					break;
				}
				case 2:
				{
					cadastrarCliente();
					break;
				}
				case 3:
					break;
					
				default: throw new opcaoInvalida();
			}	
		} while (escolha < 1 || escolha > 3);
    }
    
    private static void logarCliente() throws clienteSemCadastroException, Exception
    {
    	String email = JOptionPane.showInputDialog("Digite o e-mail: ");
    	String senha = JOptionPane.showInputDialog("Digite a senha: ");
    	
    	int tentativas = 5;
    	boolean logou = false;
    	boolean achouemail = false;
    	
    	do
    	{
	    	for(int i = 0; i < MAX_CLIENTES; i++)
	    	{
	    		if(clientes[i] != null)
	    		{
		    		Cliente c = clientes[i];
		    		if(c.email() != null && email != null && c.email().equals(email))
		    		{
		    			achouemail = true;	
		    			if(c.senha() != null && senha != null && c.senha().equals(senha))  
		    			{
		    				logou = true;
		    				System.out.println("Voce logou com sucesso!");
		    				telaCliente(email);
		    				break;
		    			}
		    		}
	    		}
	    	}
	    	if(logou == false && achouemail == true)
	    	{
	    		tentativas --;
	    		senha = JOptionPane.showInputDialog("Senha incorreta!\n" + tentativas + " restantes.\nDigite a senha: ");
	    	}
	    	else if(achouemail == false)
	    	{
	    		email = JOptionPane.showInputDialog("E-mail incorreto!\nDigite novamente: ");
	        	senha = JOptionPane.showInputDialog("Digite a senha: ");
	    	}
    	} 
    	while(logou == false && tentativas > 0);
        	
    }
    
    private static void telaCliente(String email) throws clienteSemCadastroException, Exception
    {
    	int opcao;
    	do 
		{
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção:\n" + "1. Alugar um veiculo\n"
					+ "2. Ver historico\n" + "3. Deletar conta\n4. Sair"));
			
			switch (opcao) 
			{
				case 1:
					telaLocacao(email);
					break;
				case 2:
					// ver historico
					break;
				case 3:
				{
					int escolher = 0;
					while(escolher != 1 && escolher != 2)
					{
						escolher = Integer.parseInt(JOptionPane.showInputDialog("Deseja deletar sua conta?\nTodos seus dados serao apagados.\n" + "1. Sim\n"
								+ "2. Cancelar\n"));
					}
					if(escolher == 1) // deletar
					{
						removeCliente(email);
						opcao = 4;
					}
					break;
				}
				case 4:
					System.out.println("Voce saiu!");
					break;
	
				default:
					System.out.println("Opcao invalida!");
					break;
			}
		}	while (opcao != 4);

    }

    private static void cadastrarCliente() throws idadeMinimaException, IOException, limiteClientesException, idadeMinimaException
    {
    	String nome = JOptionPane.showInputDialog("Digite o nome: ");
    	String email = JOptionPane.showInputDialog("Digite o e-mail: ");
    	while(validarEmail(email) == false)
    	{
    		email = JOptionPane.showInputDialog("E-mail invalido\nSeu e-mail deve conter . e @.\nDigite um e-mail: ");
    	}
    	while(emailExistente(email) == true)
    	{
    		email = JOptionPane.showInputDialog("Esse email já está em uso.\nDigite um novo e-mail: ");
    	}
    	String senha = JOptionPane.showInputDialog("Digite uma senha:");
		String dataNasc = JOptionPane.showInputDialog("Digite a data de nascimento (dd/mm/aaaa): ");
		while(formatoInvalido(dataNasc) == true)
		{
			dataNasc = JOptionPane.showInputDialog("Digite a data de nascimento (dd/mm/aaaa): ");
		}
		if(idadeValida(dataNasc) == false)
		{
			throw new idadeMinimaException();
		}
		String cpf = JOptionPane.showInputDialog("Digite o seu CPF (sem pontos e hifen): ");
		while(!validaCPF(cpf))
		{
			cpf = JOptionPane.showInputDialog("CPF Invalido!\nDigite o seu CPF (sem pontos e hifen): ");
		}
		String cep = JOptionPane.showInputDialog("Digite o seu CEP: ");
		String estado = JOptionPane.showInputDialog("Digite o seu estado: ");
		String cidade = JOptionPane.showInputDialog("Digite a sua cidade: ");
		String endereco = JOptionPane.showInputDialog("Digite o seu endereço: ");
		String complemento = JOptionPane.showInputDialog("Digite o seu complemento: ");
		int numero = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero da residencia: "));
		String celular = JOptionPane.showInputDialog("Digite o numero de seu celular: "); 
		String cnh = JOptionPane.showInputDialog("Digite o numero de sua CNH: "); 
    	Cliente cliente = new Cliente(nome, email, senha, dataNasc, cpf, cep, cidade, estado, endereco, complemento, numero, celular, cnh);
        
    	if(ContagemCliente + 1 < MAX_CLIENTES)
    	{
			try 
			{
	
				ClienteFile.salvarcliente(cliente);
				clientes[++ContagemCliente] = cliente;
			} 
			catch (IOException e) 
			{
				System.out.println("Ocorreu um erro no cadastro: " + e.getMessage());
			}
		}
    	else throw new limiteClientesException(MAX_CLIENTES);
    
    }

    private static void carregarClientes() 
    {
		try 
		{
			clientes = ClienteFile.LerClientes();
		} catch (FileNotFoundException e) {
			System.out.println("Erro: Arquivo não encontrado! " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro durante a leitura do arquivo!" + e.getMessage());
		} catch(semClientesException e) {
			System.out.println("Nao ha clientes cadastrados");
		}
		for (int i = 0; i < MAX_CLIENTES && clientes[i] != null; i++) 
		{
			if(ContagemCliente == -1)
				ContagemCliente = 0;

			ContagemCliente++;
		}
	}
    
    public static int cbusca(String email)
	{
		for(int c = 0; c < MAX_CLIENTES; c++)
		{
			if(clientes[c] != null && clientes[c].email().equals(email))
			{
				return c;
			}
		}
		return -1;
		
	}

    private static void removeCliente(String email) throws clienteSemCadastroException
	{
		int pos = cbusca(email);
		
		if(pos == -1)
		{
			throw new clienteSemCadastroException(email);
		}
		else 
		{
			try 
			{
				ClienteFile.removercliente(clientes[pos]);
				clientes[pos] = null;
				
				// reestruturando o vetor para eliminar "slots" vazios no meio
				int contar = 0;
				for(int a = pos; a < MAX_CLIENTES; a++)
				{
					if(clientes[a] == null)
					{
						for(int b=a+1; b < MAX_CLIENTES; b++)
						{
							if(clientes[b] != null)
							{
								clientes[a] = clientes[b];
								clientes[b] = null;
								break;
							}
						}
					}
				}
				// reescrevendo o arquivo
				for(int c = 0; c < MAX_CLIENTES; c++)
				{
					if(clientes[c] != null)
					{
						contar ++;
						try
					 	{
							ClienteFile.salvarcliente(clientes[c], false);
					
						}
						catch (IOException e) {
							System.out.println("Erro no cadastro: " + e.getMessage());
						}
					}
				}	
				System.out.println("Cliente de email " + email + " foi removido com sucesso!");
				if(contar > 0) carregarClientes();
			}
			catch(semClientesException e)
			{
				System.out.println("Nenhum cliente cadastrado!");
			} 
			catch (Exception e) {
				System.out.println("Erro ao remover cliente");
			}
		}
	}

	private static Cliente procurarCliente(String email) throws clienteSemCadastroException
	{

		for(int i = 0; i <= MAX_CLIENTES; i++) {
			if(clientes[i].email().equals(email)) {
				return clientes[i];
			}
		}

		throw new clienteSemCadastroException(email);
	}

    //___________________________ SISTEMA DE FUNCIONARIO _________________________ //
    private static void opcoesFuncionario()  throws Exception
    {
    	int escolha = 0;
    	escolha = Integer.parseInt(JOptionPane.showInputDialog("Digite uma opcao:\n" + "1. Desejo logar na minha conta\n"
				+ "2. Desejo cadastrar uma conta\n3. Voltar"));
		
    	do 
    	{
			switch(escolha)
			{
				case 1:
				{
					logarFuncionario();
					break;
				}
				case 2:
				{
					cadastrarFuncionario();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new opcaoInvalida();
				}
			}
    	} while (escolha < 1 || escolha > 3);

    }
    
    private static void logarFuncionario() throws Exception
    {
    	String email = JOptionPane.showInputDialog("Digite o e-mail: ");
    	String senha = JOptionPane.showInputDialog("Digite a senha: ");
    	
    	int tentativas = 5;
    	boolean logou = false;
    	boolean achouemail = false;
    	
    	do
    	{
	    	for(int i = 0; i < MAX_FUNCS; i++)
	    	{
	    		if(funcionarios[i] != null)
	    		{
		    		Funcionario f = funcionarios[i];
		    		if(f.email() != null && email != null && f.email().equals(email))
		    		{
		    			achouemail = true;	
		    			if(f.senha() != null && senha != null && f.senha().equals(senha))  
		    			{
		    				logou = true;
		    				System.out.println("Voce logou com sucesso!");
		    				telaFuncionario();
		    				break;
		    			}
		    		}
	    		}
	    	}
	    	if(logou == false && achouemail == true)
	    	{
	    		tentativas --;
	    		senha = JOptionPane.showInputDialog("Senha incorreta!\n" + tentativas + " restantes.\nDigite a senha: ");
	    	}
	    	else if(achouemail == false)
	    	{
	    		email = JOptionPane.showInputDialog("E-mail incorreto!\nDigite novamente: ");
	        	senha = JOptionPane.showInputDialog("Digite a senha: ");
	    	}
    	} 
    	while(logou == false && tentativas > 0);
    }
    
    private static void telaFuncionario() throws Exception
    {
    	int opcao;
    	int codigoSimulacao;
    	do 
		{
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção:\n" + "1. Gerenciar veiculos\n"
					+ "2. Ver historico de locacoes\n" + "3. Registrar retirada" + "4. Registrar devolução" + "5. Sair"));
			
			switch (opcao) 
			{
				case 1:
					gerenciarVeiculos();
					break;
				case 2:
					JOptionPane.showMessageDialog(null, ArquivoHistorico.mostrarHistorico());
					break;
                case 3:
                    codigoSimulacao = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da simulação: "));
                    String dataRetirada = JOptionPane.showInputDialog("Digite a data de retirada (dd/mm/yyyy): ");
                    realizarRetirada(codigoSimulacao, StringToDate(dataRetirada));
                    break;
                case 4:
                    codigoSimulacao = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da simulação: "));
                    String dataDevolucao = JOptionPane.showInputDialog("Digite a data da devolução (dd/mm/yyyy): ");
                    realizarRetirada(codigoSimulacao, StringToDate(dataDevolucao));
                    break;
				case 5:
					System.out.println("Voce decidiu sair!");
					break;
	
				default:
					System.out.println("Opcao invalida!");
					break;
			}
		}	while (opcao != 5);
    }
    private static void gerenciarVeiculos()
    {
    	int opcao = 0;
		do 
		{
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção:\n" + "1. Cadastrar Veículo\n"
					+ "2. Imprimir dados\n" + "3. Remover Veículo\n4. Sair"));
			switch (opcao) 
			{
				case 1:
					cadastrarVeiculo();
					break;
				case 2:
					imprimirVeiculos();
					break;
				case 3:
					// remover veiculo;
					break;
				case 4:
					System.out.println("Voce saiu!");
					break;
				default:
					System.out.println("Opcao invalida!");
					break;
			}
		} while (opcao != 4);
    }
    private static void cadastrarFuncionario() throws acessoNegadoException, IOException, limiteFuncException
    {
    	String code = JOptionPane.showInputDialog("Digite o codigo de acesso:");
    	
    	if(!code.equals(codigo_padrao))
		{
    		throw new acessoNegadoException();
		}
    	String nome = JOptionPane.showInputDialog("Digite o nome: ");
    	String email = JOptionPane.showInputDialog("Digite o e-mail: ");
    	while(validarEmail(email) == false)
    	{
    		email = JOptionPane.showInputDialog("E-mail invalido\nSeu e-mail deve conter . e @.\nDigite um e-mail: ");
    	}
    	while(emailExistente(email) == true)
    	{
    		email = JOptionPane.showInputDialog("Esse email já está em uso.\nRecupere sua senha ou digite um novo e-mail: ");
    	}
    	String senha = JOptionPane.showInputDialog("Digite uma senha:");
    	
    	Funcionario funcionario = new Funcionario(nome, email, senha);
    	System.out.println(funcionario.imprimir());
    	if(ContagemFunc + 1 < MAX_FUNCS)
    	{
			try 
			{
	
				FuncFile.salvar(funcionario);
				funcionarios[++ContagemFunc] = funcionario;
			} 
			catch (IOException e) 
			{
				System.out.println("Ocorreu um erro no cadastro: " + e.getMessage());
			}
		}
    	else throw new limiteFuncException(MAX_FUNCS);
    	
    }
    
    private static void removeFuncionario(String email) throws funcionarioSemCadastroException
	{
		int pos = fbusca(email);
		
		if(pos == -1)
		{
			throw new funcionarioSemCadastroException(email);
		}
		else 
		{
			try 
			{
				FuncFile.removerfuncionario(funcionarios[pos]);
				funcionarios[pos] = null;
				
				// reestruturando o vetor para eliminar "slots" vazios no meio
				
				for(int a = pos; a < MAX_FUNCS; a++)
				{
					if(funcionarios[a] == null)
					{
						for(int b=a+1; b < MAX_FUNCS; b++)
						{
							if(funcionarios[b] != null)
							{
								funcionarios[a] = funcionarios[b];
								funcionarios[b] = null;
								break;
							}
						}
					}
				}
				// reescrevendo o arquivo
				for(int f = 0; f < MAX_FUNCS; f++)
				{
					if(funcionarios[f] != null)
					{
						try
						{
							FuncFile.salvar(funcionarios[f], false);
					
						}
						catch (IOException e) {
							System.out.println("Erro no cadastro: " + e.getMessage());
						}
					}
				}	
				System.out.println("Funcionario de email " + email + " foi removido com sucesso!");
				carregarFuncs();
			}
			catch(semFuncionariosException e)
			{
				System.out.println("Nenhum funcionario cadastrado!");
			} 
			catch (Exception e) {
				System.out.println("Erro ao remover funcionario");
			}
		}
	}
    
    public static int fbusca(String email)
	{
		for(int f = 0; f < MAX_FUNCS; f++)
		{
			if(funcionarios[f] != null && funcionarios[f].email().equals(email))
			{
				return f;
			}
		}
		return -1;
		
	}
    private static void carregarFuncs() 
    {
		try 
		{
			funcionarios = FuncFile.LerFuncionarios();
		} catch (FileNotFoundException e) {
			System.out.println("Erro: Arquivo não encontrado! " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro durante a leitura do arquivo!" + e.getMessage());
		} catch(semFuncionariosException e) {
			System.out.println("Nao ha funcionarios cadastrados");
		}
		for (int i = 0; i < MAX_FUNCS && funcionarios[i] != null; i++) 
		{
			ContagemFunc ++;
		}
	}
    
//______________________________ SISTEMA DE VEICULOS _____________________________ //
    private static void carregarVeiculos()  throws Exception
    {
		try 
		{
			frota = VehFile.lerVeh();
		} catch (FileNotFoundException e) {
			System.out.println("Erro: Arquivo não encontrado! " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro durante a leitura do arquivo!" + e.getMessage());
		}
		for (int i = 0; i < MAX_VEICULOS && frota[i] != null; i++) {

			if(ContagemVeh == -1)
				ContagemVeh = 0;

			ContagemVeh++;
		}
	}
    
    private static void imprimirVeiculos() 
    {
		int i = 0;
		System.out.println("------------------------");
		while (i <= ContagemVeh && frota[i] != null) 
		{
			System.out.println(frota[i].toString());
			System.out.println("------------------------");
			i++;
		}
	}

	private static void cadastrarVeiculo() 
	{
		if (ContagemVeh + 1 < MAX_VEICULOS) 
		{
			String placa = JOptionPane.showInputDialog("Digite a placa: ");
			int km  = Integer.parseInt(JOptionPane.showInputDialog("Digite km: "));
			String marca = JOptionPane.showInputDialog("Digite a marca: ");
			String modelo = JOptionPane.showInputDialog("Digite o modelo: "); 
			String c = JOptionPane.showInputDialog("Digite o tipo de combustivel: ");
			TipoCombustivel combustivel = null;
			Opcionais[] opcionais = {Opcionais.AR_CONDICIONADO, Opcionais.BANCOS_COURO};
			
			switch (c) 
			{
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
			try 
			{
				VehFile.salvarveh(novo);
				frota[++ContagemVeh] = novo;
		
			} 
			catch (IOException e) {
				System.out.println("Erro no cadastro: " + e.getMessage());
			}
		} 
		else System.out.println("Limite atingido!");
	}

	public static int vbusca(String placa)
	{
		for(int v = 0; v < MAX_VEICULOS; v++)
		{
			if(frota[v] != null && frota[v].getPlaca().equals(placa))
			{
				return v;
			}
		}
		return -1;

	}

    private static void removeVeh(String placa) throws Exception
    {
        int pos = vbusca(placa);

        if(pos == -1)
        {
            throw new veiculoInexistenteException(placa);
        }
        else
        {
            try
            {
                VehFile.removerveh(frota[pos]);
                frota[pos] = null;

                // reestruturando o vetor para eliminar "slots" vazios no meio

                for(int a = pos; a < MAX_VEICULOS; a++)
                {
                    if(frota[a] == null)
                    {
                        for(int b=a+1; b < MAX_VEICULOS; b++)
                        {
                            if(frota[b] != null)
                            {
                                frota[a] = frota[b];
                                frota[b] = null;
                                break;
                            }
                        }
                    }
                }
                int contar = 0;
                // reescrevendo o arquivo
                for(int v = 0; v < MAX_VEICULOS; v++)
                {
                    if(frota[v] != null)
                    {
                        contar ++;
                        try
                        {
                            VehFile.salvarveh(frota[v]);
                        }
                        catch (IOException e) {
                            System.out.println("Erro no cadastro: " + e.getMessage());
                        }
                    }
                }
                if(contar !=0) carregarVeiculos();
            }
            catch (Exception e) {
                System.out.println("Erro ao remover veiculo");
            }
        }
    }

	private static Veiculo procurarVeiculo(String placa) throws veiculoSemCadastroException{

    	for(int i = ContagemVeh; i <= MAX_VEICULOS; i++) {
    		if(frota[i].getPlaca().equals(placa)) {
    			return frota[i];
			}
		}

    	throw new veiculoSemCadastroException();
	}
//_________________________________ SISTEMA DE LOCACAO ___________________________________ //
	private static void telaLocacao(String email) throws Exception{

		int opcao;
		int codigoSimulacao;
		do
		{
			opcao = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção:\n" + "1. Carregar simulação\n"
					+ "2. Nova simulação\n" + "3. Efetivar operação" + "4. Sair"));

			switch (opcao)
			{
				case 1:
					codigoSimulacao = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da simulação:\n Caso digite 0, será exibido todas as suas simulações:"));
					if(codigoSimulacao != 0) {
						carregarSimulacaoPorCodigo(codigoSimulacao);
					} else {
						carregarSimulacoesPorCliente(email);
					}
					break;
				case 2:
					novaSimulacao(email);
					break;
				case 3:
                    codigoSimulacao = Integer.parseInt(JOptionPane.showInputDialog("Digite o código da simulação:\n "));
					efetivarOperacao(codigoSimulacao);
					break;
                case 4:
                    System.out.println("Voce decidiu sair!");
                    break;
				default:
					System.out.println("Opcao invalida!");
					break;
			}
		}	while (opcao != 4);
	}

	public static void carregarSimulacoesPorCliente(String email) throws simulacaoNaoEncontradaException, clienteSemCadastroException{

		boolean simulacaoEncontrada = false;
		boolean clienteEncontrado = false;
		String cpf = "";

		for(int i = 0; i < ContagemCliente; i++) {
			if(clientes[i].email().equals(email)) {
				cpf = clientes[i].CPF();
				clienteEncontrado = true;
			}
		}

		if(!clienteEncontrado)
			throw new clienteSemCadastroException(email);

		for(int i = 0; i <= MAX_LOCACOES; i++) {
			if(simulacoes[i].getCliente() == cpf) {
				System.out.println(simulacoes[i].exibirSimulacao());
				simulacaoEncontrada = true;
			}
		}

		if(!simulacaoEncontrada)
			throw new simulacaoNaoEncontradaException();

	}

	public static void carregarSimulacaoPorCodigo(int codigoSimulacao) throws simulacaoNaoEncontradaException{

    	boolean simulacaoEncontrada = false;

		for(int i = 0; i <= MAX_LOCACOES; i++) {
			if(simulacoes[i].getCodigoLocacao() == codigoSimulacao) {
				System.out.println(simulacoes[i].exibirSimulacao());
				simulacaoEncontrada = true;
			}
		}

		if(!simulacaoEncontrada)
			throw new simulacaoNaoEncontradaException();

	}

	public static void novaSimulacao(String email) throws Exception{

		if (ContagemSimulacao + 1 < MAX_LOCACOES)
		{
			Cliente cliente = procurarCliente(email);
			String placa = JOptionPane.showInputDialog("Digite a placa: ");

			int i = vbusca(placa);

			String dataRetirada = JOptionPane.showInputDialog("Digite a data de retirada (dd/mm/yyyy): ");
			String dataDevolucao = JOptionPane.showInputDialog("Digite a data de devolucao (dd/mm/yyyy): ");

			Simulacao nova = new Simulacao(frota[i], cliente.CPF(), StringToDate(dataRetirada), StringToDate(dataDevolucao));

			try
			{
				// Alterar
				LocacaoFile.salvarlocacao(nova);
				simulacoes[++ContagemSimulacao] = nova;

			}
			catch (IOException e) {
				System.out.println("Erro no cadastro: " + e.getMessage());
			}
		}
		else System.out.println("Limite atingido!");

	}

	public static void efetivarOperacao(int codigoSimulacao) throws simulacaoNaoEncontradaException{

        boolean simulacaoEncontrada = false;

        for(int i = 0; i <= MAX_LOCACOES; i++) {
            if(simulacoes[i].getCodigoLocacao() == codigoSimulacao) {
                simulacaoEncontrada = true;
                simulacoes[i].setOperacaoEfetivada(true);
            }
        }

        if(!simulacaoEncontrada)
            throw new simulacaoNaoEncontradaException();

    }

    public static void realizarRetirada(int codigoSimulacao, Date dataRetirada) throws simulacaoNaoEncontradaException {

        boolean simulacaoEncontrada = false;

        for(int i = 0; i <= MAX_LOCACOES; i++) {
            if(simulacoes[i].getCodigoLocacao() == codigoSimulacao) {
                simulacaoEncontrada = true;
                simulacoes[i].setDataRetirada(dataRetirada);
            }
        }

        if(!simulacaoEncontrada)
            throw new simulacaoNaoEncontradaException();

    }

    public static void realizarDevolucao(int codigoSimulacao, Date dataDevolucao) throws simulacaoNaoEncontradaException {

        boolean simulacaoEncontrada = false;

        for(int i = 0; i <= MAX_LOCACOES; i++) {
            if(simulacoes[i].getCodigoLocacao() == codigoSimulacao) {
                simulacaoEncontrada = true;
                simulacoes[i].setDataDevolucao(dataDevolucao);
            }
        }

        if(!simulacaoEncontrada)
            throw new simulacaoNaoEncontradaException();

    }

	private static void carregarLocacoes() throws Exception
	{
		try
		{
			locacoes = LocacaoFile.LerLocacao(frota, ContagemVeh);
		} catch (FileNotFoundException e) {
			System.out.println("Erro: Arquivo não encontrado! " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro durante a leitura do arquivo!" + e.getMessage());
		} catch(semClientesException e) {
			System.out.println("Nao ha locações/simulações cadastrados");
		}
		for (int i = 0; i < MAX_LOCACOES && locacoes[i] != null; i++)
		{
			if(ContagemSimulacao == -1)
				ContagemSimulacao = 0;

			ContagemSimulacao++;
		}
	}

//_________________________________ VALIDAÇÕES ___________________________________ //
    
    public static boolean idadeValida(String dataNasc)
    {
    	String[] dados = new String[3];
		dados = dataNasc.split ("/");
		Calendar c = GregorianCalendar.getInstance();
		int anoatual = 0;
		anoatual = c.get(Calendar.YEAR);
		int conta = anoatual - Integer.parseInt(dados[2]);
		return (conta >= 18);
    }
    
    public static boolean formatoInvalido (String dataNasc)
    {
    	if(dataNasc.indexOf('/') == 2 && dataNasc.indexOf('/', 4) == 5) return false;
    	return true;
    }
    public static boolean validarEmail(String email)
    {
    	return (email.indexOf('@') > 0 && email.indexOf('.') > 0);    
    }
    
    public static boolean emailExistente(String email)
    {
    	
    	for(int i = 0; i < MAX_FUNCS; i++)
    	{
    		if(funcionarios[i] != null)
    		{
    			Funcionario f = funcionarios[i];
    			if(f.email() != null && email != null && f.email().equals(email))
    			{
    				return true;
    			}
    		}
    	}
		for(int j = 0; j < MAX_CLIENTES; j++)
    	{
    		if(clientes[j] != null)
    		{
    			Cliente c = clientes[j];
    			if(c.email() != null && email != null && c.email().equals(email))
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    private static boolean validaCPF(String cpf) 
    {
        if(cpf.length() != 11) return false; 
        int[] vetor_cpf = new int[11];
        long cpflong = Long.parseLong(cpf);
        int digit_1 = 0, digit_2 = 0, x, k; 
        long num = 10000000000L, resto;
        	
        for (x = 0; x < 11; x++)
        {        
            resto= (cpflong%num);
            k = (int)(cpflong/num);
            num = num/10;
            cpflong = resto;         
            vetor_cpf[x] = k;     
        }   
        k = 10;
        for (x = 0; x < 9; x++)
        {            
        	digit_1 = digit_1 + vetor_cpf[x]*k;
        	k--;            
        }
        k = 11;        
        for (x = 0; x < 10; x++)
        {            
            digit_2 = digit_2 + vetor_cpf[x]*k;           
            k --;
        } 
        digit_1 = digit_1*10%11; 
        digit_2 = digit_2*10 %11;
        digit_1 = (digit_1 == 10) ? 0 : digit_1;
        digit_2 = (digit_2 == 10) ? 0 : digit_2;
        
        return ((digit_1 == vetor_cpf[9]) && (digit_2 == vetor_cpf[10]));
    }
    
    private static Date StringToDate(String date) 
    {
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