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
import modelo_negocio.ContatoPessoal;
import servico.ServicoCidade;
import servico.ServicoContato;
import servico.ServicoContatoPessoal;

public class TelaContatoPessoal {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNome;
    private JComboBox<String> cbCidade;
    private JTextField txtNovaCidade;
    private String nomeSelecionado = null;

    public TelaContatoPessoal() {
        initialize();
        carregarTabela();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(160, 160, 660, 480);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Cidade"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 10, 610, 160);
        frame.add(scroll);

        int y = 185;
        frame.add(label("Nome:", 10, y));
        txtNome = textField(90, y, 200);
        frame.add(txtNome);

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
        btnCidade.setBounds(565, y, 70, 22);
        btnCidade.addActionListener((ActionEvent e) -> criarCidade());
        frame.add(btnCidade);

        JButton btnNovo = button("Novo", 10, y + 35);
        btnNovo.addActionListener(e -> novo());
        frame.add(btnNovo);

        JButton btnSalvar = button("Salvar", 95, y + 35);
        btnSalvar.addActionListener(e -> salvar());
        frame.add(btnSalvar);

        JButton btnApagar = button("Apagar", 180, y + 35);
        btnApagar.addActionListener(e -> apagar());
        frame.add(btnApagar);

        JButton btnTelefone = button("Telefone", 265, y + 35);
        btnTelefone.addActionListener(e -> telefone());
        frame.add(btnTelefone);
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        List<ContatoPessoal> lista = ServicoContatoPessoal.listarContatosPessoais();
        for (ContatoPessoal cp : lista) {
            tableModel.addRow(new Object[] {
                    cp.getId(),
                    cp.getNome(),
                    cp.getCidade() != null ? cp.getCidade().getNome() : ""
            });
        }
    }

    private void carregarCidades() {
        cbCidade.removeAllItems();
        List<Cidade> cidades = ServicoCidade.listarCidades();
        for (Cidade cidade : cidades) {
            cbCidade.addItem(cidade.getNome());
        }
    }

    private void preencherFormulario() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            return;
        }

        nomeSelecionado = (String) tableModel.getValueAt(linha, 1);
        txtNome.setText(nomeSelecionado);

        String cidade = (String) tableModel.getValueAt(linha, 2);
        if (cidade != null) {
            cbCidade.setSelectedItem(cidade);
        }
    }

    private void novo() {
        nomeSelecionado = null;
        table.clearSelection();
        txtNome.setText("");
        txtNovaCidade.setText("");
        if (cbCidade.getItemCount() > 0) {
            cbCidade.setSelectedIndex(0);
        }
    }

    private void salvar() {
        String nome = txtNome.getText().trim();
        String nomeCidade = (String) cbCidade.getSelectedItem();

        if (nome.isEmpty() || nomeCidade == null || nomeCidade.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Preencha nome e cidade.");
            return;
        }

        Cidade cidade = ServicoCidade.localizarCidade(nomeCidade);
        if (cidade == null) {
            JOptionPane.showMessageDialog(frame, "Cidade inválida.");
            return;
        }

        try {
            if (nomeSelecionado == null || nomeSelecionado.isEmpty()) {
                ServicoContatoPessoal.criarContatoPessoal(nome, cidade.getId());
            } else {
                ServicoContatoPessoal.alterarContatoPessoal(nome, cidade.getId());
            }
            carregarTabela();
            novo();
            JOptionPane.showMessageDialog(frame, "Contato salvo com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void apagar() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(frame, "Selecione um contato para apagar.");
            return;
        }

        int id = (int) tableModel.getValueAt(linha, 0);
        try {
            ServicoContato.apagarContato(id);
            carregarTabela();
            novo();
            JOptionPane.showMessageDialog(frame, "Contato apagado com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarCidade() {
        String nome = txtNovaCidade.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Digite o nome da nova cidade.");
            return;
        }

        try {
            ServicoCidade.criarCidade(nome);
            txtNovaCidade.setText("");
            carregarCidades();
            cbCidade.setSelectedItem(nome);
            JOptionPane.showMessageDialog(frame, "Cidade criada com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void telefone() {
        if (nomeSelecionado == null) {
            JOptionPane.showMessageDialog(frame, "Selecione um contato.");
            return;
        }

        String num = JOptionPane.showInputDialog(frame, "Digite o telefone: ");
        if (num == null || num.isEmpty()) {
            return;
        }

        try {
            ContatoPessoal cp = ServicoContatoPessoal.localizarContatoPessoal(nomeSelecionado);
            if (cp == null) {
                throw new Exception("Contato não encontrado");
            }
            ServicoContato.adicionarTelefoneContato(num.trim(), cp.getId());
            JOptionPane.showMessageDialog(frame, "Telefone adicionado com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel label(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 80, 22);
        return lbl;
    }

    private JTextField textField(int x, int y, int largura) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, largura, 22);
        return txt;
    }

    private JButton button(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 100, 22);
        return btn;
    }
}
