package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.farmacia.dao.PessoaDao;
import br.com.farmacia.model.Pessoa;
import br.com.farmacia.ui.model.FrameConfirmUI;

public class PesquisarPessoaUI extends FrameConfirmUI {
	
	private JPanel painelCabecalho;
	private JTextField txtNome;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JButton btnSel;
	
	private Pessoa pessoa;
	
	public Pessoa getPessoa() {
		return this.pessoa;
	}
	
	private PessoaDao repo;
	
	/**
	 * True se funcionário, false caso contrário
	 * */
	private boolean proc_func;

	/**
	 * @param b True se funcionário, false caso contrário
	 * */
	public PesquisarPessoaUI(boolean b) {
		super("Pesquisar Pessoa");
		this.proc_func = b;
		this.repo = PessoaDao.getInstancia();
		this.pessoa = new Pessoa();
	}

	public static void main(String[] args) {
		new PesquisarPessoaUI(true).montarTela();
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

	private void prepararCabecalho() {
		JLabel label = new JLabel("Contém:");
		txtNome = new JTextField();
		txtNome.setBorder(BorderFactory.createTitledBorder("Nome"));
		
		btnSel = new JButton(new ImageIcon("icons/pesquisar.png"));
		btnSel.setActionCommand("Pesquisar");
		btnSel.setToolTipText("Pesquisar");
		btnSel.addActionListener(this);
		
		painelCabecalho = new JPanel(new GridLayout(1, 3, 5, 5));
		painelCabecalho.add(label);
		painelCabecalho.add(txtNome);
		painelCabecalho.add(btnSel);
		
		painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
	}
	
	private List<Pessoa> atualizarLista() {
		String nome = txtNome.getText();
		List<Pessoa> pessoas;
		
		if (proc_func) {
			pessoas = repo.recuperarFuncNome(nome);
		} else {
			pessoas = repo.recuperarClieNome(nome);
		}
		return pessoas;
	}
	
	private void atualizarTabela() {
		List<Pessoa> pessoas = atualizarLista();
		modelo.setRowCount(0);
		
		for (Pessoa pessoa : pessoas) {
			modelo.addRow(new Object[]{pessoa.getCpf(), pessoa.getNome(), pessoa.getEndereco().toString()});
		}
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("CPF");
		modelo.addColumn("Nome");
		modelo.addColumn("Endereço"); // concatenado
		
		atualizarTabela();
		JScrollPane sroll = new JScrollPane(tabela);

		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		
		switch (o) {
		case "Pronto":
			int linha = tabela.getSelectedRow();
			if (linha > -1) {
				String cpf = (String) modelo.getValueAt(linha, 0);
				
				pessoa = repo.recuperarPorCPF(cpf);
				fecharJanela();
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");
			}
			break;
		case "Cancelar":
			fecharJanela();
			break;
		case "Pesquisar":
			atualizarTabela();
			break;
		}
	}

}
