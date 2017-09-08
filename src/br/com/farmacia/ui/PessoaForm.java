package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.JXDatePicker;

import br.com.farmacia.model.Endereco;
import br.com.farmacia.model.Pessoa;
import br.com.farmacia.ui.model.FormularioUI;

public class PessoaForm extends FormularioUI {

	public static void main(String[] args) {
		new PessoaForm(true, new Pessoa(), false).montarTela();
	}

	private JTextField cpfTxt, nomeTxt, identidadeTxt, ruaTxt, numeroCasaTxt, bairroTxt, cidadeTxt,
			carteira_estudanteTxt, salarioTxt;
	private JCheckBox is_estudanteTxt;
	private JComboBox<String> funcaoTxt, estadosC;

	private JXDatePicker dt_nascTxt, dt_inicioTxt;

	private boolean func;
	private Pessoa pessoa;
	private List<String> telefones;
	private boolean editar;
	private JFormattedTextField[] tel;

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	/**
	 * Constrói um formulário para Pessoa
	 * 
	 * @param b
	 *            True se for funcionario, False caso contrário
	 * @param pessoa
	 *            Pessoa para ser editada ou vazia, caso contrário
	 * @param editar
	 *            True se for uma edição ou False, caso contrário
	 */
	public PessoaForm(boolean b, Pessoa pessoa, boolean editar) {
		super("Pessoa");
		this.func = b;
		this.pessoa = pessoa;
		this.telefones = pessoa.getTelefones();
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
			tel = new JFormattedTextField[5];
			for (int i = 0; i < tel.length; i++) {
				tel[i] = new JFormattedTextField(new MaskFormatter("(##)####-####"));
				tel[i].setBorder(BorderFactory.createTitledBorder("Telefone" + (i + 1)));
			}
		} catch (ParseException e) { }
		
		cpfTxt = new JTextField();
		cpfTxt.setBorder(BorderFactory.createTitledBorder("CPF"));
		nomeTxt = new JTextField();
		nomeTxt.setBorder(BorderFactory.createTitledBorder("Nome"));
		identidadeTxt = new JTextField();
		identidadeTxt.setBorder(BorderFactory.createTitledBorder("Identidade"));

		dt_nascTxt = new JXDatePicker();
		dt_nascTxt.setBorder(BorderFactory.createTitledBorder("Data de nascimento"));

		ruaTxt = new JTextField();
		ruaTxt.setBorder(BorderFactory.createTitledBorder("Rua"));
		numeroCasaTxt = new JTextField();
		numeroCasaTxt.setBorder(BorderFactory.createTitledBorder("Nº"));
		bairroTxt = new JTextField();
		bairroTxt.setBorder(BorderFactory.createTitledBorder("Bairro"));
		cidadeTxt = new JTextField();
		cidadeTxt.setBorder(BorderFactory.createTitledBorder("Cidade"));

		String es[] = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR",
				"PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" };
		estadosC = new JComboBox<>(es);
		estadosC.setBorder(BorderFactory.createTitledBorder("Estado"));

		is_estudanteTxt = new JCheckBox("Estudante");
		carteira_estudanteTxt = new JTextField();
		carteira_estudanteTxt.setBorder(BorderFactory.createTitledBorder("Carteira de estudante(opcional)"));

		dt_inicioTxt = new JXDatePicker();
		dt_inicioTxt.setBorder(BorderFactory.createTitledBorder("Data de início"));
		salarioTxt = new JTextField();
		salarioTxt.setBorder(BorderFactory.createTitledBorder("Salário"));

		String funcaoNome[] = { "Atendente", "Repositor", "Administrador", "Entregador" };
		funcaoTxt = new JComboBox<>(funcaoNome);
		funcaoTxt.setBorder(BorderFactory.createTitledBorder("Função"));

		if (editar) {
			cpfTxt.setText(pessoa.getCpf());
			cpfTxt.setEditable(false);

			nomeTxt.setText(pessoa.getNome());
			identidadeTxt.setText(pessoa.getIdentidade());
			dt_nascTxt.setDate(pessoa.getDataNascimento());
			Endereco e = pessoa.getEndereco();

			ruaTxt.setText(e.getRua());
			numeroCasaTxt.setText(e.getNumero() + "");
			bairroTxt.setText(e.getBairro());
			cidadeTxt.setText(e.getCidade());
			estadosC.setSelectedItem(e.getEstado());

			is_estudanteTxt.setSelected(pessoa.isEstudante());
			carteira_estudanteTxt.setText(pessoa.getCarteiraEstudante());

			if (pessoa.isFuncionario()) {
				dt_inicioTxt.setDate(pessoa.getDataInicio());
				salarioTxt.setText(pessoa.getSalario() + "");
				funcaoTxt.setSelectedItem(pessoa.getFuncao());
			}
		}

		if (func) {
			funcaoTxt.setEnabled(true);
			dt_inicioTxt.setEnabled(true);
			salarioTxt.setEnabled(true);
		} else {
			funcaoTxt.setEnabled(false);
			dt_inicioTxt.setEnabled(false);
			salarioTxt.setEnabled(false);
		}

		painelCampos = new JPanel(new GridLayout(8, 2, 5, 5));
		painelCampos.add(cpfTxt);
		painelCampos.add(nomeTxt);
		painelCampos.add(identidadeTxt);
		painelCampos.add(dt_nascTxt);
		painelCampos.add(ruaTxt);
		painelCampos.add(numeroCasaTxt);
		painelCampos.add(bairroTxt);
		painelCampos.add(cidadeTxt);
		painelCampos.add(estadosC);
		painelCampos.add(carteira_estudanteTxt);
		painelCampos.add(is_estudanteTxt);
		for (int i = 0; i < tel.length; i++) {
			painelCampos.add(tel[i]);
		}
		painelCampos.add(funcaoTxt);
		painelCampos.add(dt_inicioTxt);
		painelCampos.add(salarioTxt);

		painelPrincipal.add(painelCampos, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();

		switch (o) {
		case "Pronto":

			String cpf = cpfTxt.getText();
			String nome = nomeTxt.getText();
			String identidade = identidadeTxt.getText();
			Date dataNascimento = dt_nascTxt.getDate();

			String estado = (String) estadosC.getSelectedItem();
			String cidade = cidadeTxt.getText();
			String bairro = bairroTxt.getText();
			int numero_casa = Integer.parseInt(numeroCasaTxt.getText());
			String rua = ruaTxt.getText();
			Endereco endereco = new Endereco(rua, numero_casa, bairro, cidade, estado);

			boolean is_estudante = is_estudanteTxt.isSelected();
			String carteiraEstudante = carteira_estudanteTxt.getText();

			boolean is_funcionario = func;
			Date dataInicio = dt_inicioTxt.getDate();
			String funcao = (String) funcaoTxt.getSelectedItem();
			double salario = Double.parseDouble(salarioTxt.getText());

			List<String> tel = this.telefones;

			pessoa = new Pessoa(cpf, nome, identidade, dataNascimento, endereco, is_estudante, carteiraEstudante,
					is_funcionario, dataInicio, funcao, salario, tel);
			fecharJanela();
			break;

		case "Cancelar":
			pessoa = new Pessoa();
			fecharJanela();
			break;
		}
	}
}
