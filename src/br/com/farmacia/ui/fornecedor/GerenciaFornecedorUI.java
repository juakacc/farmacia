package br.com.farmacia.ui.fornecedor;

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

import static br.com.farmacia.ui.aux.Recuperar.*;

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
		
		String nome = txtNome.getText();
		List<Fornecedor> fornecedores = repo.recuperarPorNome(nome);
		
		for (Fornecedor forn : fornecedores) {
			modelo.addRow(new Object[]{
				traduzirCnpjMostrar(forn.getCnpj()), 
				forn.getNome(), 
				forn.getEndereco().toString()});
		}
	}
	
	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("CNPJ");
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
				cnpj = traduzirCnpjProBanco(cnpj);
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
			atualizarTabela();
			break;
		case "Remover":
			if (linha > -1) {
				String cnpj = (String) modelo.getValueAt(linha, 0);
				cnpj = traduzirCnpjProBanco(cnpj);
				Fornecedor fornecedor = repo.recuperarPorCnpj(cnpj);
				int opcao = JOptionPane.showConfirmDialog(null, "Isso removerá todos os registros desse fornecedor. Deseja realmente fazer isso?");
				
				if (opcao == JOptionPane.YES_OPTION) {
					if (repo.remover(fornecedor)) {
						JOptionPane.showMessageDialog(null, "Removido com sucesso");
					} else {
						JOptionPane.showMessageDialog(null, "Aconteceu algum erro durante a remoção");					
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			atualizarTabela();
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
			atualizarTabela();
			break;
		case "Pesquisar":
			atualizarTabela();
			break;
		}
	}

}
