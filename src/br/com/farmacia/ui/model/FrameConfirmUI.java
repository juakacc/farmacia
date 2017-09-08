package br.com.farmacia.ui.model;

import java.awt.BorderLayout; 
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class FrameConfirmUI extends FrameBasicoUI {

	public FrameConfirmUI(String titulo) {
		super(titulo);
	}

	protected JPanel painelBotoes;
	protected JButton pronto, cancelar;
	
	protected void prepararPainelBotoes() {
		painelBotoes = new JPanel();
		painelBotoes.setLayout(new GridLayout(1, 2, 5, 5));
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
	}

	protected void prepararBotoes() {
		pronto = new JButton(new ImageIcon("icons/pronto.png"));
		pronto.setToolTipText("Pronto");
		pronto.setActionCommand("Pronto");
		
		cancelar = new JButton(new ImageIcon("icons/cancelar.png"));
		cancelar.setActionCommand("Cancelar");
		cancelar.setToolTipText("Cancelar");
		
		pronto.addActionListener(this);
		cancelar.addActionListener(this);
		
		painelBotoes.add(pronto);
		painelBotoes.add(cancelar);
	}
	
}
