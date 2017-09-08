package br.com.farmacia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.farmacia.model.Compra;
import br.com.farmacia.model.Fornecedor;
import br.com.farmacia.model.Medicamento;
import br.com.farmacia.reader.Conexao;

public final class CompraDao implements ICrud<Compra> {

	private static CompraDao instancia;

	public static CompraDao getInstancia() {
		if (instancia == null) {
			instancia = new CompraDao();
		}
		return instancia;
	}

	@Override
	public boolean adicionar(Compra e) {
		String sql = "INSERT INTO medicamento_fornecedor "
				+ "(medicamento_id, fornecedor_cnpj, quant_fornecida, data_compra) " + "VALUES (?, ?, ?, ?)";

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, e.getMedicamento().getId());
			stmt.setString(2, e.getFornecedor().getCnpj());
			stmt.setInt(3, e.getQuantidade());
			stmt.setDate(4, new Date(e.getData().getTime()));

			// Se adicionar atualiza a quantidade do medicamento
			stmt.execute();
			
			MedicamentoDao repo = MedicamentoDao.getInstancia();
			Medicamento antigo = repo.recuperarPorId(e.getMedicamento().getId());
			Medicamento novo = e.getMedicamento();
			novo.setQuantidade_estoque(antigo.getQuantidade_estoque() + e.getQuantidade());
			repo.editar(novo);
			
			stmt.close();
			return true;
		} catch (SQLException e2) {
			return false;
		}
	}

	@Override
	public boolean remover(Compra e) {
		String sql = "DELETE FROM medicamento_fornecedor WHERE id_compra = ?";

		try (Connection conexao = Conexao.getConexao()) {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, e.getId());

			stmt.execute();
			stmt.close();
			return true;
		} catch (Exception e2) {
			return false;
		}
	}

	@Override
	public boolean editar(Compra e) {
		// Não sei se vai ser preciso
		return false;
	}

	/**
	 * Recebe um determinado ResultSet e recuperar as Compras enquanto houver
	 * rs.next;
	 * 
	 * @param rs
	 *            ResultSet
	 * @return a lista com as compras
	 */
	public List<Compra> recuperar(ResultSet rs) throws SQLException {
		List<Compra> compras = new ArrayList<>();

		while (rs.next()) {
			Compra compra = transformarCompra(rs);
			compras.add(compra);
		}
		return compras;
	}

	/**
	 * Recebe um ResultSet e retorna Compra que existe nele
	 * 
	 * @param rs
	 *            ResultSet
	 * @return A Compra associada
	 * @throws SQLException
	 *             caso o rs lance exceção
	 */
	private Compra transformarCompra(ResultSet rs) throws SQLException {
		Fornecedor fornecedor = FornecedorDao.getInstancia().recuperarPorCnpj(rs.getString("fornecedor_cnpj"));
		int qtd = rs.getInt("quant_fornecida");
		Medicamento medicamento = MedicamentoDao.getInstancia().recuperarPorId(rs.getInt("medicamento_id"));
		java.util.Date data = rs.getDate("data_compra");

		Compra compra = new Compra(fornecedor, medicamento, qtd, data);
		compra.setId(rs.getInt("id_compra"));
		return compra;
	}

	/**
	 * Recupera todas as vendas cadastradas no sistema
	 * 
	 * @return Uma List com todas as compras
	 */
	public List<Compra> recuperar() {
		String sql = "SELECT * FROM medicamento_fornecedor";
		List<Compra> compras = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			compras = recuperar(rs);

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compras;
	}

	/**
	 * Recupera uma compra pelo seu ID
	 * 
	 * @param id
	 * @return A Compra
	 */
	public Compra recuperarPorId(int id) {
		String sql = "SELECT * FROM medicamento_fornecedor WHERE id_compra = ?";

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				Compra compra = transformarCompra(rs);
				compra.setId(id);
				return compra;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Compra();
	}

	public List<Compra> recuperarPorData(java.util.Date i, java.util.Date f) {
		Date inicio = new Date(i.getTime()), fim = new Date(f.getTime());
		String sql = "SELECT * FROM medicamento_fornecedor " + "WHERE data_compra BETWEEN '" + inicio.toString()
				+ "' AND '" + fim.toString() + "'";
		List<Compra> compras = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			compras = recuperar(rs);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compras;
	}
}
