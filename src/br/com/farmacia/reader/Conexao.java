package br.com.farmacia.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {

	public static Connection getConexao() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost/farmacia_bd", "juaka", "123");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro no banco");
			throw new RuntimeException();
		}
	}
}
