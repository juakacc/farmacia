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

import br.com.farmacia.dao.FornecedorDao;
import br.com.farmacia.model.Fornecedor;
import br.com.farmacia.ui.model.FrameConfirmUI;

public class PesquisarFornecedorUI extends FrameConfirmUI {
	
	private JPanel painelCabecalho;
	private JTextField txtNome;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JButton btnPesquisar;
	
	private Fornecedor fornecedor;
	private FornecedorDao repo;
	
	public Fornecedor getFornecedor() {
		return this.fornecedor;
	}
	
	public PesquisarFornecedorUI() {
		super("Pesquisar Fornecedor");
		this.repo = FornecedorDao.getInstancia();
		this.fornecedor = new Fornecedor();
	}

	public static void main(String[] args) {
		new PesquisarFornecedorUI().montarTela();
	}
	
	@Override
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

	private void prepararCabecalho() {
		JLabel label = new JLabel("Contém:");
		txtNome = new JTextField();
		txtNome.setBorder(BorderFactory.createTitledBorder("Nome"));
		
		btnPesquisar = new JButton(new ImageIcon("icons/pesquisar.png"));
		btnPesquisar.setActionCommand("Pesquisar");
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.addActionListener(this);
		
		painelCabecalho = new JPanel(new GridLayout(1, 3, 5, 5));
		painelCabecalho.add(label);
		painelCabecalho.add(txtNome);
		painelCabecalho.add(btnPesquisar);
		
		painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
	}
	
	private List<Fornecedor> atualizarLista() {
		String nome = txtNome.getText();
		List<Fornecedor> fornecedores;
		
		fornecedores = repo.recuperarPorNome(nome);
		return fornecedores;
	}
	
	private void atualizarTabela() {
		List<Fornecedor> fornecedores = atualizarLista();
		modelo.setRowCount(0);
		
		for (Fornecedor fornecedor : fornecedores) {
			modelo.addRow(new Object[]{fornecedor.getCnpj(), fornecedor.getNome(), fornecedor.getEndereco().toString()});
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
		
		switch (o) {
		case "Pronto":
			int linha = tabela.getSelectedRow();
			if (linha > -1) {
				String cnpj = (String) modelo.getValueAt(linha, 0);
				fornecedor = repo.recuperarPorCnpj(cnpj);
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
