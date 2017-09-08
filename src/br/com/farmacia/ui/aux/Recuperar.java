package br.com.farmacia.ui.aux;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public final class Recuperar {

	
	
	/**
	 * Recupera um inteiro da entrada
	 * @param pergunta Pergunta para o usuário
	 * @return O inteiro ou -1, caso gere uma exceção
	 * */
	public static int recuperarInt(String pergunta) {
		int i = -1;
		try {
			i = Integer.parseInt(JOptionPane.showInputDialog(pergunta));
		} catch (NumberFormatException e) { }
		
		return i;
	}
	
	public static String colocarMascaraTelefone(String t) {
		StringBuilder s = new StringBuilder();
		s.append("(");
		s.append(t.charAt(0));
		s.append(t.charAt(1));
		s.append(")");
		s.append(t.charAt(2));
		s.append(t.charAt(3));
		s.append(t.charAt(4));
		s.append(t.charAt(5));
		s.append("-");
		s.append(t.charAt(6));
		s.append(t.charAt(7));
		s.append(t.charAt(8));
		s.append(t.charAt(9));
		
		return s.toString(); 
	}
	
	public static String traduzirCnpjProBanco(String cnpj) {
		return cnpj.replaceAll("[./-]", "");
	}

	public static String traduzirTelProBanco(String tel) {
		return tel.replaceAll("[()-]", "");
	}

	public static String traduzirCpfMostrar(String cpf) {
		//123.456.789-00
		StringBuilder s = new StringBuilder();
		s.append(cpf.charAt(0));
		s.append(cpf.charAt(1));
		s.append(cpf.charAt(2));
		s.append(".");
		s.append(cpf.charAt(3));
		s.append(cpf.charAt(4));
		s.append(cpf.charAt(5));
		s.append(".");
		s.append(cpf.charAt(6));
		s.append(cpf.charAt(7));
		s.append(cpf.charAt(8));
		s.append("-");
		s.append(cpf.charAt(9));
		s.append(cpf.charAt(10));
		
		return s.toString();
	}
	
	public static String traduzirCnpjMostrar(String cnpj) {
		// 11.111.111/1111-90
		StringBuilder s = new StringBuilder();
		s.append(cnpj.charAt(0));
		s.append(cnpj.charAt(1));
		s.append(".");
		s.append(cnpj.charAt(2));
		s.append(cnpj.charAt(3));
		s.append(cnpj.charAt(4));
		s.append(".");
		s.append(cnpj.charAt(5));
		s.append(cnpj.charAt(6));
		s.append(cnpj.charAt(7));
		s.append("/");
		s.append(cnpj.charAt(8));
		s.append(cnpj.charAt(9));
		s.append(cnpj.charAt(10));
		s.append(cnpj.charAt(11));
		s.append("-");
		s.append(cnpj.charAt(12));
		s.append(cnpj.charAt(13));
		
		return s.toString();
	}
	
	public static String traduzirDataMostrar(Date data) {
		StringBuilder s = new StringBuilder();
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		
		// 11/11/1111
		
		s.append(c.get(Calendar.DAY_OF_MONTH));
		s.append("/");
		s.append(c.get(Calendar.MONTH) + 1);
		s.append("/");
		s.append(c.get(Calendar.YEAR));
		
		return s.toString();
	}
	
}
