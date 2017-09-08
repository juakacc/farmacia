package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.farmacia.dao.FornecedorDao;
import br.com.farmacia.model.Fornecedor;
import br.com.farmacia.ui.model.FrameGerenciaUI;

public class GerenciaFornecedorUI extends FrameGerenciaUI {
	
	private JTable tabela;
	private DefaultTableModel modelo;
	
	private FornecedorDao repo;
	
	public GerenciaFornecedorUI(JFrame janelaChamadora) {
		super("Gerenciar Fornecedor");
		repo = FornecedorDao.getInstancia();
	}
	
	public static void main(String[] args) {
		new GerenciaFornecedorUI(null).montarTela();
	}

	public void montarTela() {
		prepararJanela();
		prepararPainelPrincipal();

		prepararCabecalho();
		prepararTabela();
		prepararPainelBotoes();
		prepararBotoes();
		
		mostrarJanela();
	}
	
	private void atualizarTabela() {
		modelo.setRowCount(0);
		List<Fornecedor> fornecedores = atualizarLista();
		
		for (Fornecedor forn : fornecedores) {
			modelo.addRow(new Object[]{forn.getCnpj(), forn.getNome(), forn.getEndereco().toString()});
		}
	}

	private List<Fornecedor> atualizarLista() {
		String nome = txtNome.getText();
		return repo.recuperarPorNome(nome); // Jogar isso na tabela
	}
	
	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("CPF");
		modelo.addColumn("Nome");
		modelo.addColumn("Endereço"); // concatenado
		
		JScrollPane sroll = new JScrollPane(tabela);
		atualizarTabela();
		
		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		int linha = tabela.getSelectedRow();
		
		switch (o) {
		case "Voltar":
			fecharJanela();
			break;
		case "Editar":
			if (linha > -1) {
				String cnpj = (String) modelo.getValueAt(linha, 0);
				Fornecedor fornecedor = repo.recuperarPorCnpj(cnpj);
				
				FornecedorForm f = new FornecedorForm(fornecedor, true);
				f.montarTela();
				fornecedor = f.getFornecedor();
				
				if (!fornecedor.isNull()) {
					if (repo.editar(fornecedor)) {
						JOptionPane.showMessageDialog(null, "Editado com sucesso");
					} else {
						JOptionPane.showMessageDialog(null, "Ocorreu um erro");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			break;
		case "Remover":
			if (linha > -1) {
				String cnpj = (String) modelo.getValueAt(linha, 0);
				Fornecedor fornecedor = repo.recuperarPorCnpj(cnpj);
				
				if (repo.remover(fornecedor)) {
					JOptionPane.showMessageDialog(null, "Removido com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Aconteceu algum erro durante a remoção");					
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			break;
		case "Adicionar":
			FornecedorForm f = new FornecedorForm(new Fornecedor(), false);
			f.montarTela();
			Fornecedor fornecedor = f.getFornecedor();
			
			if (!fornecedor.isNull()) {
				if (repo.adicionar(fornecedor)) {
					JOptionPane.showMessageDialog(null, "Adicionado com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro");					
				}
			}
			break;
		case "Pesquisar":
			atualizarTabela();
			break;
		}
	}

}
