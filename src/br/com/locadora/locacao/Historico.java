package br.com.locadora.locacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.com.locadora.pessoa.*;
import exception.*;

public class Historico {
	private static int MAX_CLIENTES = 1000;
	Cliente[] clientes = new Cliente[MAX_CLIENTES]; 
	private String arquivo;
	
	public ArquivoCliente(String arquivo) 
	{
		this.arquivo = arquivo;
	}

	public Cliente[] LerClientes() throws FileNotFoundException, IOException, semClientesException {
		String linha;
		int quant = 0; 

		BufferedReader bufferLeitura = new BufferedReader(new FileReader(arquivo));
		try 
		{

			linha = bufferLeitura.readLine();
			while (linha != null && quant < MAX_CLIENTES) {
				String campos[] = linha.split(";");

				String nome = campos[0];
				String email = campos[1];
				String senha = campos[2];
				String dataNasc = campos[3];
				String cpf = campos[4];
				String cep = campos[5];
				String cidade = campos[6];
				String estado = campos[7];
				String endereco = campos[8];
				String complemento = campos[9];
				int numero = Integer.parseInt(campos[10]);
				String celular = campos[11];
				String CNH = campos[12];
				Cliente c = new Cliente(nome, email, senha, dataNasc, cpf, cep, cidade, estado, endereco, complemento, numero, celular, CNH);
		   
				clientes[quant] = c;
				quant++;
				linha = bufferLeitura.readLine();
			}
			if(quant == 0)
			{
				throw new semClientesException();
			}
			else System.out.println("Os clientes foram carregados com sucesso!");
		} 	
		finally 
		{
			bufferLeitura.close();
		}
		return clientes;
	}

	public void salvarcliente(Cliente cliente) throws IOException  
	{
		salvarcliente(cliente, true);
	}
	public void salvarcliente(Cliente cliente, boolean msg) throws IOException 
	{
		BufferedWriter bufferEscrita = new BufferedWriter(new FileWriter(arquivo, true));
		try 
		{
			bufferEscrita.write(cliente.nome() + ";");
			bufferEscrita.write(cliente.email() + ";");
			bufferEscrita.write(cliente.senha() + ";");
			bufferEscrita.write(cliente.dataNasc() + ";");
			bufferEscrita.write(cliente.CPF() + ";");
			bufferEscrita.write(cliente.CEP() + ";");
			bufferEscrita.write(cliente.cidade() + ";");
			bufferEscrita.write(cliente.estado() + ";");
			bufferEscrita.write(cliente.complemento() + ";");
			bufferEscrita.write(cliente.endereco() + ";");	
			bufferEscrita.write(cliente.numero() + ";");
			bufferEscrita.write(cliente.celular() + ";");
			bufferEscrita.write(cliente.CNH() + "\r\n");
			
			   
			if(msg == true)
			{
				System.out.println("Cliente " + cliente.nome() + " de email " + cliente.email() + " cadastrado!");
			}	
		} 
		finally 
		{
			bufferEscrita.close();
		}
	}

	public void removercliente(Cliente cliente)  throws semClientesException 
	{
		File txtfile = new File(arquivo);
		txtfile.delete();
	}

}
