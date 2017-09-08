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

import br.com.farmacia.dao.MedicamentoDao;
import br.com.farmacia.model.Medicamento;
import br.com.farmacia.ui.model.FrameConfirmUI;

public class PesquisarMedicamentoUI extends FrameConfirmUI {

	private JPanel painelCabecalho;
	private JTextField txtNome;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JButton btnSelect;
	
	private Medicamento medicamento;
	private MedicamentoDao repo;
	
	public PesquisarMedicamentoUI() {
		super("Pesquisar Medicamento");
		this.medicamento = new Medicamento();
		this.repo = MedicamentoDao.getInstancia();
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
		prepararPainelBotoes();
		prepararBotoes();
		
		mostrarJanela();
	}

	private void prepararCabecalho() {
		JLabel label = new JLabel("Contém:");
		txtNome = new JTextField();
		txtNome.setBorder(BorderFactory.createTitledBorder("Nome"));
		
		btnSelect = new JButton(new ImageIcon("icons/pesquisar.png"));
		btnSelect.setActionCommand("Pesquisar");
		btnSelect.setToolTipText("Pesquisar");
		btnSelect.addActionListener(this);
		
		painelCabecalho = new JPanel(new GridLayout(1, 3, 5, 5));
		painelCabecalho.add(label);
		painelCabecalho.add(txtNome);
		painelCabecalho.add(btnSelect);
		
		painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
	}
	
	private List<Medicamento> atualizarLista() {
		String nome = txtNome.getText();
		List<Medicamento> medicamentos;
		
		medicamentos = repo.recuperarPorNome(nome);
		return medicamentos;
	}
	
	private void atualizarTabela() {
		List<Medicamento> medicamentos = atualizarLista();
		modelo.setRowCount(0);
		
		for (Medicamento med : medicamentos) {	// Ver quais mais atributos exibir
			modelo.addRow(new Object[]{med.getId(), med.getNome(), med.getValor_venda()});
		}
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("ID");
		modelo.addColumn("Nome");
		modelo.addColumn("Valor"); // concatenado
		
		atualizarTabela();
		JScrollPane sroll = new JScrollPane(tabela);

		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}

	public Medicamento getMedicamento() {
		return this.medicamento;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		
		switch (o) {
		case "Pronto":
			int linha = tabela.getSelectedRow();
			if (linha > -1) {
				int id = (int) modelo.getValueAt(linha, 0);
				
				medicamento = repo.recuperarPorId(id);
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
