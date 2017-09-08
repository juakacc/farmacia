package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.farmacia.dao.CompraDao;
import br.com.farmacia.model.Compra;
import br.com.farmacia.model.Fornecedor;
import br.com.farmacia.model.Item;
import br.com.farmacia.model.Medicamento;
import br.com.farmacia.ui.aux.Valida;
import br.com.farmacia.ui.fornecedor.PesquisarFornecedorUI;
import br.com.farmacia.ui.medicamento.PesquisarMedicamentoUI;
import br.com.farmacia.ui.model.FrameConfirmUI;


public class CompraUI extends FrameConfirmUI {

	private JPanel painelCabecalho, painelLateral;
	private JTextField txtFornecedor;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JButton addMedicamento, rmMedicamento, btnSelForn;

	private List<Item> itens;
	private Fornecedor fornecedor;
	private CompraDao repo;
	
	public static void main(String[] args) {
		new CompraUI().montarTela();
	}
	
	public CompraUI() {
		super("Compra");
		this.repo = CompraDao.getInstancia();
		fornecedor = new Fornecedor();
		this.itens = new ArrayList<>();
	}
	
	public void montarTela() {
		prepararJanela();
		prepararPainelPrincipal();
		prepararCabecalho();
		
		prepararTabela();
		prepararPainelBotoes();
		prepararBotoes();
		prepararPainelLateral();

		mostrarJanela();
	}

	private void prepararCabecalho() {
		txtFornecedor = new JTextField();
		txtFornecedor.setBorder(BorderFactory.createTitledBorder("Fornecedor"));
		txtFornecedor.setEditable(false);
		
		btnSelForn = new JButton("Fornecedor");
		btnSelForn.setActionCommand("Fornecedor");
		btnSelForn.addActionListener(this);
		
		painelCabecalho = new JPanel(new GridLayout(1, 2, 5, 5));
		painelCabecalho.add(txtFornecedor);
		painelCabecalho.add(btnSelForn);
		
		painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("ID");
		modelo.addColumn("Produto");
		modelo.addColumn("Laboratório");
		modelo.addColumn("Concentração");
		modelo.addColumn("Quantidade");
		
		JScrollPane sroll = new JScrollPane(tabela);
		
		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}

	private void prepararPainelLateral() {
		addMedicamento = new JButton(new ImageIcon("icons/adicionar.png"));
		addMedicamento.setActionCommand("Adicionar");
		addMedicamento.addActionListener(this);
		
		rmMedicamento = new JButton(new ImageIcon("icons/remover.png"));
		rmMedicamento.setActionCommand("Remover");
		rmMedicamento.addActionListener(this);
		
		painelLateral = new JPanel(new GridLayout(2, 1, 5, 5));
		painelLateral.add(addMedicamento);
		painelLateral.add(rmMedicamento);
		
		painelPrincipal.add(painelLateral, BorderLayout.EAST);
	}
	
	private void adicionarNaTabela(Item i) {
		Medicamento m = i.getMedicamento();
		modelo.addRow(new Object[]{
			m.getId(), 
			m.getNome(), 
			m.getLaboratorio(), 
			m.getConcentracao(), 
			i.getQuantidade()});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		
		switch (o) {
		case "Pronto":
			
			if (Valida.validarCompra(fornecedor, itens)) {
				boolean gravou = false;
				
				for (Item item : itens) {
					Medicamento medicamento = item.getMedicamento();
					int qtd = item.getQuantidade();
					Compra compra = new Compra(fornecedor, medicamento, qtd, new Date());
					gravou = repo.adicionar(compra);
				}
				
				if (gravou) {
					JOptionPane.showMessageDialog(null, "Adicionado com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro");
				}
				fecharJanela();
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, verifique o fornecedor e itens!");
			}
			break;
		case "Cancelar":
			fecharJanela();
			break;
		case "Fornecedor":
			PesquisarFornecedorUI p = new PesquisarFornecedorUI();
			p.montarTela();
			fornecedor = p.getFornecedor();
			
			txtFornecedor.setText(fornecedor.getNome());
			break;
			
		case "Adicionar":	//Adicionar Medicamento - Apenas medicamentos cadastrados
			PesquisarMedicamentoUI pM = new PesquisarMedicamentoUI();
			pM.montarTela();
			Medicamento medicamento = pM.getMedicamento();
			
			MedicamentoCompraUI mc = new MedicamentoCompraUI(medicamento);
			mc.montarTela();
			medicamento = mc.getMedicamento();
			int quantidade = mc.getQtd();
			if (quantidade > 0) {
				verificarEAdd(medicamento, quantidade);
			}
			break;
		case "Remover":	// Remover Medicamento
			int linha = tabela.getSelectedRow();
			if (linha > -1) {
				int id = (int) modelo.getValueAt(linha, 0);
				
				for (Item item : itens) {
					if (item.getMedicamento().getId() == id) {
						itens.remove(item);
						break;
					}
				}
				modelo.removeRow(linha);
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");
			}
			break;
		}
		
	}

	private void verificarEAdd(Medicamento medicamento, int qtd) {
		if (!medicamento.isNull()) {
			Item item = new Item(medicamento, qtd);
			itens.add(item);
			
			adicionarNaTabela(item);
		}
	}
}
