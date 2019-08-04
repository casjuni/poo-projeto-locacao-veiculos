package arquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.locadora.pessoa.*;
import exception.*;

public class ArquivoFunc {
	private static int MAX_FUNCS = 10;
	Funcionario[] funcs = new Funcionario[MAX_FUNCS]; 
	private String arquivo;
	
	public ArquivoFunc(String arquivo) 
	{
		this.arquivo = arquivo;
	}

	public Funcionario[] LerFuncionarios() throws FileNotFoundException, IOException, semFuncionariosException {
		String linha;
		int quant = 0; 

		BufferedReader bufferLeitura = new BufferedReader(new FileReader(arquivo));
		try 
		{

			linha = bufferLeitura.readLine();
			while (linha != null && quant < MAX_FUNCS) {
				String campos[] = linha.split(";");

				String nome = campos[0];
				String email = campos[1];
				String senha = campos[2];
				
				Funcionario funcionario = new Funcionario(nome, email, senha);
				funcs[quant] = funcionario;
				quant++;
				linha = bufferLeitura.readLine();
			}
			if(quant == 0)
			{
				throw new semFuncionariosException();
			}
			else System.out.println("Os funcionarios foram carregados!");
		} 	
		finally 
		{
			bufferLeitura.close();
		}
		return funcs;
	}

	public void salvar(Funcionario funcionario) throws IOException  
	{
		salvar(funcionario, true);
	}
	public void salvar(Funcionario funcionario, boolean msg) throws IOException 
	{
		BufferedWriter bufferEscrita = new BufferedWriter(new FileWriter(arquivo, true));
		try 
		{
			bufferEscrita.write(funcionario.nome() + ";");
			bufferEscrita.write(funcionario.email() + ";");
			bufferEscrita.write(funcionario.senha() + "\r\n");
			
			if(msg == true)
			{
				System.out.println("Funcionarix " + funcionario.nome() + " cadastradx!");
			}	
		} 
		finally 
		{
			bufferEscrita.close();
		}
	}

	public void removerfuncionario(Funcionario funcionario)  throws semFuncionariosException 
	{
		File txtfile = new File(arquivo);
		txtfile.delete();
	}

}
