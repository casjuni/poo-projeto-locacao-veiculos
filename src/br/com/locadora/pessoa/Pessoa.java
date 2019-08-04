package br.com.locadora.pessoa;

public abstract class Pessoa {

   
    private String nome;
    private String email;
    private String senha;

    public Pessoa(String nome, String email, String senha)
    {
    	setNome(nome);
    	setEmail(email);
    	setSenha(senha);
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String nome() {
        return nome;
    }
    public String email() {
        return email;
    }
    public String senha() {
        return senha;
    }
}
