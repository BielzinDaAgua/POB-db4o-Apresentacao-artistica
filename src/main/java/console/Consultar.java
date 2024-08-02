package console;

import fachada.Fachada;
import model.Apresentacao;
import model.Artista;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Consultar {
    public static void main(String[] args) throws ParseException {
        Fachada fachada = new Fachada();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Consultar apresentações na data D
        Date dataD = sdf.parse("01/08/2024");
        List<Apresentacao> apresentacoesNaDataD = fachada.listarApresentacoesPorData(dataD);
        System.out.println("Apresentações na data " + dataD + ":");
        for (Apresentacao apresentacao : apresentacoesNaDataD) {
            System.out.println("ID: " + apresentacao.getId() + ", Artista: " + apresentacao.getArtista().getNome() + ", Cidade: " + apresentacao.getCidade().getNome() + ", Preço: " + apresentacao.getPrecoIngresso());
        }

        // Consultar artistas que vão se apresentar na cidade X na data D
        String cidadeX = "Cidade 1";
        List<Artista> artistasNaCidadeXNaDataD = fachada.listarArtistasPorDataECidade(cidadeX, dataD);
        System.out.println("\nArtistas que vão se apresentar na cidade " + cidadeX + " na data " + dataD + ":");
        for (Artista artista : artistasNaCidadeXNaDataD) {
            System.out.println("Nome: " + artista.getNome() + ", Gênero: " + artista.getGeneroMusical() + ", Idade: " + artista.getIdade());
        }

        // Consultar artistas com mais de N apresentações
        int n = 1;
        List<Artista> artistasComMaisDeNApresentacoes = fachada.listarArtistasComMaisDeNApresentacoes(n);
        System.out.println("\nArtistas com mais de " + n + " apresentações:");
        for (Artista artista : artistasComMaisDeNApresentacoes) {
            System.out.println("Nome: " + artista.getNome() + ", Gênero: " + artista.getGeneroMusical() + ", Idade: " + artista.getIdade());
        }

        fachada.close();
    }
}