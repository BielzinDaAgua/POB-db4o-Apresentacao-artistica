package tela;

import fachada.Fachada;
import model.Cidade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CidadeFrame extends JFrame {
    private Fachada fachada;
    private JTextField nomeField;
    private JTextArea listaCidadesArea;
    private JButton cadastrarButton, excluirButton, listarButton;

    public CidadeFrame(Fachada fachada) {
        this.fachada = fachada;
        setTitle("Gerenciamento de Cidades");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Use DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        cadastrarButton = new JButton("Cadastrar");
        panel.add(cadastrarButton);

        excluirButton = new JButton("Excluir");
        panel.add(excluirButton);

        listarButton = new JButton("Listar");
        panel.add(listarButton);

        listaCidadesArea = new JTextArea();
        listaCidadesArea.setEditable(false);
        panel.add(new JScrollPane(listaCidadesArea));

        add(panel);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCidade();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCidade();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCidades();
            }
        });
    }

    private void cadastrarCidade() {
        try {
            String nome = nomeField.getText();
            Cidade cidade = new Cidade(nome, null);
            fachada.createCidade(cidade);
            JOptionPane.showMessageDialog(this, "Cidade cadastrada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cidade: " + e.getMessage());
        }
    }

    private void excluirCidade() {
        try {
            String nome = nomeField.getText();
            Cidade cidade = fachada.buscarCidadePorNome(nome);

            if (cidade != null) {
                fachada.deleteCidade(cidade);
                JOptionPane.showMessageDialog(this, "Cidade excluída com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Cidade não encontrada!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cidade: " + e.getMessage());
        }
    }

    private void listarCidades() {
        try {
            List<Cidade> cidades = fachada.listarCidades();
            listaCidadesArea.setText("");
            for (Cidade cidade : cidades) {
                listaCidadesArea.append("Nome: " + cidade.getNome() + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar cidades: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Fachada fachada = new Fachada(); // Crie uma instância da Fachada
        new CidadeFrame(fachada).setVisible(true);
    }
}
