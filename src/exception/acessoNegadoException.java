package exception;

@SuppressWarnings("serial")
public class acessoNegadoException extends Exception 
{
	public acessoNegadoException()
	{
		super("Codigo de acesso invalido! Permissao negada");
	}
}
