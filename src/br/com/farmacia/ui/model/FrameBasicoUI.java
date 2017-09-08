package br.com.farmacia.ui.model;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class FrameBasicoUI implements ActionListener {

	protected JFrame dono;
	
	protected JDialog janela;
	protected JPanel painelPrincipal;
	private String titulo;
	
	public FrameBasicoUI(String titulo) {
		this.titulo = titulo;
	}
	
	public abstract void montarTela();
	
	protected void prepararPainelPrincipal() {
		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout(5, 5));
		janela.add(painelPrincipal);
	}

	protected void mostrarJanela() {
		janela.pack();
		janela.setSize(500, 500);
		//janela.setLocationRelativeTo(dono);
		janela.setVisible(true);
	}

	protected void prepararJanela() {
		janela = new JDialog(dono, true);
		janela.setTitle(titulo);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	protected void fecharJanela() {
		janela.dispose();
	}
}
