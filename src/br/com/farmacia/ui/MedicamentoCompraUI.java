package br.com.farmacia.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import br.com.farmacia.model.Medicamento;
import br.com.farmacia.ui.model.FrameConfirmUI;

public class MedicamentoCompraUI extends FrameConfirmUI {

	private JPanel painelCampos;
	private JTextField quantidade, valorVenda, valorCompra, nome;
	private JXDatePicker dataValidade;

	private Medicamento medicamento;
	private int qtd;

	public MedicamentoCompraUI(Medicamento m) {
		super("Informações");
		this.medicamento = m;
	}

	public Medicamento getMedicamento() {
		return this.medicamento;
	}

	public int getQtd() {
		return this.qtd;
	}

	public static void main(String[] args) {
		new MedicamentoCompraUI(new Medicamento()).montarTela();
	}

	@Override
	public void montarTela() {
		prepararJanela();
		prepararPainelPrincipal();

		prepararPainelBotoes();
		prepararBotoes();
		prepararPainelCampos();

		mostrarJanela();
	}

	protected void mostrarJanela() {
		janela.pack();
		janela.setSize(300, 400);
		janela.setVisible(true);
	}

	private void prepararPainelCampos() {
		painelCampos = new JPanel(new GridLayout(5, 1, 5, 5));

		nome = new JTextField(this.medicamento.getNome());
		nome.setEnabled(false);

		quantidade = new JTextField();
		quantidade.setBorder(BorderFactory.createTitledBorder("Quantidade"));

		valorCompra = new JTextField();
		valorCompra.setBorder(BorderFactory.createTitledBorder("Valor de compra(99.99)"));

		valorVenda = new JTextField();
		valorVenda.setBorder(BorderFactory.createTitledBorder("Valor de venda(99.99)"));

		dataValidade = new JXDatePicker();
		dataValidade.setBorder(BorderFactory.createTitledBorder("Data de validade(MM/dd/yyyy)"));

		painelCampos.add(nome);
		painelCampos.add(quantidade);
		painelCampos.add(valorCompra);
		painelCampos.add(valorVenda);
		painelCampos.add(dataValidade);

		painelPrincipal.add(painelCampos, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();

		switch (o) {
		case "Cancelar":
			qtd = 0;
			fecharJanela();
			break;
		case "Pronto":
			double vlCompra, vlVenda;
			try {
				vlCompra = Double.parseDouble(valorCompra.getText());
				vlVenda = Double.parseDouble(valorVenda.getText());
				Date validade = dataValidade.getDate();

				medicamento.setValor_compra(vlCompra);
				medicamento.setValor_venda(vlVenda);
				medicamento.setData_validade(validade);

				qtd = Integer.parseInt(quantidade.getText());

				fecharJanela();
			} catch (NumberFormatException nx) {
				JOptionPane.showMessageDialog(null, "Porfavor, verifique os dados!");
			}
			break;
		}
	}
}
