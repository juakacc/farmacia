package br.com.farmacia.ui.aux;

import java.util.List;

import br.com.farmacia.model.Fornecedor;
import br.com.farmacia.model.Item;
import br.com.farmacia.model.Pessoa;

public final class Valida {

	/**
	 * Valida uma data no formato dd/MM/yyyy
	 * 
	 * */
	public static boolean validarData(String data) {
		String padrao = "\\d{2}/\\d{2}/\\d{4}";
		return data.matches(padrao);
	}
	
	public static boolean validarTelefone(String telefone) {
		
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(validarData("11/11/1995"));
	}

	public static boolean validarVenda(Pessoa cliente, Pessoa funcionario, List<Item> itens) {
		if (cliente.isNull() || funcionario.isNull() || itens.size() == 0) {
			return false;
		}
		return true;
	}

	public static boolean validarCompra(Fornecedor fornecedor, List<Item> itens) {
		if (fornecedor.isNull() || itens.size() == 0) {
			return false;
		}
		return true;
	}
}
