package tela;

import fachada.Fachada;
import model.Artista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ArtistaFrame extends JFrame {
    private Fachada fachada;
    private JTextField nomeField, generoField, idadeField;
    private JTextArea listaArtistasArea;
    private JButton criarButton, alterarButton, excluirButton, listarButton;

    public ArtistaFrame(Fachada fachada) {
        this.fachada = fachada;
        setTitle("Gerenciamento de Artistas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 5)); // 6 linhas e 2 colunas com espaçamento de 5 pixels

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nomeLabel);
        nomeField = new JTextField();
        nomeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(nomeField);

        JLabel generoLabel = new JLabel("Gênero Musical:");
        generoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(generoLabel);
        generoField = new JTextField();
        generoField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(generoField);

        JLabel idadeLabel = new JLabel("Idade:");
        idadeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(idadeLabel);
        idadeField = new JTextField();
        idadeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(idadeField);

        criarButton = new JButton("Criar");
        panel.add(criarButton);

        alterarButton = new JButton("Alterar");
        panel.add(alterarButton);

        excluirButton = new JButton("Excluir");
        panel.add(excluirButton);

        listarButton = new JButton("Listar");
        panel.add(listarButton);

        listaArtistasArea = new JTextArea();
        listaArtistasArea.setEditable(false);
        panel.add(new JScrollPane(listaArtistasArea));

        add(panel);

        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarArtista();
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarArtista();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirArtista();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarArtistas();
            }
        });
    }

    private void criarArtista() {
        try {
            String nome = nomeField.getText();
            String generoMusical = generoField.getText();
            int idade = Integer.parseInt(idadeField.getText());

            Artista artista = new Artista(nome, generoMusical, idade, null);
            fachada.createArtista(artista);
            JOptionPane.showMessageDialog(this, "Artista criado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar artista: " + ex.getMessage());
        }
    }

    private void alterarArtista() {
        try {
            String nome = nomeField.getText();
            String generoMusical = generoField.getText();
            int idade = Integer.parseInt(idadeField.getText());

            Artista artista = new Artista(nome, generoMusical, idade, null);
            fachada.updateArtista(artista);
            JOptionPane.showMessageDialog(this, "Artista atualizado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar artista: " + ex.getMessage());
        }
    }

    private void excluirArtista() {
        try {
            String nome = nomeField.getText();
            List<Artista> artistas = fachada.listarArtistas();
            Artista artista = artistas.stream()
                    .filter(a -> a.getNome().equals(nome))
                    .findFirst()
                    .orElse(null);

            if (artista != null) {
                fachada.deleteArtista(artista);
                JOptionPane.showMessageDialog(this, "Artista excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Artista não encontrado!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir artista: " + ex.getMessage());
        }
    }

    private void listarArtistas() {
        List<Artista> artistas = fachada.listarArtistas();
        listaArtistasArea.setText("");
        for (Artista artista : artistas) {
            listaArtistasArea.append("Nome: " + artista.getNome() + " | Gênero Musical: " + artista.getGeneroMusical() + " | Idade: " + artista.getIdade() + "\n");
        }
    }
}
