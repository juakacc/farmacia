package br.com.farmacia.model;

import java.util.List;

public class Fornecedor {

	private String cnpj;
	private String nome;
	private Endereco endereco;
	private List<String> telefones;
	
	public Fornecedor(String cnpj, String nome, Endereco endereco, List<String> telefones) {
		this.cnpj = cnpj;
		this.nome = nome;
		this.endereco = endereco;
		this.telefones = telefones;
	}

	public Fornecedor() {
		this.cnpj = "";
		this.nome = "";
		this.endereco = null;
		this.telefones = null;
	}

	/**
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @return the telefones
	 */
	public List<String> getTelefones() {
		return telefones;
	}

	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @param telefones the telefones to set
	 */
	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

	public boolean isNull() {
		return "".equals(cnpj);
	}
}
