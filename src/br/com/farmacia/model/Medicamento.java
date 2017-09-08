package br.com.farmacia.model;

import java.util.Date;

public class Medicamento {

	private int id;
	private String nome, numero_ms, categoria, forma, laboratorio, codigo_barras, lote;
	private double concentracao;
	
	private Date data_validade;
	private Date data_compra;
	
	private double valor_compra;
	private double valor_venda;
	
	private int quantidade_estoque;
	
	public Medicamento(String nome, String numero_ms,
			String categoria, String forma, String laboratorio, String codigo_barras,
			String lote, double concentracao, Date data_validade, Date data_compra,
			double valor_compra, double valor_venda, int quantidade_estoque) {
		
		this.nome = nome;
		this.numero_ms = numero_ms;
		this.categoria = categoria;
		this.forma = forma;
		this.laboratorio = laboratorio;
		this.codigo_barras = codigo_barras;
		this.lote = lote;
		this.concentracao = concentracao;
		this.data_validade = data_validade;
		this.data_compra = data_compra;
		this.valor_compra = valor_compra;
		this.valor_venda = valor_venda;
		this.quantidade_estoque = quantidade_estoque;
	}
	
	public Medicamento() {
		this.nome = "";
		// ...
	}

	@Override
	public String toString(){
		
		return "Medicamento [id_medicamento =" + id + ", nome= " + nome +
				", numero_ms=" + numero_ms + ", categoria=" + categoria + ", forma= " + forma +
				", laboratorio= " + laboratorio + ", codigo de barras= " + codigo_barras +
				", lote= " + lote + ", concentracao= " + concentracao + ", data de validade= " + data_validade +
				", data de compra= " + data_compra + ", valor de compra= " + valor_compra +
				", valor de venda= " + valor_venda + ", estoque= " + quantidade_estoque + "]";
		
		
	}

	/**
	 * @return the id_medicamento
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id_medicamento the id_medicamento to set
	 */
	public void setId_medicamento(int id_medicamento) {
		this.id = id_medicamento;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the numero_ms
	 */
	public String getNumero_ms() {
		return numero_ms;
	}

	/**
	 * @param numero_ms the numero_ms to set
	 */
	public void setNumero_ms(String numero_ms) {
		this.numero_ms = numero_ms;
	}

	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the forma
	 */
	public String getForma() {
		return forma;
	}

	/**
	 * @param forma the forma to set
	 */
	public void setForma(String forma) {
		this.forma = forma;
	}

	/**
	 * @return the laboratorio
	 */
	public String getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio the laboratorio to set
	 */
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the codigo_barras
	 */
	public String getCodigo_barras() {
		return codigo_barras;
	}

	/**
	 * @param codigo_barras the codigo_barras to set
	 */
	public void setCodigo_barras(String codigo_barras) {
		this.codigo_barras = codigo_barras;
	}

	/**
	 * @return the lote
	 */
	public String getLote() {
		return lote;
	}

	/**
	 * @param lote the lote to set
	 */
	public void setLote(String lote) {
		this.lote = lote;
	}

	/**
	 * @return the concentracao
	 */
	public double getConcentracao() {
		return concentracao;
	}

	/**
	 * @param concentracao the concentracao to set
	 */
	public void setConcentracao(double concentracao) {
		this.concentracao = concentracao;
	}

	/**
	 * @return the data_validade
	 */
	public Date getData_validade() {
		return data_validade;
	}

	/**
	 * @param data_validade the data_validade to set
	 */
	public void setData_validade(Date data_validade) {
		this.data_validade = data_validade;
	}

	/**
	 * @return the data_compra
	 */
	public Date getData_compra() {
		return data_compra;
	}

	/**
	 * @param data_compra the data_compra to set
	 */
	public void setData_compra(Date data_compra) {
		this.data_compra = data_compra;
	}

	/**
	 * @return the valor_compra
	 */
	public double getValor_compra() {
		return valor_compra;
	}

	/**
	 * @param valor_compra the valor_compra to set
	 */
	public void setValor_compra(double valor_compra) {
		this.valor_compra = valor_compra;
	}

	/**
	 * @return the valor_venda
	 */
	public double getValor_venda() {
		return valor_venda;
	}

	/**
	 * @param valor_venda the valor_venda to set
	 */
	public void setValor_venda(double valor_venda) {
		this.valor_venda = valor_venda;
	}

	/**
	 * @return the quantidade_estoque
	 */
	public int getQuantidade_estoque() {
		return quantidade_estoque;
	}

	/**
	 * @param quantidade_estoque the quantidade_estoque to set
	 */
	public void setQuantidade_estoque(int quantidade_estoque) {
		this.quantidade_estoque = quantidade_estoque;
	}

	public boolean isNull() {
		return "".equals(nome);
	}
	
	
}
