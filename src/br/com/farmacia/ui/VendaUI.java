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

import br.com.farmacia.dao.VendaDao;
import br.com.farmacia.model.Item;
import br.com.farmacia.model.Medicamento;
import br.com.farmacia.model.Pessoa;
import br.com.farmacia.model.Venda;
import br.com.farmacia.ui.aux.Recuperar;
import br.com.farmacia.ui.aux.Valida;
import br.com.farmacia.ui.medicamento.PesquisarMedicamentoUI;
import br.com.farmacia.ui.model.FrameConfirmUI;
import br.com.farmacia.ui.pessoa.PesquisarPessoaUI;

public class VendaUI extends FrameConfirmUI {

	private JPanel painelCabecalho, painelLateral;
	private JTextField txtClie, txtFunc;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JButton btnAdicionar, btnRemover, btnSelFunc, btnSelClie;
	
	private VendaDao repo;
	private Pessoa cliente, funcionario;
	private List<Item> itens;

	public VendaUI() {
		super("Venda");
		this.cliente = new Pessoa();
		this.funcionario = new Pessoa();
		this.repo = VendaDao.getInstancia();
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
		txtClie = new JTextField();
		txtClie.setBorder(BorderFactory.createTitledBorder("Cliente"));
		txtClie.setEditable(false);
		
		txtFunc = new JTextField();
		txtFunc.setBorder(BorderFactory.createTitledBorder("Funcionário"));
		txtFunc.setEditable(false);
		
		btnSelClie = new JButton("Cliente");
		btnSelClie.setActionCommand("Cliente");
		btnSelClie.addActionListener(this);
		
		btnSelFunc = new JButton("Funcionário");
		btnSelFunc.setActionCommand("Funcionario");
		btnSelFunc.addActionListener(this);

		painelCabecalho = new JPanel(new GridLayout(2, 2, 5, 5));
		painelCabecalho.add(txtClie);
		painelCabecalho.add(btnSelClie);
		painelCabecalho.add(txtFunc);
		painelCabecalho.add(btnSelFunc);
		
		painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("ID");
		modelo.addColumn("Produto");
		modelo.addColumn("Laboratório");
		modelo.addColumn("Concentração");
		modelo.addColumn("Preço Un.");
		modelo.addColumn("Quantidade");
		
		JScrollPane sroll = new JScrollPane(tabela);
		
		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}
	
	private void adicionarNaTabela(Medicamento m, int qtd) {
		modelo.addRow(new Object[]{m.getId(), m.getNome(), m.getLaboratorio(), 
				m.getConcentracao(), m.getValor_venda(), qtd});
	}

	private void prepararPainelLateral() {

		btnAdicionar = new JButton(new ImageIcon("icons/adicionar.png"));
		btnAdicionar.setToolTipText("Adicionar Medicamento");
		btnAdicionar.setActionCommand("Adicionar");
		
		btnRemover = new JButton(new ImageIcon("icons/remover.png"));
		btnRemover.setToolTipText("Remover Medicamento");
		btnRemover.setActionCommand("Remover");
		
		btnAdicionar.addActionListener(this);
		btnRemover.addActionListener(this);
		
		painelLateral = new JPanel(new GridLayout(2, 1, 5, 5));
		painelLateral.add(btnAdicionar);
		painelLateral.add(btnRemover);
		
		painelPrincipal.add(painelLateral, BorderLayout.EAST);
	}
	
	protected void mostrarJanela() {
		janela.pack();
		janela.setSize(900, 500);
		janela.setLocationRelativeTo(dono);
		janela.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		
		switch (o) {
		case "Pronto":
			// validar e gravar no banco
			if (Valida.validarVenda(cliente, funcionario, itens)) {
				Venda v = new Venda(cliente, funcionario, itens, new Date());
				
				if (repo.adicionar(v)) {
					JOptionPane.showMessageDialog(null, "Adicionado com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro");
				}
				fecharJanela();
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, verifique o cliente, funcionário e itens!");
			}
			break;

		case "Cancelar":
			fecharJanela();
			break;
		case "Adicionar":
			PesquisarMedicamentoUI m = new PesquisarMedicamentoUI();
			m.montarTela();
			Medicamento medi = m.getMedicamento();
			
			if (!medi.isNull()) {
				int qtd = Recuperar.recuperarInt("Qual a quantidade? ");
				if (qtd > 0) {
					adicionarNaTabela(medi, qtd);
					Item i = new Item(medi, qtd);
					itens.add(i);
				} else {
					JOptionPane.showMessageDialog(null, "Quantidade inválida");
				}
			}
			break;
		case "Remover":
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
		case "Cliente":
			PesquisarPessoaUI c = new PesquisarPessoaUI(false);
			c.montarTela();
			cliente = c.getPessoa();
			txtClie.setText(cliente.getNome());
			break;
		case "Funcionario":
			PesquisarPessoaUI f = new PesquisarPessoaUI(true);
			f.montarTela();
			funcionario = f.getPessoa();
			txtFunc.setText(funcionario.getNome());
			break;
		}
	}
}
