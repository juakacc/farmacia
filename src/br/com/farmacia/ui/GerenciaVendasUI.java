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

import br.com.farmacia.dao.VendaDao;
import br.com.farmacia.model.Venda;
import br.com.farmacia.ui.model.FrameBasicoUI;
import static br.com.farmacia.ui.aux.Recuperar.*;

public class GerenciaVendasUI extends FrameBasicoUI {

	private JTable tabela;
	private DefaultTableModel modelo;
	
	private JPanel painelBotoes;
	private JPanel painelCabecalho;
	private JButton btnPesquisar, remover, voltar;
	private JXDatePicker inicio, fim;
	
	private VendaDao repo;
	
	public GerenciaVendasUI() {
		super("Gerenciar Vendas");
		this.repo = VendaDao.getInstancia();
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
		
		List<Venda> vendas = repo.recuperarPorData(i, f);
		
		for (Venda venda : vendas ) {
			modelo.addRow(new Object[]{
				venda.getId(), 
				venda.getCliente().getNome(), 
				venda.getFuncionario().getNome(), 
				traduzirDataMostrar(venda.getData()), 
				venda.getValor()});
		}
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		modelo.addColumn("ID");
		modelo.addColumn("Cliente");
		modelo.addColumn("Funcionario"); // concatenado
		modelo.addColumn("Data");
		modelo.addColumn("Valor");
		
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

	
	// Concertarrrrrrrrrrrrrr
	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		int linha = tabela.getSelectedRow();
		
		Venda venda;
		
		switch(o) {
		case "Voltar":
			fecharJanela();
			break;
		case "Remover": // Tenho que verificar as restrições antes de remover
			if (linha > -1) {
				int id = (int) modelo.getValueAt(linha, 0);
				venda = repo.recuperarPorId(id);
				
				if (repo.remover(venda)) {
					JOptionPane.showMessageDialog(null, "Removido com sucesso");
					atualizarTabela();
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
