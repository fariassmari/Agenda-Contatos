package iu_swing;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import modelo_negocio.Cidade;
import modelo_negocio.ContatoComercial;
import servico.ServicoCidade;
import servico.ServicoContatoComercial;
import servico.ServicoContato;

public class TelaContatoComercial {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome;
    private JTextField txtEmpresa;
    private JComboBox<String> cbCidade;
    private JTextField txtNovaCidade;
    private String nomeSelecionado = null;

    public TelaContatoComercial() {
        initialize();
        carregarTabela();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(160, 160, 640, 480);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Empresa", "Cidade"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherFormulario();
        });
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 10, 610, 160);
        frame.add(scroll);

        int y = 185;
        frame.add(label("Nome:", 10, y));
        txtNome = textField(90, y, 200);
        frame.add(txtNome);

        frame.add(label("Empresa:", 305, y));
        txtEmpresa = textField(390, y, 200);
        frame.add(txtEmpresa);

        y += 35;
        frame.add(label("Cidade:", 10, y));
        cbCidade = new JComboBox<>();
        cbCidade.setBounds(90, y, 200, 22);
        frame.add(cbCidade);
        carregarCidades();

        frame.add(label("Nova cidade:", 305, y));
        txtNovaCidade = textField(405, y, 150);
        frame.add(txtNovaCidade);

        JButton btnCidade = new JButton("Criar");
        btnCidade.setBounds(565, y, 55, 22);
        btnCidade.addActionListener((ActionEvent e) -> criarCidade());
        frame.add(btnCidade);

        JButton btnNovo = button("Novo",     10,  y);
        btnNovo.addActionListener(e -> novo());
        frame.add(btnNovo);

        JButton btnSalvar = button("Salvar",   95,  y);
        btnSalvar.addActionListener(e -> salvar());
        frame.add(btnSalvar);

        JButton btnApagar = button("Apagar",   180, y);
        btnApagar.addActionListener(e -> apagar());
        frame.add(btnApagar);

        JButton btnTelefone = button("Telefone", 265, y);
        btnTelefone.addActionListener(e -> telefone());
        frame.add(btnTelefone);
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<ContatoComercial> lista = ServicoContatoComercial.listarContatosEmpresa();
        for (ContatoComercial cc : lista){
            tableModel.addRow(new Object[]{
                    cc.getId(), cc.getNome(), cc.getEmpresa(), cc.getCidade() != null ? cc.getCidade().getNome : "",
            });
        }
    }

    private void telefone(){
        if (nomeSelecionado == null){
            JOptionPane.showMessageDialog(frame, "Selecione um novo contato.");
            return;
        }

        String num = JOptionPane.showInputDialog(frame, "Digite o telefone: ");
        if (num == null || num.isEmpty()) return;
        try {
            ContatoComercial cc = ServicoContatoComercial.localizarContatoComercial(nomeSelecionado);
            if (cc == null) throw new Exception("Contato não encontrado");
            ServicoContato.adicionarTelefoneContato(num.trim(), cc.getId());
            JOptionPane.showMessageDialog(frame, "Telefone adicionado com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
