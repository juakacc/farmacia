package br.com.farmacia.ui.model;

import java.awt.BorderLayout; 
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class FrameGerenciaUI extends FrameBasicoUI {

	public FrameGerenciaUI(String titulo) {
		super(titulo);
	}

	private JButton remover, voltar, editar, adicionar, btnPesquisar;
	private JPanel painelBotoes, painelCabecalho;
	protected JTextField txtNome;
	
	protected void prepararCabecalho() {
		JLabel label = new JLabel("Contém:");
		
		txtNome = new JTextField();
		txtNome.setBorder(BorderFactory.createTitledBorder("Nome"));
		
		btnPesquisar = new JButton(new ImageIcon("icons/pesquisar.png"));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setActionCommand("Pesquisar");
		btnPesquisar.addActionListener(this);
		
		painelCabecalho = new JPanel(new GridLayout(1, 3, 5, 5));
		painelCabecalho.add(label);
		painelCabecalho.add(txtNome);
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
		
		editar = new JButton(new ImageIcon("icons/editar.png"));
		editar.setToolTipText("Editar");
		editar.setActionCommand("Editar");
		editar.addActionListener(this);

		adicionar = new JButton(new ImageIcon("icons/adicionar.png"));
		adicionar.setToolTipText("Adicionar");
		adicionar.setActionCommand("Adicionar");
		adicionar.addActionListener(this);
		
		painelBotoes.add(voltar);
		painelBotoes.add(editar);
		painelBotoes.add(adicionar);
		painelBotoes.add(remover);
	}

}
