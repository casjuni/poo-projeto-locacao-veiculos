package exception;

@SuppressWarnings("serial")
public class funcionarioSemCadastroException extends Exception 
{

	public funcionarioSemCadastroException(String email)
	{
		super("Funcionario de email " + email + " nao possui cadastro.");
	}
}
