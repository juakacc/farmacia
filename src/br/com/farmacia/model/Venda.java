package br.com.farmacia.model;

import java.util.Date;
import java.util.List;

public class Venda {

	private int id;
	private Pessoa cliente;
	private Pessoa funcionario;
	private List<Item> itens;
	private Date data;
	private double valor; // verificar
	
	public Venda(Pessoa cliente, Pessoa funcionario, List<Item> itens, Date data) {
		this.cliente = cliente;
		this.funcionario = funcionario;
		this.itens = itens;
		this.data = data;
		this.valor = 0;
		for (Item item : itens) {
			this.valor += item.getQuantidade() * item.getMedicamento().getValor_venda();
		}
	}

	public Venda() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the cliente
	 */
	public Pessoa getCliente() {
		return cliente;
	}

	/**
	 * @return the funcionario
	 */
	public Pessoa getFuncionario() {
		return funcionario;
	}

	/**
	 * @return the itens
	 */
	public List<Item> getItens() {
		return itens;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(Pessoa funcionario) {
		this.funcionario = funcionario;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	
}
