package br.com.farmacia.ui.fornecedor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.farmacia.model.Endereco;
import br.com.farmacia.model.Fornecedor;
import static br.com.farmacia.ui.aux.Recuperar.*;
import br.com.farmacia.ui.model.FormularioUI;

public class FornecedorForm extends FormularioUI {

	public static void main(String[] args) {
		new FornecedorForm(new Fornecedor(), false).montarTela();
	}

	private JFormattedTextField cnpjTxt, tel[];

	JTextField numero_casaTxt;

	private JTextField nomeTxt, ruaTxt, bairroTxt, cidadeTxt;
	private JComboBox<String> estadosC;

	private Fornecedor fornecedor;
	private boolean editar;

	public Fornecedor getFornecedor() {
		return this.fornecedor;
	}

	/**
	 * @param editar
	 *            True se for edição, False caso contrário
	 */
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

			tel = new JFormattedTextField[5];
			for (int i = 0; i < tel.length; i++) {
				tel[i] = new JFormattedTextField(new MaskFormatter("(##)####-####"));
				tel[i].setBorder(BorderFactory.createTitledBorder("Telefone" + (i + 1)));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		cnpjTxt.setBorder(BorderFactory.createTitledBorder("CNPJ"));

		nomeTxt = new JTextField();
		nomeTxt.setBorder(BorderFactory.createTitledBorder("Nome"));
		ruaTxt = new JTextField();
		ruaTxt.setBorder(BorderFactory.createTitledBorder("Rua"));
		numero_casaTxt = new JTextField();
		numero_casaTxt.setBorder(BorderFactory.createTitledBorder("Nº"));

		bairroTxt = new JTextField();
		bairroTxt.setBorder(BorderFactory.createTitledBorder("Bairro"));
		cidadeTxt = new JTextField();
		cidadeTxt.setBorder(BorderFactory.createTitledBorder("Cidade"));

		String es[] = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR",
				"PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };
		estadosC = new JComboBox<>(es);
		estadosC.setBorder(BorderFactory.createTitledBorder("Estado"));

		if (editar) {
			cnpjTxt.setEditable(false);
			cnpjTxt.setText(fornecedor.getCnpj());
			nomeTxt.setText(fornecedor.getNome());
			Endereco e = fornecedor.getEndereco();
			ruaTxt.setText(e.getRua());
			numero_casaTxt.setText("" + e.getNumero());
			bairroTxt.setText(e.getBairro());
			cidadeTxt.setText(e.getCidade());
			estadosC.setSelectedItem(e.getEstado());

			List<String> telefones = fornecedor.getTelefones();
			for (int i = 0; i < tel.length && i < telefones.size(); i++) {
				tel[i].setText(telefones.get(i));
			}
		}

		painelCampos = new JPanel(new GridLayout(6, 2, 5, 5));
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
			int numero_casa = 0;
			boolean validado = true;

			cnpj = traduzirCnpjProBanco(cnpjTxt.getText());
			if (cnpj.trim().isEmpty()) {
				validado = false;
			}
			nome = nomeTxt.getText();
			if (nome.trim().isEmpty()) {
				validado = false;
			}

			try {
				numero_casa = Integer.parseInt(numero_casaTxt.getText());
			} catch (NumberFormatException ex) {
				validado = false;
			}
			
			rua = ruaTxt.getText();
			if (rua.trim().isEmpty()) {
				validado = false;
			}
			bairro = bairroTxt.getText();
			if (bairro.trim().isEmpty()) {
				validado = false;
			}
			cidade = cidadeTxt.getText();
			if (cidade.trim().isEmpty()) {
				validado = false;
			}
			estado = (String) estadosC.getSelectedItem();

			Endereco endereco = new Endereco(rua, numero_casa, bairro, cidade, estado);
			List<String> telefones = new ArrayList<>();

			for (int i = 0; i < tel.length; i++) {
				String telefone = traduzirTelProBanco(tel[i].getText());

				if (!telefone.trim().isEmpty()) {
					telefones.add(telefone);
				}
			}
			
			if (validado) {
				fornecedor = new Fornecedor(cnpj, nome, endereco, telefones);
				fecharJanela();
			} else {
				JOptionPane.showMessageDialog(null, "Porfavor, verifique os dados!");
			}
			break;

		case "Cancelar":
			fornecedor = new Fornecedor();
			fecharJanela();
			break;
		}
	}
}
