package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.farmacia.model.Endereco;
import br.com.farmacia.model.Fornecedor;
import br.com.farmacia.ui.model.FormularioUI;

public class FornecedorForm extends FormularioUI {

	public static void main(String[] args) {
		new FornecedorForm(new Fornecedor(), false).montarTela();
	}

	private JFormattedTextField cnpjTxt, numero_casaTxt, tel[];
	
	private JTextField nomeTxt, ruaTxt, bairroTxt, cidadeTxt;
	private List<String> telefones = new ArrayList<>();
	
	private JComboBox<String> estadosC;	
	
	private Fornecedor fornecedor;
	private boolean editar;
	
	public Fornecedor getFornecedor() {
		return this.fornecedor;
	}
	
	/**
	 * @param editar True se for edição, False caso contrário
	 * */
	public FornecedorForm(Fornecedor f, boolean editar) {
		super("Fornecedor");
		this.fornecedor = f;
		this.editar = editar;
	}
	
	public void montarTela() {
		prepararJanela();
		prepararPainelPrincipal();
		
		prepararPainelBotoes();
		prepararBotoes();
		prepararCampos();
		
		mostrarJanela();
	}

	private void prepararCampos() {
		try {
			cnpjTxt = new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
			numero_casaTxt = new JFormattedTextField(new MaskFormatter("####"));
			
			tel = new JFormattedTextField[5];
			for (int i = 0; i < tel.length; i++) {
				tel[i] = new JFormattedTextField(new MaskFormatter("(##)####-####"));
				tel[i].setBorder(BorderFactory.createTitledBorder("Telefone" + (i+1)));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		cnpjTxt.setBorder(BorderFactory.createTitledBorder("CPF"));
		
		nomeTxt = new JTextField();
		nomeTxt.setBorder(BorderFactory.createTitledBorder("Nome"));
		ruaTxt = new JTextField();
		ruaTxt.setBorder(BorderFactory.createTitledBorder("Rua"));
		
		numero_casaTxt.setBorder(BorderFactory.createTitledBorder("Nº"));
		bairroTxt = new JTextField();
		bairroTxt.setBorder(BorderFactory.createTitledBorder("Bairro"));
		cidadeTxt = new JTextField();
		cidadeTxt.setBorder(BorderFactory.createTitledBorder("Cidade"));
		
		String es[] = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", 
				"MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", 
				"RO", "RR", "SC", "SP", "SE", "TO"}; 
		estadosC = new JComboBox<>(es);
		estadosC.setBorder(BorderFactory.createTitledBorder("Estado"));
		
		if (editar) {
			cnpjTxt.setText(fornecedor.getCnpj());
			nomeTxt.setText(fornecedor.getNome());
			Endereco e = fornecedor.getEndereco();
			ruaTxt.setText(e.getRua());
			numero_casaTxt.setText("" + e.getNumero());
			bairroTxt.setText(e.getBairro());
			cidadeTxt.setText(e.getCidade());
			estadosC.setSelectedItem(e.getEstado());
			
			List<String> telefones = fornecedor.getTelefones();
			for (int i = 0; i < tel.length; i++) {
				tel[i].setText(telefones.get(i));
			} 
		}
		
		painelCampos = new JPanel(new GridLayout(5, 2, 5, 5));
		painelCampos.add(cnpjTxt);
		painelCampos.add(nomeTxt);
		painelCampos.add(ruaTxt);
		painelCampos.add(numero_casaTxt);
		painelCampos.add(bairroTxt);
		painelCampos.add(cidadeTxt);
		painelCampos.add(estadosC);
		for (int i = 0; i < tel.length; i++) {
			painelCampos.add(tel[i]);
		}
		
		painelPrincipal.add(painelCampos, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		
		switch (o) {
		case "Pronto":
			// Recupero os dados e salvo em fornecedor
			String cnpj, nome, rua, bairro, cidade, estado;
			int numero_casa;
			
			cnpj = cnpjTxt.getText().replaceAll("[./-]", "");
			nome = nomeTxt.getText();
			rua = ruaTxt.getText();
			numero_casa = Integer.parseInt(numero_casaTxt.getText());
			bairro = bairroTxt.getText();
			cidade = cidadeTxt.getText();
			estado = (String) estadosC.getSelectedItem();
			
			Endereco endereco = new Endereco(rua, numero_casa, bairro, cidade, estado);
			fornecedor = new Fornecedor(cnpj, nome, endereco, telefones);
			fecharJanela();
			break;

		case "Cancelar":
			fornecedor = new Fornecedor();
			fecharJanela();
			break;
		}
	}
}
