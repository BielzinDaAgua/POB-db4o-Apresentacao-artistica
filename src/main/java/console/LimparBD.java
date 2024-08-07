package console;

import fachada.Fachada;
import model.Artista;
import model.Apresentacao;
import model.Cidade;

import java.util.List;

public class LimparBD {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();

        // Remover todas as apresentações
        List<Apresentacao> apresentacoes = fachada.listarApresentacoes();
        for (Apresentacao apresentacao : apresentacoes) {
            fachada.deleteApresentacao(apresentacao);
        }

        // Remover todos os artistas
        List<Artista> artistas = fachada.listarArtistas();
        for (Artista artista : artistas) {
            fachada.deleteArtista(artista);
        }

        // Remover todas as cidades
        List<Cidade> cidades = fachada.listarCidades();
        for (Cidade cidade : cidades) {
            fachada.deleteCidade(cidade);
        }

        fachada.close();
        System.out.println("Banco de dados limpo com sucesso.");
    }
}
