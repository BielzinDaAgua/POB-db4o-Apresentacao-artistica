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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 5, 5)); // 8 linhas e 2 colunas com espaçamento de 5 pixels

        JLabel idLabel = new JLabel("ID:");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(idLabel);
        idField = new JTextField();
        idField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(idField);

        JLabel dataLabel = new JLabel("Data (dd/MM/yyyy):");
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(dataLabel);
        dataField = new JTextField();
        dataField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(dataField);

        JLabel artistaLabel = new JLabel("Artista:");
        artistaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(artistaLabel);
        artistaField = new JTextField();
        artistaField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(artistaField);

        JLabel cidadeLabel = new JLabel("Cidade:");
        cidadeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(cidadeLabel);
        cidadeField = new JTextField();
        cidadeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(cidadeField);

        JLabel precoLabel = new JLabel("Preço do Ingresso:");
        precoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(precoLabel);
        precoField = new JTextField();
        precoField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(precoField);

        JLabel duracaoLabel = new JLabel("Duração (min):");
        duracaoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(duracaoLabel);
        duracaoField = new JTextField();
        duracaoField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(duracaoField);

        JLabel ingressosLabel = new JLabel("Ingressos Vendidos:");
        ingressosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(ingressosLabel);
        ingressosField = new JTextField();
        ingressosField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(ingressosField);

        criarButton = new JButton("Criar");
        panel.add(criarButton);

        alterarButton = new JButton("Alterar");
        panel.add(alterarButton);

        excluirButton = new JButton("Excluir");
        panel.add(excluirButton);

        listarButton = new JButton("Listar");
        panel.add(listarButton);

        listaApresentacoesArea = new JTextArea();
        listaApresentacoesArea.setEditable(false);
        panel.add(new JScrollPane(listaApresentacoesArea));

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
        List<Apresentacao> apresentacoes = fachada.listarApresentacoes();
        listaApresentacoesArea.setText("");
        for (Apresentacao apresentacao : apresentacoes) {
            listaApresentacoesArea.append("ID: " + apresentacao.getId() + " | Data: " + apresentacao.getData() + " | Artista: "
                    + apresentacao.getArtista().getNome() + " | Cidade: " + apresentacao.getCidade().getNome() + " | Preço: "
                    + apresentacao.getPrecoIngresso() + " | Duração: " + apresentacao.getDuracao() + " | Ingressos Vendidos: "
                    + apresentacao.getNumeroDeIngressosVendidos() + "\n");
        }
    }
}
