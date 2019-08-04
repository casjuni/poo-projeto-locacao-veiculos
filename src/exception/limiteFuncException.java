package exception;

@SuppressWarnings("serial")
public class limiteFuncException extends Exception 
{
	public limiteFuncException(int limite)
	{
		super("O limite de " + limite + "funcionarios ja foi atingido!");
	}
}
