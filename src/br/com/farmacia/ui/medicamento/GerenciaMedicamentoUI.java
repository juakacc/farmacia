package br.com.farmacia.ui.medicamento;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.farmacia.dao.MedicamentoDao;
import br.com.farmacia.model.Medicamento;
import br.com.farmacia.ui.model.FrameGerenciaUI;
import static br.com.farmacia.ui.aux.Recuperar.*;

public class GerenciaMedicamentoUI extends FrameGerenciaUI {

	private JTable tabela;
	private DefaultTableModel modelo;
	
	private MedicamentoDao repo;
	
	public GerenciaMedicamentoUI() {
		super("Gerenciar Estoque");
		repo = MedicamentoDao.getInstancia();
	}
	
	public static void main(String[] args) {
		new GerenciaMedicamentoUI().montarTela();
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
		
		String nome = txtNome.getText();
		List<Medicamento> medicamentos = repo.recuperarPorNome(nome);
		
		for (Medicamento med : medicamentos) {// verificar quais atributos
			modelo.addRow(new Object[]{
				med.getId(), 
				med.getQuantidade_estoque(), 
				med.getNome(), 
				med.getNumero_ms(), 
				med.getCategoria(), 
				med.getForma(), 
				med.getLaboratorio(), 
				med.getCodigo_barras(), 
				med.getLote(), 
				med.getConcentracao(), 
				traduzirDataMostrar(med.getData_validade()),
				med.getValor_compra(), 
				med.getValor_venda()});
		}
	}

	private void prepararTabela() {
		modelo = new DefaultTableModel();
		tabela = new JTable(modelo);
		
		modelo.addColumn("ID");
		modelo.addColumn("Qtd. Estoque");
		modelo.addColumn("Nome");
		modelo.addColumn("Nº MS");
		modelo.addColumn("Categoria");
		modelo.addColumn("Forma");
		modelo.addColumn("Laboratório");
		modelo.addColumn("Cód. Barras");
		modelo.addColumn("Lote");
		modelo.addColumn("Concentração");
		modelo.addColumn("Dt Validade");
		modelo.addColumn("Vl Compra");
		modelo.addColumn("Vl Venda");
		
		JScrollPane sroll = new JScrollPane(tabela);
		painelPrincipal.add(sroll, BorderLayout.CENTER);
	}
	
	protected void mostrarJanela() {
		janela.pack();
		janela.setSize(1250, 500);
		//janela.setLocationRelativeTo(dono);
		janela.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String o = e.getActionCommand();
		int linha = tabela.getSelectedRow();
		
		switch (o) {
		case "Voltar":
			fecharJanela();
			break;
		case "Editar":
			if (linha > -1) {
				int id = (int) modelo.getValueAt(linha, 0);
				Medicamento medicamento = repo.recuperarPorId(id);
				
				MedicamentoForm f = new MedicamentoForm(medicamento, true);
				f.montarTela();
				medicamento = f.getMedicamento();
				if (!medicamento.isNull()) {
					if (repo.editar(medicamento)) {
						JOptionPane.showMessageDialog(janela, "Editado com sucesso");
					} else {
						JOptionPane.showMessageDialog(janela, "Ocorreu um erro");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			atualizarTabela();
			break;
		case "Remover":
			if (linha > -1) {
				int id = (int) modelo.getValueAt(linha, 0);
				Medicamento medicamento = repo.recuperarPorId(id);
				int opcao = JOptionPane.showConfirmDialog(null, "Isso removerá todos os registros desse medicamento. Deseja realmente fazer isso?");
				if (opcao == JOptionPane.YES_OPTION) {
					if (repo.remover(medicamento)) {
						JOptionPane.showMessageDialog(null, "Removido com sucesso");
					} else {
						JOptionPane.showMessageDialog(null, "Aconteceu algum erro durante a remoção");					
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleção inválida");					
			}
			atualizarTabela();
			break;
		case "Adicionar":
			MedicamentoForm f = new MedicamentoForm(new Medicamento(), false);
			f.montarTela();
			Medicamento medicamento = f.getMedicamento();
			if (!medicamento.isNull()) {
				if (repo.adicionar(medicamento)) {
					JOptionPane.showMessageDialog(null, "Adicionado com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro");					
				}
				atualizarTabela();
			}
			break;
		case "Pesquisar":
			atualizarTabela();
			break;
		}
	}
}
