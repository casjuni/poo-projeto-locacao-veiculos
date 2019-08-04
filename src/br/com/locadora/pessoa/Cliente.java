package br.com.locadora.pessoa;


public class Cliente extends Pessoa
{
	private class Endereco 
	{
        String logradouro;
        int numero;
        String complemento;
        String cep;
        String cidade;
        String estado;
	}

	private String cnh;
    private Endereco endereco;
    private String dataNasc;
    private String cpf;
    private String celular;
    
    public Cliente(String nome, String email, String senha, String dataNasc, String cpf, String cep, String cidade, String estado, String endereco, String complemento,int numero, String celular, String CNH)
    { 
    	super(nome, email, senha);
    	setEndereco(endereco, numero, complemento, cep, cidade, estado);
		setDataNasc(dataNasc);
		setCelular(celular);
		setCPF(cpf);
		setCNH(CNH);
    }
    public void setCPF(String cpf) 
    {
        this.cpf = cpf;
    }

    public void setCNH(String cnh) 
    {
        this.cnh = cnh;
    }

    public void setCelular(String celular)
    {
    	this.celular = celular;
    }
    public void setEndereco(String rua, int n, String comp, String cep, String cidade, String uf) {
        Endereco end = new Endereco();
        end.logradouro = rua;
        end.numero = n;
        end.complemento = comp;
        end.cep = cep;
        end.cidade = cidade;
        end.estado = uf;
        this.endereco = end;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }
    public String CPF() {
        return cpf;
    }
    
    public  String CEP() {
        return endereco.cep;
    }
    
    public  String estado() {
        return endereco.estado;
    }
    
    public  String complemento() {
        return endereco.complemento;
    }
    
    public  String cidade() {
        return endereco.cidade;
    }
    
    public int numero() {
    	return endereco.numero;
    }
    
    public String dataNasc()
    {
    	return dataNasc;
    }
    public String endereco()
    {
    	return endereco.logradouro;
    }
    
    public String celular()
    {
    	return celular;
    }
    
    public String CNH() {
        return cnh;
    } 
}
