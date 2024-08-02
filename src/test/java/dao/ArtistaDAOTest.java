package dao;

import model.Apresentacao;
import model.Artista;
import model.Cidade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArtistaDAOTest {
    private static ArtistaDAO artistaDAO;
    private static ApresentacaoDAO apresentacaoDAO;
    private static CidadeDAO cidadeDAO;
    private static SimpleDateFormat sdf;

    @BeforeAll
    static void setup() {
        artistaDAO = new ArtistaDAO();
        apresentacaoDAO = new ApresentacaoDAO();
        cidadeDAO = new CidadeDAO();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @AfterEach
    void cleanUp() {
        for (Apresentacao apresentacao : apresentacaoDAO.read()) {
            apresentacaoDAO.delete(apresentacao);
        }
        for (Artista artista : artistaDAO.read()) {
            artistaDAO.delete(artista);
        }
        for (Cidade cidade : cidadeDAO.read()) {
            cidadeDAO.delete(cidade);
        }
    }

    @AfterAll
    static void tearDown() {
        artistaDAO.close();
        apresentacaoDAO.close();
        cidadeDAO.close();
    }

    //Este teste verifica a criação e leitura de um artista.

    //Cria um artista e o salva no DAO.
    //Lê todos os artistas do DAO.
    //Verifica se há apenas um artista no DAO e se o nome desse artista é "Artista Teste".
    @Test
    void testCreateAndReadArtista() {
        Artista artista = new Artista("Artista Teste", "Rock", 25, new ArrayList<>());
        artistaDAO.create(artista);

        List<Artista> artistas = artistaDAO.read();
        assertEquals(1, artistas.size());
        assertEquals("Artista Teste", artistas.get(0).getNome());
    }

    //Este teste verifica a atualização de um artista.

    //Cria um artista e o salva no DAO.
    //Atualiza o gênero musical do artista para "Pop".
    //Lê todos os artistas do DAO.
    //Verifica se o gênero musical do artista foi atualizado para "Pop".
    @Test
    void testUpdateArtista() {
        Artista artista = new Artista("Artista Teste", "Rock", 25, new ArrayList<>());
        artistaDAO.create(artista);

        artista.setGeneroMusical("Pop");
        artistaDAO.update(artista);

        List<Artista> artistas = artistaDAO.read();
        assertEquals("Pop", artistas.get(0).getGeneroMusical());
    }

    //Este teste verifica a exclusão de um artista.

    //Cria um artista e o salva no DAO.
    //Exclui o artista do DAO.
    //Lê todos os artistas do DAO.
    //Verifica se não há artistas no DAO (lista vazia).
    @Test
    void testDeleteArtista() {
        Artista artista = new Artista("Artista Teste", "Rock", 25, new ArrayList<>());
        artistaDAO.create(artista);

        artistaDAO.delete(artista);

        List<Artista> artistas = artistaDAO.read();
        assertTrue(artistas.isEmpty());
    }

    //Este teste verifica a listagem de artistas por data e cidade.

    //Cria uma data e uma cidade.
    //Cria dois artistas e os salva no DAO.
    //Cria duas apresentações para os artistas na cidade e data especificadas.
    //Lê todos os artistas do DAO que têm apresentações na cidade e data especificadas.
    //Verifica se há dois artistas retornados.
    @Test
    void testListarArtistasPorDataECidade() throws ParseException {
        Date data = sdf.parse("01/08/2024");
        Cidade cidade = new Cidade("Cidade Teste", null);
        cidadeDAO.create(cidade);

        Artista artista1 = new Artista("Artista 1", "Rock", 25, new ArrayList<>());
        Artista artista2 = new Artista("Artista 2", "Pop", 30, new ArrayList<>());
        artistaDAO.create(artista1);
        artistaDAO.create(artista2);

        Apresentacao apresentacao1 = new Apresentacao(1, data, artista1, cidade, 50.0, 120, 100);
        Apresentacao apresentacao2 = new Apresentacao(2, data, artista2, cidade, 70.0, 90, 200);

        apresentacaoDAO.create(apresentacao1);
        apresentacaoDAO.create(apresentacao2);

        List<Artista> artistas = artistaDAO.listarArtistasPorDataECidade("Cidade Teste", data);
        assertEquals(2, artistas.size());
    }

    //Este teste verifica a listagem de artistas com mais de um certo número de apresentações.

    //Cria uma data e uma cidade.
    //Cria um artista e o salva no DAO.
    //Cria duas apresentações para o artista na cidade e data especificadas.
    //Atualiza o artista para associar as apresentações.
    //Lê todos os artistas do DAO que têm mais de uma apresentação.
    //Verifica se há um artista retornado e se o nome desse artista é "Artista Teste".
    @Test
    void testListarArtistasComMaisDeNApresentacoes() throws ParseException {
        Date data = sdf.parse("01/08/2024");
        Cidade cidade = new Cidade("Cidade Teste", null);
        cidadeDAO.create(cidade);

        Artista artista = new Artista("Artista Teste", "Rock", 25, new ArrayList<>());
        artistaDAO.create(artista);

        Apresentacao apresentacao1 = new Apresentacao(1, data, artista, cidade, 50.0, 120, 100);
        Apresentacao apresentacao2 = new Apresentacao(2, data, artista, cidade, 70.0, 90, 200);

        apresentacaoDAO.create(apresentacao1);
        apresentacaoDAO.create(apresentacao2);

        List<Apresentacao> listaApresentacoes = new ArrayList<>();
        listaApresentacoes.add(apresentacao1);
        listaApresentacoes.add(apresentacao2);
        artista.setListaApresentacao(listaApresentacoes);

        artistaDAO.update(artista);

        List<Artista> artistas = artistaDAO.listarArtistasComMaisDeNApresentacoes(1);
        assertEquals(1, artistas.size());
        assertEquals("Artista Teste", artistas.get(0).getNome());
    }
}
