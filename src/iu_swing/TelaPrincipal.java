package iu_swing;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import servico.Servico;

public class TelaPrincipal {
    private JFrame frame;

    public TelaPrincipal() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize(){
        frame = new JFrame("Agenda de Contatos!");
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        JLabel label = new JLabel("Agedna de contatos", SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.BOLD, 24));
        label.setBounds(0, 0, 500, 320);
        frame.getContentPane().add(label);

        frame.addWindowListener(windowClosing(e) -> {
            try {
                Servico.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        });

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menuPessoal = new JMenu("Contato Pessoal");
        menuPessoal.addMouseListener(mouseClicked(e) -> { new TelaContatoPessoal(); });
        menuBar.add(menuPessoal);

        JMenu menuComercial = new JMenu("Contato Comercial");
        menuComercial.addMouseListener(mouseClicked(e) -> { new TelaContatoComercial(); });
        menuBar.add(menuComercial);
    }

    public static void main(String[] args){
        javax.swing.EventQueue.invokeLater(() -> {
            try {
                Servico.conectar();
                new TelaPrincipal();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage);
            }
        });
    }
}
