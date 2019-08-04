package exception;

@SuppressWarnings("serial")
public class semClientesException extends Exception 
{
	public semClientesException()
	{
		super("Nao ha clientes cadastrados. ");
	}
}
