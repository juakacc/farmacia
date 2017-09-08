package br.com.farmacia.model;

public class Endereco {

	private String rua;
	private int numero;
	private String bairro;
	private String cidade;
	private String estado;
	
	public Endereco(String rua, int numero_casa, String bairro, String cidade, String estado) {
		this.rua = rua;
		this.numero = numero_casa;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	/**
	 * @return the rua
	 */
	public String getRua() {
		return rua;
	}
	/**
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}
	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}
	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param rua the rua to set
	 */
	public void setRua(String rua) {
		this.rua = rua;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(this.rua + ", ");
		str.append((this.numero != 0) ? (this.numero + ", ") : "");
		str.append(this.bairro + ", ");
		str.append(this.cidade + ", ");
		str.append(this.estado);
		
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + numero;
		result = prime * result + ((rua == null) ? 0 : rua.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Endereco) {
			Endereco other = (Endereco) obj;
			
			if (other.hashCode() == this.hashCode()) {
				return true;
			}
		}
		return false;
	}	
}
