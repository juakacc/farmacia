package br.com.farmacia.dao;

public interface ICrud<E> {

	
	/**
	 * Adiciona <code>E</code> a sua tabela
	 * @param e Registro a ser adicionado
	 * @return True se conseguir adicionar ou False, 
	 * caso contrário
	 * */
	public boolean adicionar(E e);
	
	/**
	 * Remove <code>E</code> da sua tabela
	 * @param e Registro a ser removido
	 * @return True se conseguir remover ou False, 
	 * caso contrário
	 * */
	public boolean remover(E e);
	
	/**
	 * Edita <code>E</code> na sua tabela
	 * @param e Registro a ser editado
	 * @return True se conseguir editar ou False, 
	 * caso contrário
	 * */
	public boolean editar(E e);
	
	//public List<E> recuperar();
	
}