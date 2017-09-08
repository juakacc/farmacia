package br.com.farmacia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.farmacia.model.Endereco;
import br.com.farmacia.model.Pessoa;
import br.com.farmacia.reader.Conexao;

public class PessoaDao implements ICrud<Pessoa> {

	private static PessoaDao instancia = null;

	public static PessoaDao getInstancia() {
		if (instancia == null) {
			instancia = new PessoaDao();
		}
		return instancia;
	}

	@Override
	public boolean adicionar(Pessoa e) {

		String sql = "INSERT INTO pessoa (cpf, nome, identidade, "
				+ "data_nascimento, rua, numero_casa, bairro, cidade, estado, is_estudante, carteira_estudante, "
				+ "is_funcionario, data_inicio, funcao, salario) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setString(1, e.getCpf());
			stmt.setString(2, e.getNome());

			stmt.setString(3, e.getIdentidade());
			stmt.setDate(4, new Date(e.getDataNascimento().getTime())); // verificar como vai ficar

			Endereco end = e.getEndereco();
			stmt.setString(5, end.getRua());
			stmt.setInt(6, end.getNumero());
			stmt.setString(7, end.getBairro());
			stmt.setString(8, end.getCidade());
			stmt.setString(9, end.getEstado());

			stmt.setBoolean(10, e.isEstudante());
			stmt.setString(11, e.getCarteiraEstudante());

			stmt.setBoolean(12, e.isFuncionario());
			stmt.setDate(13, new Date(e.getDataInicio().getTime()));
			stmt.setString(14, e.getFuncao());
			stmt.setDouble(15, e.getSalario());

			stmt.execute();
			stmt.close();

			adicionarTelefones(e);
			return true;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	// Removo e depois adiciono
	private void editarTelefones(Pessoa p) {
		String sql = "DELETE FROM telefone_pessoa WHERE pessoa_cpf = ?";

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, p.getCpf());

			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			adicionarTelefones(p);
		}
	}

	private void adicionarTelefones(Pessoa p) {
		String sql = "INSERT INTO telefone_pessoa (pessoa_cpf, telefone) " + "VALUES (?, ?)";

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, p.getCpf());

			List<String> telefones = p.getTelefones();
			for (String telefone : telefones) {
				stmt.setString(2, telefone);
				stmt.execute();
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean remover(Pessoa e) {
		String sql = "DELETE FROM pessoa WHERE cpf = ?";

		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, e.getCpf());

			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e1) {
			System.err.println(e1.getMessage());
			return false;
		}
	}

	@Override
	public boolean editar(Pessoa e) {
		String sql = "UPDATE pessoa SET " + "nome = ?, " + "identidade = ?, " + "data_nascimento = ?, " + "rua = ?, "
				+ "numero_casa = ?, " + "bairro = ?, " + "cidade = ?, " + "estado = ?, " + "is_estudante = ?, "
				+ "carteira_estudante = ?, " + "is_funcionario = ?, " + "data_inicio = ?, " + "funcao = ?, "
				+ "salario = ? " + "WHERE cpf = ?";

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, e.getNome());
			stmt.setString(2, e.getIdentidade());
			stmt.setDate(3, new Date(e.getDataNascimento().getTime()));

			Endereco end = e.getEndereco();
			stmt.setString(4, end.getRua());
			stmt.setInt(5, end.getNumero());
			stmt.setString(6, end.getBairro());
			stmt.setString(7, end.getCidade());
			stmt.setString(8, end.getEstado());

			stmt.setBoolean(9, e.isEstudante());
			stmt.setString(10, e.getCarteiraEstudante());

			stmt.setBoolean(11, e.isFuncionario());
			stmt.setDate(12, new Date(e.getDataInicio().getTime()));
			stmt.setString(13, e.getFuncao());
			stmt.setDouble(14, e.getSalario());
			stmt.setString(15, e.getCpf());

			stmt.execute();
			stmt.close();

			editarTelefones(e);
			return true;
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
			return false;
		}
	}

	/**
	 * Recupera uma Pessoa de acordo com o CPF informado
	 * 
	 * @param cpf
	 *            CPF informado
	 * @return Um Pessoa
	 */
	public Pessoa recuperarPorCPF(String cpf) {
		String sql = "SELECT * FROM pessoa WHERE cpf = ?";

		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, cpf);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return transformarPessoa(rs);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return new Pessoa();
	}

	private List<String> recuperarTelefones(String cpf) {
		String sql = "SELECT * FROM telefone_pessoa WHERE pessoa_cpf = ?";
		List<String> telefones = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, cpf);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				telefones.add(rs.getString("telefone"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return telefones;
	}

	public List<Pessoa> recuperar(ResultSet rs) throws SQLException {
		List<Pessoa> pessoas = new ArrayList<>();
		while (rs.next()) {
			pessoas.add(transformarPessoa(rs));
		}
		return pessoas;
	}

	/**
	 * Recebe um ResultSet e transforma-o em uma Pessoa
	 * 
	 * @param rs
	 *            ResultSet vindo de uma pesquisa SQL
	 * @return Pessoa
	 */
	private Pessoa transformarPessoa(ResultSet rs) throws SQLException {
		String cpf, nome, identidade, carteira_estudante, rua, bairro, cidade, estado, funcao = "";
		java.util.Date data_nascimento = new java.util.Date(), data_inicio = new java.util.Date();
		boolean is_estudante, is_funcionario;

		cpf = rs.getString("cpf");
		nome = rs.getString("nome");
		identidade = rs.getString("identidade");

		data_nascimento = new java.util.Date(rs.getDate("data_nascimento").getTime());

		rua = rs.getString("rua");
		bairro = rs.getString("bairro");
		cidade = rs.getString("cidade");
		estado = rs.getString("estado");
		int numero = rs.getInt("numero_casa");

		Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado);

		is_estudante = rs.getBoolean("is_estudante");
		carteira_estudante = is_estudante ? rs.getString("carteira_estudante") : "";

		is_funcionario = rs.getBoolean("is_funcionario");
		double salario = 0;

		if (is_funcionario) {
			data_inicio = new java.util.Date(rs.getDate("data_inicio").getTime());	
			funcao = rs.getString("funcao");
			salario = rs.getDouble("salario");
		}
		
		List<String> telefones = recuperarTelefones(cpf);

		return new Pessoa(cpf, nome, identidade, data_nascimento, endereco, is_estudante, carteira_estudante,
				is_funcionario, data_inicio, funcao, salario, telefones);
	}

	public List<Pessoa> recuperarFuncNome(String nome) {
		nome = "%" + nome + "%";
		String sql = "SELECT * FROM pessoa WHERE nome LIKE ? AND is_funcionario = 1";
		List<Pessoa> pessoas = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			pessoas = recuperar(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoas;
	}

	public List<Pessoa> recuperarClieNome(String nome) {
		nome = "%" + nome + "%";
		String sql = "SELECT * FROM pessoa WHERE nome LIKE ? AND is_funcionario = 0";
		List<Pessoa> pessoas = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			pessoas = recuperar(rs);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return pessoas;
	}

	public List<Pessoa> recuperarCliEFuncNome(String nome) {
		nome = "%" + nome + "%";
		String sql = "SELECT * FROM pessoa WHERE nome LIKE ?";
		List<Pessoa> pessoas = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			pessoas = recuperar(rs);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return pessoas;
	}

}
