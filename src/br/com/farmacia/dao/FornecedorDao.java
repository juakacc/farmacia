package br.com.farmacia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.farmacia.model.Endereco;
import br.com.farmacia.model.Fornecedor;
import br.com.farmacia.reader.Conexao;
import br.com.farmacia.ui.aux.Recuperar;

public class FornecedorDao implements ICrud<Fornecedor> {

	private static FornecedorDao instance = null;

	public static FornecedorDao getInstancia() {
		if (instance == null) {
			instance = new FornecedorDao();
		}
		return instance;
	}

	@Override
	public boolean adicionar(Fornecedor e) {

		String sql = "INSERT INTO fornecedor (cnpj, nome, rua, numero_casa, "
				+ "bairro, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setString(1, e.getCnpj());
			stmt.setString(2, e.getNome());
			Endereco end = e.getEndereco();
			stmt.setString(3, end.getRua());
			stmt.setInt(4, end.getNumero());
			stmt.setString(5, end.getBairro());
			stmt.setString(6, end.getCidade());
			stmt.setString(7, end.getEstado());

			stmt.execute();

			adicionarTelefones(e);

			stmt.close();
			return true;
		} catch (SQLException e1) {
			return false;
		}
	}

	private void adicionarTelefones(Fornecedor f) {
		String sql = "INSERT INTO telefone_fornecedor (fornecedor_cnpj, telefone)" + 
					"VALUES (?, ?)";

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, f.getCnpj());

			List<String> telefones = f.getTelefones();
			
			for (String telefone : telefones ) {
				stmt.setString(2, telefone);

				stmt.execute();
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean remover(Fornecedor e) {
		String sql = "DELETE FROM fornecedor WHERE cnpj = ?";
		
		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, e.getCnpj());
			
			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e1) {
			return false;
		}
	}

	@Override
	public boolean editar(Fornecedor e) {
		String sql = "UPDATE fornecedor set nome = ?, " + "rua = ?," + "numero_casa = ?," + "bairro = ?,"
				+ "cidade = ?," + "estado = ?" + "WHERE cnpj = ?";
		
		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setString(1, e.getNome());
			Endereco end = e.getEndereco();
			stmt.setString(2, end.getRua());
			stmt.setInt(3, end.getNumero());
			stmt.setString(4, end.getBairro());
			stmt.setString(5, end.getCidade());
			stmt.setString(6, end.getEstado());
			stmt.setString(7, e.getCnpj());

			stmt.execute();
			stmt.close();
			
			editarTelefones(e);
			return true;
		} catch (SQLException e1) {
			return false;
		}
	}

	private void editarTelefones(Fornecedor f) {
		String sql = "DELETE FROM telefone_fornecedor WHERE fornecedor_cnpj = ?";
		
		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, f.getCnpj());
			
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			adicionarTelefones(f);
		}
	}

	public List<Fornecedor> recuperar(ResultSet rs) throws SQLException {
		List<Fornecedor> fornecedores = new ArrayList<>();

		while (rs.next()) {
			Fornecedor f = transformarFornecedor(rs);
			fornecedores.add(f);
		}
		return fornecedores;
	}

	private Fornecedor transformarFornecedor(ResultSet rs) throws SQLException {
		String cnpj = rs.getString("cnpj");
		String nome = rs.getString("nome");

		String rua = rs.getString("rua");
		int numero = rs.getInt("numero_casa");
		String bairro = rs.getString("bairro");
		String cidade = rs.getString("cidade");
		String estado = rs.getString("estado");

		Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado);
		List<String> telefones = recuperarTelefones(cnpj);

		return new Fornecedor(cnpj, nome, endereco, telefones);
	}

	/**
	 * Recupera um fornecedor de acordo com o CNPJ informado
	 * 
	 * @param cnpj
	 *            CNPJ do fornecedor
	 * @return Um Fornecedor
	 */
	public Fornecedor recuperarPorCnpj(String cnpj) {

		String sql = "SELECT * FROM fornecedor WHERE cnpj = ?";
		Fornecedor fornecedor = new Fornecedor();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, cnpj);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String nome = rs.getString("nome");

				String rua = rs.getString("rua");
				int numero = rs.getInt("numero_casa");
				String bairro = rs.getString("bairro");
				String cidade = rs.getString("cidade");
				String estado = rs.getString("estado");

				Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado);
				List<String> telefones = recuperarTelefones(cnpj);

				fornecedor = new Fornecedor(cnpj, nome, endereco, telefones);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fornecedor;
	}


	private List<String> recuperarTelefones(String cnpj) {
		String sql = "SELECT * FROM telefone_fornecedor WHERE fornecedor_cnpj = ?";
		List<String> telefones = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, cnpj);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				telefones.add(Recuperar.colocarMascaraTelefone(rs.getString("telefone")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return telefones;
	}

	public List<Fornecedor> recuperarPorNome(String nome) {
		String sql = "SELECT * FROM fornecedor WHERE nome LIKE '%" + nome + "%'";
		List<Fornecedor> fornedores = new ArrayList<>();

		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			// stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			fornedores = recuperar(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fornedores;
	}

}
