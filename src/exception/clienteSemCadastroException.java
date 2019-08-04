package exception;

@SuppressWarnings("serial")
public class clienteSemCadastroException extends Exception 
{
	public clienteSemCadastroException(String email)
	{
		super("Cliente de email " + email + " nao possui cadastro.");
	}
}
