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

public class ArquivoHistorico {
	private static int MAX_LINHAS = 10000;
	private static final String  arquivo = "historico.txt";
	
	
	public static String mostrarHistorico() throws FileNotFoundException, IOException {
		String linha;
		int quant = 0; 
		String concatenar = "";
		BufferedReader bufferLeitura = new BufferedReader(new FileReader(arquivo));
		try 
		{
			linha = bufferLeitura.readLine();
			while (linha != null && quant < MAX_LINHAS) {
				String campos[] = linha.split(";");
				// codigolocacao, emailcliente, veiculo, data retirada, data devolucao, valor pago, km
				concatenar = concatenar + "Codigo: " + campos[0] + ". Cliente: " + campos[1] +
				" Veiculo: " + campos[2] + ". Data retirada: " + campos[3] + ". Data devolucao: " +
				campos[4] + ". Valor pago: " + campos[5] + " KMs percorridos: " + campos[6] + "\n";
				
				
				quant++;
				linha = bufferLeitura.readLine();
			}
			if(quant == 0) concatenar = "Nenhuma locacao ate o momento";
		} 	
		finally 
		{
			bufferLeitura.close();
		}
		return concatenar;
	}
	public static String historicoCliente(String email) throws FileNotFoundException, IOException {
		String linha;
		int quant = 0; 
		int contar = 0;
		String concatenar = "";
		BufferedReader bufferLeitura = new BufferedReader(new FileReader(arquivo));
		try 
		{
			linha = bufferLeitura.readLine();
			while (linha != null && quant < MAX_LINHAS) {
				String campos[] = linha.split(";");
				if(campos[1].equals(email))
				{
					// codigolocacao, emailcliente, veiculo, data retirada, data devolucao, valor pago, km
					concatenar = concatenar + "Codigo: " + campos[0] + ". Cliente: " + campos[1] +
					" Veiculo: " + campos[2] + ". Data retirada: " + campos[3] + ". Data devolucao: " +
					campos[4] + ". Valor pago: " + campos[5] + " KMs percorridos: " + campos[6] + "\n";
					
					contar++;
				}
				quant ++;
				
				linha = bufferLeitura.readLine();
			}
			if(contar == 0) concatenar = "Nenhuma locacao ate o momento";
		} 	
		finally 
		{
			bufferLeitura.close();
		}
		return concatenar;
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
