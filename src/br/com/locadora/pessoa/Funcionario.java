package br.com.locadora.pessoa;


public class Funcionario extends Pessoa {
	
    
    public Funcionario(String nome, String email, String senha)
    { 
    	super(nome, email, senha);
    }
    

    public boolean funcionario()
    { 
    	return true; 
    }
    public String imprimir()
    {
    	return "Nome: " + nome() + ". Email: " + email() + ". Senha: " + senha(); 
    }
}
