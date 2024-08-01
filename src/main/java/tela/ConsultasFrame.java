package tela;

import fachada.Fachada;
import model.Apresentacao;
import model.Artista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConsultasFrame extends JFrame {
    private JTextField dataField;
    private JTextField cidadeField;
    private JTextField numApresentacoesField;
    private JTextArea resultadoArea;
    private Fachada fachada;

    public ConsultasFrame(Fachada fachada) {
        this.fachada = fachada;

        setTitle("Consultas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JLabel dataLabel = new JLabel("Data (dd/MM/yyyy):");
        dataField = new JTextField();
        JLabel cidadeLabel = new JLabel("Cidade:");
        cidadeField = new JTextField();
        JLabel numApresentacoesLabel = new JLabel("Artistas com mais de N Apresentações:");
        numApresentacoesField = new JTextField();
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarConsultas();
            }
        });

        add(dataLabel);
        add(dataField);
        add(cidadeLabel);
        add(cidadeField);
        add(numApresentacoesLabel);
        add(numApresentacoesField);
        add(new JScrollPane(resultadoArea));
        add(consultarButton);

        setVisible(true);
    }

    private void realizarConsultas() {
        try {
            String dataTexto = dataField.getText();
            String cidadeNome = cidadeField.getText();
            int numApresentacoes = Integer.parseInt(numApresentacoesField.getText());

            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataTexto);

            List<Apresentacao> apresentacoesNaData = fachada.listarApresentacoesPorData(data);
            List<Artista> artistasNaDataCidade = fachada.listarArtistasPorDataECidade(cidadeNome, data);
            List<Artista> artistasComMaisApresentacoes = fachada.listarArtistasComMaisDeNApresentacoes(numApresentacoes);

            StringBuilder resultado = new StringBuilder();

            resultado.append("Apresentações na Data:\n");
            for (Apresentacao a : apresentacoesNaData) {
                resultado.append(a.toString()).append("\n");
            }

            resultado.append("\nArtistas na Data e Cidade:\n");
            for (Artista a : artistasNaDataCidade) {
                resultado.append(a.toString()).append("\n");
            }

            resultado.append("\nArtistas com mais de ").append(numApresentacoes).append(" Apresentações:\n");
            for (Artista a : artistasComMaisApresentacoes) {
                resultado.append(a.toString()).append("\n");
            }

            resultadoArea.setText(resultado.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar consultas: " + e.getMessage());
        }
    }
}
