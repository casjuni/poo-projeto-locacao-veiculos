package exception;

@SuppressWarnings("serial")
public class veiculoInexistenteException extends Exception {

	public veiculoInexistenteException(String placa)
	{
		super("Veiculo de placa " + placa + " inexistente");
	}
}
	
