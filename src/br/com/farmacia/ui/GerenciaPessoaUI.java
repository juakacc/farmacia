package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.farmacia.dao.PessoaDao;
import br.com.farmacia.model.Pessoa;
import br.com.farmacia.ui.model.FrameGerenciaUI;

public class GerenciaPessoaUI extends FrameGerenciaUI {
	
	private JTable tabela;
	private DefaultTableModel modelo;
	
	/**
	 * True se for para gerenciar funcionários, False caso contrário
	 * */
	private boolean func;
	private PessoaDao repo;
	
	/**
	 * True se for para gerenciar funcionários, False caso contrário
	 * */
	public GerenciaPessoaUI(boolean b) {
		super("Gerenciar Pessoa");
		this.func = b;
		this.repo = PessoaDao.getInstancia();
	}
	

	public static void main(String[] args) {
		new GerenciaPessoaUI(true).montarTela();
	}

	public void montarTela() {
		prepararJanela();
		prepararPainelPrincipal();

		prepararCabecalho();
		prepararTabela();
		atualizarTabela();
		prepararPainelBotoes();
		prepararBotoes();
	
		mostrarJanela();
	}
	
	private List<Pessoa> atualizarLista() {
		
		String nome = txtNome.getText();
		List<Pessoa> pessoas;
		if (func) {
			pessoas = repo.recuperarFuncNome(nome);
		} else {
			pessoas = repo.recuperarClieNome(nome);
		}
		return pessoas;
	}
	
	private void atualizarTabela() {
		modelo.setRowCount(0);
		List<Pessoa> pessoas = atualizarLista();
		
		for (Pessoa pessoa : pessoas ) {
			modelo.addRow(new Object[]{pessoa.getCpf(), pessoa.getNome(), pessoa.getEndereco().toString()});
		}
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("CPF");
		modelo.addColumn("Nome");
		modelo.addColumn("Endereço"); // concatenado
		
		JScrollPane sroll = new JScrollPane(tabela);
		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		int linha = tabela.getSelectedRow();
		
		PessoaForm pForm;
		Pessoa pessoa;
		
		switch(o) {
		case "Voltar":
			fecharJanela();
			break;
		case "Editar":
			if (linha > -1) {
				String cpf = (String) modelo.getValueAt(linha, 0);
				pessoa = repo.recuperarPorCPF(cpf);
				pForm = new PessoaForm(func, pessoa, true);
				pForm.montarTela();
				pessoa = pForm.getPessoa();
				
				if (!pessoa.isNull()) {
					if (repo.editar(pessoa)) {
						JOptionPane.showMessageDialog(null, "Editado com sucesso");
					} else {
						JOptionPane.showMessageDialog(null, "Aconteceu algum erro");					
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			break;
		case "Adicionar":
			pForm = new PessoaForm(func, new Pessoa(), false);
			pForm.montarTela();
			pessoa = pForm.getPessoa();
			
			if (!pessoa.isNull()) {
				if (repo.adicionar(pessoa)) {
					JOptionPane.showMessageDialog(null, "Adicionado com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Aconteceu algum erro");					
				}		
			}
			break;
		case "Remover": // Tenho que verificar as restrições antes de remover
			if (linha > -1) {
				String cpf = (String) modelo.getValueAt(linha, 0);
				pessoa = repo.recuperarPorCPF(cpf);
				
				if (repo.remover(pessoa)) {
					JOptionPane.showMessageDialog(null, "Removido com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Aconteceu algum erro durante a remoção");					
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			break;
		case "Pesquisar":
			atualizarTabela();
			break;
		}
	}

}
