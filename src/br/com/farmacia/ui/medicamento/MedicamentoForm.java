package br.com.farmacia.ui.medicamento;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import br.com.farmacia.model.Medicamento;
import br.com.farmacia.ui.model.FormularioUI;

public class MedicamentoForm extends FormularioUI {

	private JTextField numero_msTxt, nomeTxt, labTxt, codigo_barrasTxt, loteTxt, concentracaoTxt, valor_compraTxt,
			valor_vendaTxt, quantidadeTxt;
	private JComboBox<String> formaCb, categoriaCb;

	private Medicamento medicamento;
	private int id;
	private boolean editar;
	private JXDatePicker dt_compraTxt, dt_validadeTxt;

	public static void main(String[] args) {
		new MedicamentoForm(new Medicamento(), false).montarTela();
	}

	/**
	 * Constrói um novo formulário para medicamento
	 * 
	 * @param medicamento
	 *            Medicamento para ser editado ou um vazio, caso seja um novo
	 *            cadastro
	 * @param e
	 *            True se for uma edição ou False, caso contrário
	 */
	public MedicamentoForm(Medicamento medicamento, boolean e) {
		super("Medicamento");
		this.medicamento = medicamento;
		this.id = medicamento.getId();
		this.editar = e;
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

		nomeTxt = new JTextField();
		nomeTxt.setBorder(BorderFactory.createTitledBorder("Nome"));
		numero_msTxt = new JTextField();
		numero_msTxt.setBorder(BorderFactory.createTitledBorder("Número MS"));
		labTxt = new JTextField();
		labTxt.setBorder(BorderFactory.createTitledBorder("Laboratório"));
		codigo_barrasTxt = new JTextField();
		codigo_barrasTxt.setBorder(BorderFactory.createTitledBorder("Código de barras"));
		loteTxt = new JTextField();
		loteTxt.setBorder(BorderFactory.createTitledBorder("Lote"));
		concentracaoTxt = new JTextField(); // double
		concentracaoTxt.setBorder(BorderFactory.createTitledBorder("Concentração"));

		dt_compraTxt = new JXDatePicker();
		dt_compraTxt.setBorder(BorderFactory.createTitledBorder("Data de compra"));
		dt_compraTxt.setEditable(false);

		dt_validadeTxt = new JXDatePicker();
		dt_validadeTxt.setBorder(BorderFactory.createTitledBorder("Data de validade"));
		dt_validadeTxt.setEditable(false);

		valor_compraTxt = new JTextField(); // double
		valor_compraTxt.setBorder(BorderFactory.createTitledBorder("Valor de compra"));
		valor_compraTxt.setEditable(false);

		valor_vendaTxt = new JTextField(); // double
		valor_vendaTxt.setBorder(BorderFactory.createTitledBorder("Valor de venda"));
		valor_vendaTxt.setEditable(false);

		quantidadeTxt = new JTextField(); // int
		quantidadeTxt.setBorder(BorderFactory.createTitledBorder("Quantidade"));
		quantidadeTxt.setEditable(false);

		String formas[] = { "Comprimido", "Cápsula", "Liquido" };
		formaCb = new JComboBox<>(formas);
		formaCb.setBorder(BorderFactory.createTitledBorder("Forma"));

		String categorias[] = { "Ético", "Genérico", "Similar", "Produto oficinal", "Envelopado", "Outra" };
		categoriaCb = new JComboBox<>(categorias);
		categoriaCb.setBorder(BorderFactory.createTitledBorder("Categoria"));

		if (editar) {
			nomeTxt.setText(medicamento.getNome());
			numero_msTxt.setText(medicamento.getNumero_ms());
			labTxt.setText(medicamento.getLaboratorio());
			codigo_barrasTxt.setText(medicamento.getCodigo_barras());
			loteTxt.setText(medicamento.getLote());
			concentracaoTxt.setText(medicamento.getConcentracao() + "");

			dt_compraTxt.setDate(medicamento.getData_compra());
			dt_validadeTxt.setDate(medicamento.getData_validade());

			valor_compraTxt.setText(medicamento.getValor_compra() + "");
			valor_vendaTxt.setText(medicamento.getValor_venda() + "");
			quantidadeTxt.setText(medicamento.getQuantidade_estoque() + ""); // Verificar se é necessário

			formaCb.setSelectedItem(medicamento.getForma());
			categoriaCb.setSelectedItem(medicamento.getCategoria());
			
			dt_validadeTxt.setEditable(true);
			valor_compraTxt.setEditable(true);
			valor_vendaTxt.setEditable(true);
			quantidadeTxt.setEditable(true);
		}

		painelCampos = new JPanel(new GridLayout(8, 2, 3, 3));
		painelCampos.add(numero_msTxt);
		painelCampos.add(nomeTxt);
		painelCampos.add(labTxt);
		painelCampos.add(codigo_barrasTxt);
		painelCampos.add(loteTxt);
		painelCampos.add(concentracaoTxt);
		painelCampos.add(formaCb);
		painelCampos.add(categoriaCb);

		painelCampos.add(dt_compraTxt);
		painelCampos.add(dt_validadeTxt);
		painelCampos.add(valor_compraTxt);
		painelCampos.add(valor_vendaTxt);
		painelCampos.add(quantidadeTxt);

		painelPrincipal.add(painelCampos, BorderLayout.CENTER);
	}

	public Medicamento getMedicamento() {
		return this.medicamento;
	}

	protected void mostrarJanela() {
		janela.pack();
		janela.setSize(500, 600);
		janela.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();

		switch (o) {
		case "Pronto": // Validar mandar pro banco
			String nome, numero_ms, categoria, forma, laboratorio, codigo_barras, lote;
			Date data_compra = new Date(), data_validade = new Date();
			double valor_compra = 0, valor_venda = 0, concentracao = 0;
			int quantidade_estoque = 0;

			boolean validado = true;

			nome = nomeTxt.getText();
			if (nome.trim().isEmpty()) {
				validado = false;
			}

			numero_ms = numero_msTxt.getText();
			if (numero_ms.trim().isEmpty()) {
				validado = false;
			}

			categoria = (String) categoriaCb.getSelectedItem();
			forma = (String) formaCb.getSelectedItem();
			
			laboratorio = labTxt.getText();
			if (laboratorio.trim().isEmpty()) {
				validado = false;
			}

			codigo_barras = codigo_barrasTxt.getText();
			if (codigo_barras.trim().isEmpty()) {
				validado = false;
			}

			lote = loteTxt.getText();
			if (lote.trim().isEmpty()) {
				validado = false;
			}

			data_compra = dt_compraTxt.getDate();
			data_validade = dt_validadeTxt.getDate();

			if (editar) {
				try {
					valor_compra = Double.parseDouble(valor_compraTxt.getText());
					valor_venda = Double.parseDouble(valor_vendaTxt.getText());
					concentracao = Double.parseDouble(concentracaoTxt.getText());
					quantidade_estoque = Integer.parseInt(quantidadeTxt.getText());
				} catch (NumberFormatException n) {
					validado = false;
				}
			}
			
			if (validado) {
				medicamento = new Medicamento(nome, numero_ms, categoria, forma, laboratorio, codigo_barras, lote,
						concentracao, data_validade, data_compra, valor_compra, valor_venda, quantidade_estoque);
				medicamento.setId_medicamento(id);
				fecharJanela();
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, verifique os dados!");
			}
			break;

		case "Cancelar":
			medicamento = new Medicamento();
			fecharJanela();
			break;
		}
	}
}
