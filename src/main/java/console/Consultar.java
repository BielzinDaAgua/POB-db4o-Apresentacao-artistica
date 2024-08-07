package console;

import fachada.Fachada;
import model.Artista;
import model.Apresentacao;
import excecoes.ExcecaoNegocio;

import java.util.Date;
import java.util.List;

public class Consultar {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();

        try {
            // Consulta 1: Listar apresentações por data
            Date data = new Date();
            List<Apresentacao> apresentacoesPorData = fachada.listarApresentacoesPorData(data);
            System.out.println("Apresentações na data " + data + ":");
            for (Apresentacao apresentacao : apresentacoesPorData) {
                System.out.println(apresentacao.getArtista().getNome() + " em " + apresentacao.getCidade().getNome());
            }

            // Consulta 2: Listar artistas por data e cidade
            String cidadeNome = "Cidade1";
            List<Artista> artistasPorDataECidade = fachada.listarArtistasPorDataECidade(cidadeNome, data);
            System.out.println("\nArtistas na cidade " + cidadeNome + " na data " + data + ":");
            for (Artista artista : artistasPorDataECidade) {
                System.out.println(artista.getNome());
            }

            // Consulta 3: Listar artistas com mais de N apresentações
            int n = 1;
            List<Artista> artistasComMaisDeNApresentacoes = fachada.listarArtistasComMaisDeNApresentacoes(n);
            System.out.println("\nArtistas com mais de " + n + " apresentações:");
            for (Artista artista : artistasComMaisDeNApresentacoes) {
                System.out.println(artista.getNome() + " (" + artista.getListaApresentacao().size() + " apresentações)");
            }
        } catch (ExcecaoNegocio e) {
            System.err.println(e.getMessage());
        } finally {
            fachada.close();
        }
    }
}
