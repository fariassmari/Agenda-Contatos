import iu_swing.TelaPrincipal;
import servico.Servico;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Servico.conectar();
                new TelaPrincipal();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage());
            }
        });
    }
}
