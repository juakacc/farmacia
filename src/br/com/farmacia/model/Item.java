package br.com.farmacia.model;

public class Item {

	private int id;
	private int idPedido;
	private Medicamento medicamento;
	private int quantidade;
	
	public Item(Medicamento medicamento, int quantidade) {
		this.medicamento = medicamento;
		this.quantidade = quantidade;
	}

	/**
	 * @return the idPedido
	 */
	public int getIdPedido() {
		return idPedido;
	}

	/**
	 * @return the medicamento
	 */
	public Medicamento getMedicamento() {
		return medicamento;
	}

	/**
	 * @return the quantidade
	 */
	public int getQuantidade() {
		return quantidade;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * @param medicamento the medicamento to set
	 */
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	
	
}
