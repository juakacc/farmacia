package br.com.farmacia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.farmacia.model.Item;
import br.com.farmacia.model.Medicamento;
import br.com.farmacia.model.Pessoa;
import br.com.farmacia.model.Venda;
import br.com.farmacia.reader.Conexao;

public final class VendaDao implements ICrud<Venda>{

	private static VendaDao instancia = null;

	public static VendaDao getInstancia() {
		if (instancia == null)
			instancia = new VendaDao();
		return instancia;
	}

	public boolean adicionar(Venda e) {
		
		try (Connection con = Conexao.getConexao()) {
			// Crio o pedido
			String sql = "INSERT INTO pedido () VALUES ()";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();

			// Recupero o id do pedido
			sql = "SELECT * FROM pedido ORDER BY id_pedido desc LIMIT 1";
			stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			int id_pedido = rs.next() ? rs.getInt("id_pedido") : 0;
			
			// Por fim adiciono a venda
			sql = "INSERT INTO venda (pedido_id_pedido, cliente_cpf, funcionario_cpf, data_venda) "
					+ "VALUES (?, ?, ?, ?)";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, id_pedido);
			stmt.setString(2, e.getCliente().getCpf());
			stmt.setString(3, e.getFuncionario().getCpf());
			stmt.setDate(4, new Date(e.getData().getTime()));

			stmt.execute();
			
			// Adiciono os itens
			List<Item> itens = e.getItens();
			for (Item item : itens) {
				sql = "INSERT INTO item (pedido_id, medicamento_id, quantidade) "
						+ "VALUES (?, ?, ?)";
				stmt = con.prepareStatement(sql);
				
				stmt.setInt(1, id_pedido);
				stmt.setInt(2, item.getMedicamento().getId());
				stmt.setInt(3, item.getQuantidade());
				
				stmt.execute();
				// decremento a qtd que o cliente comprou
				MedicamentoDao.getInstancia().adicionarQuantidade(item.getMedicamento(), - item.getQuantidade());
				stmt.close();
			}
			return true;
		} catch (Exception e2) {
			return false;
		}
	}

	public boolean remover(Venda e) {
		String sql = "DELETE FROM venda WHERE id_venda = ?";
		
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

	public boolean editar(Venda e) {
		// Não sei se vai ser necessario
		return false;
	}

	private List<Item> recuperarItens(int id_pedido) {
		String sql = "SELECT * FROM item WHERE pedido_id = " + id_pedido;
		List<Item> itens = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Medicamento medicamento = MedicamentoDao.getInstancia().recuperarPorId(rs.getInt("medicamento_id"));
				int quantidade = rs.getInt("quantidade");

				Item item = new Item(medicamento, quantidade);
				itens.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itens;
	}

	public List<Venda> recuperarPorData(java.util.Date i, java.util.Date f) {
		Date inicio = new Date(i.getTime());
		Date fim = new Date(f.getTime());
		
		String sql = "SELECT * FROM venda "
				+ "WHERE data_venda BETWEEN '"+ inicio.toString() +"' AND '"+ fim.toString() + "'";
		List<Venda> vendas = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			vendas = recuperar(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendas;
	}

	public Venda recuperarPorId(int id) {
		String sql = "SELECT * FROM venda WHERE id_venda = ?";
		
		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Venda venda = transformarVenda(rs);
				return venda;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Venda();
	}
	
	/**
	 * Transforma o ResultSet recebido em uma Venda
	 * @param rs ResultSet
	 * @return A Venda
	 * @throws SQLException caso o rs lance uma exceção
	 * */
	private Venda transformarVenda(ResultSet rs) throws SQLException {
		Pessoa cliente = PessoaDao.getInstancia().recuperarPorCPF(rs.getString("cliente_cpf"));
		Pessoa funcionario = PessoaDao.getInstancia().recuperarPorCPF(rs.getString("funcionario_cpf"));
		int id_pedido = rs.getInt("pedido_id_pedido");
		List<Item> itens = recuperarItens(id_pedido);
		java.util.Date data = rs.getDate("data_venda");

		Venda v = new Venda(cliente, funcionario, itens, data);
		v.setId(rs.getInt("id_venda"));
		return v;
	}

	public List<Venda> recuperar(ResultSet rs) throws SQLException {
		List<Venda> vendas = new ArrayList<>();

		while (rs.next()) {
			Venda v = transformarVenda(rs);
			vendas.add(v);
		}
		return vendas;
	}

	public List<Venda> recuperarPorCliente(Pessoa e) {
		String sql = "SELECT * FROM venda WHERE cliente_cpf = ?";
		List<Venda> vendas = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, e.getCpf());
			ResultSet rs = stmt.executeQuery();

			vendas = recuperar(rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return vendas;
	}

	public List<Venda> recuperarPorFuncionario(Pessoa e) {
		String sql = "SELECT * FROM venda WHERE funcionario_cpf = ?";
		List<Venda> vendas = new ArrayList<>();

		try (Connection con = Conexao.getConexao()) {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, e.getCpf());
			ResultSet rs = stmt.executeQuery();

			vendas = recuperar(rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return vendas;
	}

}
