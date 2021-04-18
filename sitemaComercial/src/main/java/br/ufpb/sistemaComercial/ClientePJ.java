package br.ufpb.sistemaComercial;

public class ClientePJ extends Cliente {
	
	private String cnpj;
	
	ClientePJ(String nome, String endereco, String email, String cnpj) {
		super(nome, endereco, email);
		this.cnpj = cnpj;
	}

	public String getId() {
		return cnpj;
	}

	public void setId(String cnpj) {
		this.cnpj = cnpj;
	}
	
}