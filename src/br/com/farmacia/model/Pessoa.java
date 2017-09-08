package br.com.farmacia.model;

import java.util.Date;
import java.util.List;

public class Pessoa {

	private String cpf;
	private String nome;
	private String identidade;
	private Date dataNascimento;
	private Endereco endereco;

	private boolean is_estudante;
	private String carteiraEstudante;

	private boolean is_funcionario;
	private Date dataInicio;
	private String funcao;
	private double salario;

	private List<String> telefones;

	public Pessoa(String cpf, String nome, String identidade, Date dataNascimento, Endereco endereco,
			boolean is_estudante, String carteiraEstudante, boolean is_funcionario, Date dataInicio, String funcao,
			double salario, List<String> telefones) {

		this.cpf = cpf;
		this.nome = nome;
		this.identidade = identidade;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;

		this.is_estudante = is_estudante;
		this.carteiraEstudante = carteiraEstudante;
		
		this.is_funcionario = is_funcionario;
		this.dataInicio = dataInicio;
		this.funcao = funcao;
		this.salario = salario;

		this.telefones = telefones;
	}

	public Pessoa() {
		this.cpf = "";
		this.nome = "";
		this.identidade = "";
		this.dataNascimento = null;
		this.endereco = null;

		this.is_estudante = false;
		this.is_funcionario = false;

		this.telefones = null;
	}

	@Override
	public String toString() {
		return "Pessoa [cpf=" + cpf + ", nome=" + nome + ", identidade=" + identidade + ", dataNascimento="
				+ dataNascimento + ", endereco=" + endereco + ", is_estudante=" + is_estudante + ", carteiraEstudante="
				+ carteiraEstudante + ", is_funcionario=" + is_funcionario + ", dataInicio=" + dataInicio + ", funcao="
				+ funcao + ", salario=" + salario + ", telefones=" + telefones + "]";
	}

	public String getCpf() {
		return this.cpf;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the identidade
	 */
	public String getIdentidade() {
		return identidade;
	}

	/**
	 * @return the dataNascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @return the endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * @return the is_estudante
	 */
	public boolean isEstudante() {
		return is_estudante;
	}

	/**
	 * @return the carteiraEstudante
	 */
	public String getCarteiraEstudante() {
		return carteiraEstudante;
	}

	/**
	 * @return the is_funcionario
	 */
	public boolean isFuncionario() {
		return is_funcionario;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @return the funcao
	 */
	public String getFuncao() {
		return funcao;
	}

	/**
	 * @return the salario
	 */
	public double getSalario() {
		return salario;
	}

	/**
	 * @return the telefones
	 */
	public List<String> getTelefones() {
		return telefones;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @param identidade
	 *            the identidade to set
	 */
	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	/**
	 * @param dataNascimento
	 *            the dataNascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 * @param is_estudante
	 *            the is_estudante to set
	 */
	public void setIs_estudante(boolean is_estudante) {
		this.is_estudante = is_estudante;
	}

	/**
	 * @param carteiraEstudante
	 *            the carteiraEstudante to set
	 */
	public void setCarteiraEstudante(String carteiraEstudante) {
		this.carteiraEstudante = carteiraEstudante;
	}

	/**
	 * @param is_funcionario
	 *            the is_funcionario to set
	 */
	public void setIs_funcionario(boolean is_funcionario) {
		this.is_funcionario = is_funcionario;
	}

	/**
	 * @param dataInicio
	 *            the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @param funcao
	 *            the funcao to set
	 */
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	/**
	 * @param salario
	 *            the salario to set
	 */
	public void setSalario(double salario) {
		this.salario = salario;
	}

	/**
	 * @param telefones
	 *            the telefones to set
	 */
	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

	public boolean isNull() {
		return "".equals(cpf);
	}

}
