package exception;

@SuppressWarnings("serial")
public class semFuncionariosException extends Exception 
{
	public semFuncionariosException()
	{
		super("Nao ha funcionarios cadastrados!");

	}
}