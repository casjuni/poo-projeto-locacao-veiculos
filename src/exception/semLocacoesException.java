package exception;

@SuppressWarnings("serial")
public class semLocacoesException extends Exception 
{
	public semLocacoesException()
	{
		super("Nenhum veiculo foi alugado ainda!");

	}
}