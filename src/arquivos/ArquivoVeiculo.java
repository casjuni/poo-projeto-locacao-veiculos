package arquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import br.com.locadora.veiculos.Opcionais;
import br.com.locadora.veiculos.TipoCombustivel;
import br.com.locadora.veiculos.Veiculo;
import exception.*;

public class ArquivoVeiculo {
	private static int MAX_VEICULOS = 100;
	Veiculo[] frota = new Veiculo[MAX_VEICULOS]; 
	private Opcionais[] itens = new Opcionais[4];
	private String arquivo;
	
	public ArquivoVeiculo(String arquivo) 
	{
		this.arquivo = arquivo;
	}

	public Veiculo[] lerVeh() throws Exception 
	{
		Veiculo[] frota = new Veiculo[MAX_VEICULOS]; 
		String linha;
		int quant = 0;
		BufferedReader bufferLeitura = new BufferedReader(new FileReader(arquivo));
		try {

			linha = bufferLeitura.readLine();
			while (linha != null && quant < MAX_VEICULOS) 
			{
				String campos[] = linha.split(";");
				int i = 0;

				String placa = campos[i++];
				int km = Integer.parseInt(campos[i++]);
				String marca = campos[i++];
				String modelo = campos[i++];
				TipoCombustivel combustivel = null;
				String descricao = campos[i++];
				switch (descricao) {
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
				Opcionais a = null, b = null, c = null, d = null;
						if (campos.length > i)
							a = opcional(campos[i++]);
						if (campos.length > i)
							b = opcional(campos[i++]);
						if (campos.length > i)
							c = opcional(campos[i++]);
						if (campos.length > i)
							d = opcional(campos[i++]);
						Opcionais itens[] = {a, b, c, d};
				
				Veiculo v = new Veiculo(placa, km, marca, modelo, combustivel, itens);
				frota[quant] = v;
				quant++;
				linha = bufferLeitura.readLine();
			}
			System.out.println("O arquivo foi lido e carregado para a memoria!");
		} finally {
			bufferLeitura.close();
		}
		return frota;
	}
	public void salvarveh(Veiculo veiculo) throws IOException {
		BufferedWriter bufferEscrita = new BufferedWriter(new FileWriter(arquivo, true));
		try {
			bufferEscrita.write(veiculo.getPlaca() + ";");
			bufferEscrita.write(veiculo.getKm() + ";");
			bufferEscrita.write(veiculo.getMarca() + ";");
			bufferEscrita.write(veiculo.getModelo() + ";");
			bufferEscrita.write(veiculo.getTipoCombustivel() + ";");
			bufferEscrita.write(veiculo.getOpcionais() + "\r\n");
			System.out.println("Veiculo " + veiculo.getPlaca() + " cadastrado!");
		} finally {
			bufferEscrita.close();
		}
	}	
	public static Opcionais opcional(String descricao) {
		switch (descricao) {
			case "AR_CONDICIONADO":
				return Opcionais.AR_CONDICIONADO;
			case "DIRECAO_HIDRAULICA":
				return Opcionais.DIRECAO_HIDRAULICA;
			case "VIDROS_ELETRICOS":
				return Opcionais.VIDROS_ELETRICOS;
			case "BANCOS_COURO":
				return Opcionais.BANCOS_COURO;
		}
		return null;
	}

	public void removerveh(Veiculo v) {
		File txtfile = new File(arquivo);
		txtfile.delete();
	}

}
