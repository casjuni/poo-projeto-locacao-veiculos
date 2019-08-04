package exception;

@SuppressWarnings("serial")
public class limiteClientesException extends Exception {

	public limiteClientesException(int limite) 
	{
		super("A quantidade limite (" + limite + ") de clientes foi atingida!");
	}
}
