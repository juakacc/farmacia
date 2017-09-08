package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import br.com.farmacia.dao.CompraDao;
import br.com.farmacia.model.Compra;
import br.com.farmacia.ui.model.FrameBasicoUI;
import static br.com.farmacia.ui.aux.Recuperar.*;

public class GerenciaComprasUI extends FrameBasicoUI {

	private JTable tabela;
	private DefaultTableModel modelo;
	
	private JPanel painelBotoes;
	private JPanel painelCabecalho;
	private JButton btnPesquisar, remover, voltar;
	private JXDatePicker inicio, fim;
	
	private CompraDao repo;
	
	public GerenciaComprasUI() {
		super("Gerenciar Compras");
		this.repo = CompraDao.getInstancia();
	}
	

	public static void main(String[] args) {
		new GerenciaVendasUI().montarTela();
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
	
	private void atualizarTabela() {
		modelo.setRowCount(0);
		Date i = inicio.getDate();
		Date f = fim.getDate();

		List<Compra> compras = repo.recuperarPorData(i, f);
		
		for (Compra compra : compras ) {
			modelo.addRow(new Object[]{
				compra.getId(), 
				compra.getFornecedor().getNome(), 
				compra.getMedicamento().getNome(),
				traduzirDataMostrar(compra.getData()), 
				compra.getValor()});
		}
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("ID");
		modelo.addColumn("Fornecedor");
		modelo.addColumn("Medicamento"); // concatenado
		modelo.addColumn("Data");
		modelo.addColumn("Valor Compra");
		
		JScrollPane sroll = new JScrollPane(tabela);
		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}
	
	protected void prepararCabecalho() {
		
		inicio = new JXDatePicker();
		inicio.setBorder(BorderFactory.createTitledBorder("Início"));
		inicio.setDate(new Date());
		
		fim = new JXDatePicker();
		fim.setBorder(BorderFactory.createTitledBorder("Fim"));
		fim.setDate(new Date());
		
		btnPesquisar = new JButton(new ImageIcon("icons/pesquisar.png"));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setActionCommand("Pesquisar");
		btnPesquisar.addActionListener(this);
		
		painelCabecalho = new JPanel(new GridLayout(1, 3, 5, 5));
		painelCabecalho.add(inicio);
		painelCabecalho.add(fim);
		painelCabecalho.add(btnPesquisar);
		
		painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
	}
	
	protected void prepararPainelBotoes() {
		painelBotoes = new JPanel();
		painelBotoes.setLayout(new GridLayout(1, 4, 5, 5));
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
	}
	
	protected void prepararBotoes() {
		remover = new JButton(new ImageIcon("icons/cancelar.png"));
		remover.setToolTipText("Remover");
		remover.setActionCommand("Remover");
		remover.addActionListener(this);
		
		voltar = new JButton(new ImageIcon("icons/voltar.png"));
		voltar.setToolTipText("Voltar");
		voltar.setActionCommand("Voltar");
		voltar.addActionListener(this);
		
		painelBotoes.add(voltar);
		painelBotoes.add(remover);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		int linha = tabela.getSelectedRow();
		
		Compra compra;
		
		switch(o) {
		case "Voltar":
			fecharJanela();
			break;
		case "Remover": 
			if (linha > -1) {
				int id = (int) modelo.getValueAt(linha, 0);
				compra = repo.recuperarPorId(id);
				
				if (repo.remover(compra)) {
					JOptionPane.showMessageDialog(null, "Removido com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Aconteceu algum erro durante a remoção");					
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			atualizarTabela();
			break;
		case "Pesquisar":
			atualizarTabela();
			break;
		}
	}

}
