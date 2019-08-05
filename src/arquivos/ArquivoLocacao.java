package arquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.locadora.locacao.*;
import br.com.locadora.veiculos.Veiculo;
import exception.*;


public class ArquivoLocacao {
	private static int MAX_LOCACOES = 100;
	Locacao[] locacoes = new Locacao[MAX_LOCACOES]; 
	private String arquivo;
	
	public ArquivoLocacao(String arquivo) 
	{
		this.arquivo = arquivo;
	}

	public Locacao[] LerLocacao(Veiculo[] frota, int contagemFrota) throws Exception {
		String linha;
		int quant = 0; 

		BufferedReader bufferLeitura = new BufferedReader(new FileReader(arquivo));
		try 
		{

			linha = bufferLeitura.readLine();
			while (linha != null && quant < MAX_LOCACOES) 
			{
				String campos[] = linha.split(";");
				
				String codigoLocacao = campos[0];
				String cpf = campos[1];
				String placa = campos[2];
				String dataRetirada = campos[3];
				String dataDevolucao = campos[4];
				float valor = Float.parseFloat(campos[5]);
				int kmInicial = Integer.parseInt(campos[6]);
				int kmFinal = Integer.parseInt(campos[7]);


				int veiculo = 0;

				for(int i = 0; i < contagemFrota; i++) {
					if(frota[i].getPlaca().equals(placa))
						veiculo = i;
				}

				
				// veiculo tem que ser obj
				Locacao loc = new Simulacao(frota[veiculo], cpf, StringToDate(dataRetirada), StringToDate(dataDevolucao));
				loc.setValorLocacao(valor);
				loc.setKmInicial(kmInicial);
				loc.setKmFinal(kmFinal);


			    
				//locacoes[quant] = loc;
				quant++;
				linha = bufferLeitura.readLine();
			}
			if(quant == 0)
			{
				throw new semLocacoesException();
			}
			else System.out.println("As locacoes foram carregadas!");
		} 	
		finally 
		{
			bufferLeitura.close();
		}
		return locacoes;
	}

	public void salvarlocacao(Locacao loc) throws IOException  
	{
		salvarlocacao(loc, true);
	}
	public void salvarlocacao(Locacao loc, boolean msg) throws IOException 
	{
		BufferedWriter bufferEscrita = new BufferedWriter(new FileWriter(arquivo, true));
		try 
		{
			// codigolocacao, emailcliente, veiculo, data retirada, data devolucao, valor pago, km
			
			bufferEscrita.write(loc.getCodigoLocacao() + ";");
			bufferEscrita.write(loc.getCliente() + ";");
			bufferEscrita.write(loc.getPlaca() + ";");
			bufferEscrita.write(loc.getDataRetirada() + ";");
			bufferEscrita.write(loc.getDataDevolucao() + ";");
			bufferEscrita.write(loc.getValorLocacao() + ";");
			bufferEscrita.write(loc.getKmInicial() + ";");
			bufferEscrita.write(loc.getKmFinal() + ";");
			bufferEscrita.write(loc.getSituacao() + "\r\n" );
			
			if(msg == true)
			{
				System.out.println("Locacao " + loc.getCodigoLocacao() + " salva!");
			}	
		} 
		finally 
		{
			bufferEscrita.close();
		}
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
