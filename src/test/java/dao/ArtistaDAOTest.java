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

    @Test
    void testCreateAndReadArtista() {
        Artista artista = new Artista("Artista Teste", "Rock", 25, new ArrayList<>());
        artistaDAO.create(artista);

        List<Artista> artistas = artistaDAO.read();
        assertEquals(1, artistas.size());
        assertEquals("Artista Teste", artistas.get(0).getNome());
    }

    @Test
    void testUpdateArtista() {
        Artista artista = new Artista("Artista Teste", "Rock", 25, new ArrayList<>());
        artistaDAO.create(artista);

        artista.setGeneroMusical("Pop");
        artistaDAO.update(artista);

        List<Artista> artistas = artistaDAO.read();
        assertEquals("Pop", artistas.get(0).getGeneroMusical());
    }

    @Test
    void testDeleteArtista() {
        Artista artista = new Artista("Artista Teste", "Rock", 25, new ArrayList<>());
        artistaDAO.create(artista);

        artistaDAO.delete(artista);

        List<Artista> artistas = artistaDAO.read();
        assertTrue(artistas.isEmpty());
    }

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
