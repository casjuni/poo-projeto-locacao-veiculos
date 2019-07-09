package cadastro;
import java.util.GregorianCalendar;

public class Pessoa
{
	private class Endereco
	{
		String logradouro;
		int numero;
		String complemento;
		String cep;
		String cidade
		String estado;
	}
	
	private String nome;
	private String cpf;
	private String cnh;
	private Endereco endereco;
	private GregorianCalendar dataNasc;
	private String usuario;
	private String senha;
	
	public void setNome(String nome)
	{ this.nome = nome; }
	
	private static boolean validaCPF(String cpf)
	{
		int[] digitos = new int[11];
		int i, resto, soma = 0;
		for (i = 0; i < digitos.length; i++)
			digitos[i] = Integer.parseInt(cpf.charAt(i));
		for (i = 0; i < 9; i++)
			soma += digitos[i] * (i + 1);
		resto = (soma % 11 == 10 ? 0 : soma % 11);
		if (resto != digitos[9]) return false;
		else
		{
			soma = 0;
			for (i = 0; i <= 9; i++)
				soma += digitos[i] * i;
			resto = (soma % 11 == 10 ? 0 : soma % 11);
			return resto == digitos[10];
		}
	}
	
	public void setCPF(String cpf)
	{
		if(validaCPF()) this.cpf = cpf;
		else System.out.println("CPF inválido");
	}
	
	public void setCNH(String cnh)
	{ this.cnh = cnh; }
	
	public void setEndereco(String rua, int n, String comp, String cep, String cidade, String uf)
	{
		Endereco end = new Endereco();
		end.logradouro = rua;
		end.numero = n;
		end.complemento = comp;
		end.cep = cep;
		end.cidade = cidade;
		end.estado = uf;
		this.endereco = end;
	}
	
	public void setDataNasc(int dia, int mes, int ano)
	{ this.dataNasc = new GregorianCalendar(ano, mes, dia);	}
	
	public void setUser(Stting username)
	{ this.usuario = username; }
	
	public void setSenha(String senha)
	{ this.senha = senha; }
	
	public String nome()
	{ return nome; }
	
	public String CPF()
	{ return cpf; }
	
	public String CNH()
	{ return cnh; }
	
	public Endereco endereco()
	{ return endereco; }
	
	public GregorianCalendar dataNasc()
	{ return dataNasc; }
	
	public abstract boolean funcionario();

}