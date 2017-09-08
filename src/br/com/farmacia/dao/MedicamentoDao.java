package br.com.farmacia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.farmacia.model.Medicamento;
import br.com.farmacia.reader.Conexao;

public class MedicamentoDao implements ICrud<Medicamento> {

	private static MedicamentoDao instance = null;

	public static MedicamentoDao getInstancia() {
		if (instance == null) {
			instance = new MedicamentoDao();
		}
		return instance;
	}

	@Override
	public boolean adicionar(Medicamento e) {
		String sql = "INSERT INTO medicamento (id_medicamento, nome, numero_ms,"
				+ "categoria, forma, laboratorio, codigo_barras, lote,"
				+ " concentracao, data_validade, data_compra, valor_compra," + " valor_venda, quantidade_estoque"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, e.getId());
			stmt.setString(2, e.getNome());
			stmt.setString(3, e.getNumero_ms());

			stmt.setString(4, e.getCategoria());
			stmt.setString(5, e.getForma());
			stmt.setString(6, e.getLaboratorio());
			stmt.setString(7, e.getCodigo_barras());
			stmt.setString(8, e.getLote());

			stmt.setDouble(9, e.getConcentracao());

			stmt.setDate(10, new Date(e.getData_validade().getTime()));
			stmt.setDate(11, new Date(e.getData_compra().getTime()));

			stmt.setDouble(12, e.getValor_compra());
			stmt.setDouble(13, e.getValor_venda());

			stmt.setInt(14, e.getQuantidade_estoque());

			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e1) {
			return false;
		}
	}

	@Override
	public boolean remover(Medicamento e) {
		String sql = "DELETE FROM medicamento WHERE id_medicamento = ?";
		
		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, e.getId());

			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e1) {
			return false;
		}
	}

	@Override
	public boolean editar(Medicamento e) {
		String sql = "UPDATE medicamento SET nome = ?, " + 
				" numero_ms = ?, " + 
				"categoria = ?, " + 
				"forma = ?, " + 
				"laboratorio = ?, " + 
				"codigo_barras = ?, " + 
				"lote = ?, " + 
				"concentracao = ?, " + 
				"data_validade = ?, " + 
				"data_compra = ?, " + 
				"valor_compra = ?, " + 
				"valor_venda = ?, " + 
				"quantidade_estoque = ? "
				+ "WHERE id_medicamento = ?";

		try (Connection conexao = Conexao.getConexao()) {

			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setString(1, e.getNome());
			stmt.setString(2, e.getNumero_ms());

			stmt.setString(3, e.getCategoria());
			stmt.setString(4, e.getForma());
			stmt.setString(5, e.getLaboratorio());
			stmt.setString(6, e.getCodigo_barras());
			stmt.setString(7, e.getLote());

			stmt.setDouble(8, e.getConcentracao());

			stmt.setDate(9, new Date(e.getData_validade().getTime()));
			stmt.setDate(10, new Date(e.getData_compra().getTime()));

			stmt.setDouble(11, e.getValor_compra());
			stmt.setDouble(12, e.getValor_venda());

			stmt.setInt(13, e.getQuantidade_estoque());
			stmt.setInt(14, e.getId());
			
			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e1) {
			return false;
		}
	}

	public List<Medicamento> recuperar(ResultSet rs) throws SQLException {
		List<Medicamento> medicamentos = new ArrayList<>();

		while (rs.next()) {
			Medicamento m = transformarMedicamento(rs);
			medicamentos.add(m);
		}
		return medicamentos;
	}

	public List<Medicamento> recuperarPorNome(String nome) {
		nome = "%" + nome + "%";
		String sql = "SELECT * FROM medicamento WHERE nome LIKE ?";
		List<Medicamento> medicamentos = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			medicamentos = recuperar(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medicamentos;
	}

	public Medicamento recuperarPorId(int id) {
		String select = "SELECT * FROM medicamento WHERE id_medicamento = ?";
		Medicamento medicamento = null;

		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(select);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				medicamento = transformarMedicamento(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medicamento;
	}

	private Medicamento transformarMedicamento(ResultSet rs) throws SQLException {
		String nome = rs.getString("nome");
		String numero_ms = rs.getString("numero_ms");
		String categoria = rs.getString("categoria");
		String forma = rs.getString("forma");
		String laboratorio = rs.getString("laboratorio");
		String codigo_barras = rs.getString("codigo_barras");
		String lote = rs.getString("lote");
		Double concentracao = rs.getDouble("concentracao");
		Date data_validade = rs.getDate("data_validade");
		Date data_compra = rs.getDate("data_compra");
		Double valor_compra = rs.getDouble("valor_compra");
		Double valor_venda = rs.getDouble("valor_venda");
		int quantidade_estoque = rs.getInt("quantidade_estoque");

		Medicamento m = new Medicamento(nome, numero_ms, categoria, forma, laboratorio, codigo_barras, lote,
				concentracao, data_validade, data_compra, valor_compra, valor_venda, quantidade_estoque);
		m.setId_medicamento(rs.getInt("id_medicamento"));

		return m;
	}

	public boolean adicionarQuantidade(Medicamento medicamento, int quantidade) {
		
		try (Connection con = Conexao.getConexao()) {
			String sql = "SELECT quantidade_estoque FROM medicamento WHERE id_medicamento = ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, medicamento.getId());
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				int qtd = rs.getInt("quantidade_estoque");
				sql = "UPDATE medicamento SET quantidade_estoque = ? WHERE id_medicamento = ?";

				stmt = con.prepareStatement(sql);
				stmt.setInt(1, quantidade + qtd);
				stmt.setInt(2, medicamento.getId());
				
				stmt.execute();
			}
			stmt.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
