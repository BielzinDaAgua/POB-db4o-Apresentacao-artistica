package tela;

import fachada.Fachada;
import model.Apresentacao;
import model.Artista;
import model.Cidade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ApresentacaoFrame extends JFrame {
    private Fachada fachada;
    private JTextField idField, dataField, precoField, duracaoField, ingressosField;
    private JTextField artistaField, cidadeField;
    private JTextArea listaApresentacoesArea;
    private JButton criarButton, alterarButton, excluirButton, listarButton;

    public ApresentacaoFrame(Fachada fachada) {
        this.fachada = fachada;
        setTitle("Gerenciamento de Apresentações");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(idLabel, gbc);

        idField = new JTextField();
        idField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(idField, gbc);

        JLabel dataLabel = new JLabel("Data (dd/MM/yyyy):");
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(dataLabel, gbc);

        dataField = new JTextField();
        dataField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(dataField, gbc);

        JLabel artistaLabel = new JLabel("Artista:");
        artistaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(artistaLabel, gbc);

        artistaField = new JTextField();
        artistaField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(artistaField, gbc);

        JLabel cidadeLabel = new JLabel("Cidade:");
        cidadeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(cidadeLabel, gbc);

        cidadeField = new JTextField();
        cidadeField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(cidadeField, gbc);

        JLabel precoLabel = new JLabel("Preço do Ingresso:");
        precoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(precoLabel, gbc);

        precoField = new JTextField();
        precoField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(precoField, gbc);

        JLabel duracaoLabel = new JLabel("Duração (min):");
        duracaoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(duracaoLabel, gbc);

        duracaoField = new JTextField();
        duracaoField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(duracaoField, gbc);

        JLabel ingressosLabel = new JLabel("Ingressos Vendidos:");
        ingressosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(ingressosLabel, gbc);

        ingressosField = new JTextField();
        ingressosField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(ingressosField, gbc);

        criarButton = new JButton("Criar");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add(criarButton, gbc);

        alterarButton = new JButton("Alterar");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add(alterarButton, gbc);

        excluirButton = new JButton("Excluir");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        panel.add(excluirButton, gbc);

        listarButton = new JButton("Listar");
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        panel.add(listarButton, gbc);

        listaApresentacoesArea = new JTextArea(10, 30);
        listaApresentacoesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listaApresentacoesArea);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        add(panel);

        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarApresentacao();
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarApresentacao();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirApresentacao();
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarApresentacoes();
            }
        });
    }

    private void criarApresentacao() {
        try {
            int id = Integer.parseInt(idField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = sdf.parse(dataField.getText());
            String artistaNome = artistaField.getText();
            String cidadeNome = cidadeField.getText();
            Artista artista = fachada.buscarArtistaPorNome(artistaNome);
            Cidade cidade = fachada.buscarCidadePorNome(cidadeNome);

            if (artista != null && cidade != null) {
                double preco = Double.parseDouble(precoField.getText());
                int duracao = Integer.parseInt(duracaoField.getText());
                int ingressosVendidos = Integer.parseInt(ingressosField.getText());

                Apresentacao apresentacao = new Apresentacao(id, data, artista, cidade, preco, duracao, ingressosVendidos);
                fachada.createApresentacao(apresentacao);
                JOptionPane.showMessageDialog(this, "Apresentação criada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Artista ou cidade não encontrados!");
            }
        } catch (ParseException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar apresentação: " + ex.getMessage());
        }
    }

    private void alterarApresentacao() {
        try {
            int id = Integer.parseInt(idField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = sdf.parse(dataField.getText());
            String artistaNome = artistaField.getText();
            String cidadeNome = cidadeField.getText();
            Artista artista = fachada.buscarArtistaPorNome(artistaNome);
            Cidade cidade = fachada.buscarCidadePorNome(cidadeNome);

            if (artista != null && cidade != null) {
                Apresentacao apresentacao = new Apresentacao(id, data, artista, cidade, Double.parseDouble(precoField.getText()),
                        Integer.parseInt(duracaoField.getText()), Integer.parseInt(ingressosField.getText()));
                fachada.updateApresentacao(apresentacao);
                JOptionPane.showMessageDialog(this, "Apresentação atualizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Artista ou cidade não encontrados!");
            }
        } catch (ParseException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar apresentação: " + ex.getMessage());
        }
    }

    private void excluirApresentacao() {
        try {
            int id = Integer.parseInt(idField.getText());
            List<Apresentacao> apresentacoes = fachada.listarApresentacoes();
            Apresentacao apresentacao = apresentacoes.stream()
                    .filter(a -> a.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (apresentacao != null) {
                fachada.deleteApresentacao(apresentacao);
                JOptionPane.showMessageDialog(this, "Apresentação excluída com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Apresentação não encontrada!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir apresentação: " + ex.getMessage());
        }
    }

    private void listarApresentacoes() {
        try {
            List<Apresentacao> apresentacoes = fachada.listarApresentacoes();
            listaApresentacoesArea.setText("");
            for (Apresentacao apresentacao : apresentacoes) {
                listaApresentacoesArea.append(
                        "ID: " + apresentacao.getId() + " / " +
                                "Data: " + apresentacao.getData() + " \n " +
                                "Artista: " + apresentacao.getArtista().getNome() + " / " +
                                "Cidade: " + apresentacao.getCidade().getNome() + " \n " +
                                "Preço do Ingresso: " + apresentacao.getPrecoIngresso() + " / " +
                                "Duração: " + apresentacao.getDuracao() + " minutos" + " \n " +
                                "Ingressos Vendidos: " + apresentacao.getNumeroDeIngressosVendidos() + "\n \n"
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar apresentações: " + e.getMessage());
        }
    }
}
