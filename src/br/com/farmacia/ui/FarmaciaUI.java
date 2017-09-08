package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.farmacia.ui.fornecedor.GerenciaFornecedorUI;
import br.com.farmacia.ui.medicamento.GerenciaMedicamentoUI;
import br.com.farmacia.ui.pessoa.GerenciaPessoaUI;

public class FarmaciaUI implements ActionListener {

	public static void main(String[] args) {
		new FarmaciaUI().montarTela();
	}

	private JFrame janela;
	private JPanel painelPrincipal, painelBotoes;
	private JButton btnClientes, btnFuncionarios, btnFornecedores, btnEstoque, 
		btnVenda, btnCompra, btnSair, btnGVendas, btnGCompras;

	public void montarTela() {
		prepararJanela();
		prepararPainelPrincipal();
		
		prepararPainelBotoes();
		prepararBotoes();
		
		mostrarJanela();
	}

	private void prepararBotoes() {
		btnClientes = new JButton(new ImageIcon("icons/cliente.png"));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setActionCommand("Clientes");
		btnClientes.addActionListener(this);
		
		btnFuncionarios = new JButton(new ImageIcon("icons/funcionario.png"));
		btnFuncionarios.setToolTipText("Funcionários");
		btnFuncionarios.setActionCommand("Funcionarios");
		btnFuncionarios.addActionListener(this);
		
		btnFornecedores = new JButton(new ImageIcon("icons/fornecedor.png"));
		btnFornecedores.setToolTipText("Fornecedores");
		btnFornecedores.setActionCommand("Fornecedores");
		btnFornecedores.addActionListener(this);
		
		btnEstoque = new JButton(new ImageIcon("icons/estoque.png"));
		btnEstoque.setToolTipText("Estoque");
		btnEstoque.setActionCommand("Estoque");
		btnEstoque.addActionListener(this);
		
		btnGVendas = new JButton(new ImageIcon("icons/gVendas.png"));
		btnGVendas.setToolTipText("Gerenciar Vendas");
		btnGVendas.setActionCommand("GerVendas");
		btnGVendas.addActionListener(this);
		
		btnGCompras = new JButton(new ImageIcon("icons/gCompras.png"));
		btnGCompras.setToolTipText("Gerenciar Compras");
		btnGCompras.setActionCommand("GerCompras");
		btnGCompras.addActionListener(this);
		
		btnVenda = new JButton(new ImageIcon("icons/venda.png"));
		btnVenda.setToolTipText("Venda");
		btnVenda.setActionCommand("Venda");
		btnVenda.addActionListener(this);
		
		btnCompra = new JButton(new ImageIcon("icons/compra.png"));
		btnCompra.setToolTipText("Compra");
		btnCompra.setActionCommand("Compra");
		btnCompra.addActionListener(this);
		
		btnSair = new JButton(new ImageIcon("icons/sair.png"));
		btnSair.setToolTipText("Sair");
		btnSair.setActionCommand("Sair");
		btnSair.addActionListener(this);
		
		painelBotoes.add(btnClientes);
		painelBotoes.add(btnFuncionarios);
		painelBotoes.add(btnFornecedores);
		painelBotoes.add(btnEstoque);
		painelBotoes.add(btnVenda);
		painelBotoes.add(btnCompra);
		painelBotoes.add(btnGVendas);
		painelBotoes.add(btnGCompras);
		painelBotoes.add(btnSair);
	}

	private void prepararPainelBotoes() {
		painelBotoes = new JPanel();
		painelBotoes.setLayout(new GridLayout(3, 3, 5, 5));
		painelPrincipal.add(painelBotoes);
	}

	private void prepararPainelPrincipal() {
		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());
		janela.add(painelPrincipal);
	}

	private void mostrarJanela() {
		janela.pack();
		janela.setSize(500, 500);
		janela.setVisible(true);
	}

	private void prepararJanela() {
		janela = new JFrame("Farmácia Estudantil");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		
		switch (o) {
		case "Clientes":
			new GerenciaPessoaUI(false).montarTela();
			break;
		case "Funcionarios":
			new GerenciaPessoaUI(true).montarTela();
			break;
		case "Fornecedores":
			new GerenciaFornecedorUI(janela).montarTela();
			break;
		case "Estoque":
			new GerenciaMedicamentoUI().montarTela();
			break;
		case "GerVendas":
			new GerenciaVendasUI().montarTela();
			break;
		case "GerCompras":
			new GerenciaComprasUI().montarTela();
			break;
		case "Venda":
			new VendaUI().montarTela();
			break;
		case "Compra":
			new CompraUI().montarTela(); // Verificar aqui!!!!!!
			break;
		case "Sair":
			System.exit(JFrame.EXIT_ON_CLOSE);
			break;
		}
	}
}
