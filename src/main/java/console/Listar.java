package console;

import fachada.Fachada;
import model.Artista;
import model.Apresentacao;
import model.Cidade;

import java.util.List;

public class Listar {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();

        List<Artista> artistas = fachada.listarArtistas();
        System.out.println("Artistas:");
        for (Artista artista : artistas) {
            System.out.println("Nome: " + artista.getNome() + ", Gênero: " + artista.getGeneroMusical() + ", Idade: " + artista.getIdade());
        }

        List<Apresentacao> apresentacoes = fachada.listarApresentacoes();
        System.out.println("\nApresentações:");
        for (Apresentacao apresentacao : apresentacoes) {
            System.out.println("ID: " + apresentacao.getId() + ", Data: " + apresentacao.getData() + ", Artista: " + apresentacao.getArtista().getNome() + ", Cidade: " + apresentacao.getCidade().getNome() + ", Preço: " + apresentacao.getPrecoIngresso());
        }

        List<Cidade> cidades = fachada.listarCidades();
        System.out.println("\nCidades:");
        for (Cidade cidade : cidades) {
            System.out.println("Nome: " + cidade.getNome());
        }

        fachada.close();
    }
}