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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApresentacaoDAOTest {
    private static ApresentacaoDAO apresentacaoDAO;
    private static ArtistaDAO artistaDAO;
    private static CidadeDAO cidadeDAO;
    private static SimpleDateFormat sdf;

    @BeforeAll
    static void setup() {
        apresentacaoDAO = new ApresentacaoDAO();
        artistaDAO = new ArtistaDAO();
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
        apresentacaoDAO.close();
        artistaDAO.close();
        cidadeDAO.close();
    }

    @Test
    void testCreateAndReadApresentacao() throws ParseException {
        Date data = sdf.parse("01/08/2024");
        Artista artista = new Artista("Artista Teste", "Rock", 25, null);
        Cidade cidade = new Cidade("Cidade Teste", null);

        artistaDAO.create(artista);
        cidadeDAO.create(cidade);

        Apresentacao apresentacao = new Apresentacao(1, data, artista, cidade, 50.0, 120, 100);
        apresentacaoDAO.create(apresentacao);

        List<Apresentacao> apresentacoes = apresentacaoDAO.read();
        assertEquals(1, apresentacoes.size());
        assertEquals("Artista Teste", apresentacoes.get(0).getArtista().getNome());
    }

    @Test
    void testUpdateApresentacao() throws ParseException {
        Date data = sdf.parse("01/08/2024");
        Artista artista = new Artista("Artista Teste", "Rock", 25, null);
        Cidade cidade = new Cidade("Cidade Teste", null);

        artistaDAO.create(artista);
        cidadeDAO.create(cidade);

        Apresentacao apresentacao = new Apresentacao(1, data, artista, cidade, 50.0, 120, 100);
        apresentacaoDAO.create(apresentacao);

        apresentacao.setPrecoIngresso(60.0);
        apresentacaoDAO.update(apresentacao);

        List<Apresentacao> apresentacoes = apresentacaoDAO.read();
        assertEquals(60.0, apresentacoes.get(0).getPrecoIngresso());
    }

    @Test
    void testDeleteApresentacao() throws ParseException {
        Date data = sdf.parse("01/08/2024");
        Artista artista = new Artista("Artista Teste", "Rock", 25, null);
        Cidade cidade = new Cidade("Cidade Teste", null);

        artistaDAO.create(artista);
        cidadeDAO.create(cidade);

        Apresentacao apresentacao = new Apresentacao(1, data, artista, cidade, 50.0, 120, 100);
        apresentacaoDAO.create(apresentacao);

        apresentacaoDAO.delete(apresentacao);

        List<Apresentacao> apresentacoes = apresentacaoDAO.read();
        assertTrue(apresentacoes.isEmpty());
    }

    @Test
    void testListarApresentacoesPorData() throws ParseException {
        Date data1 = sdf.parse("01/08/2024");
        Date data2 = sdf.parse("02/08/2024");
        Artista artista = new Artista("Artista Teste", "Rock", 25, null);
        Cidade cidade = new Cidade("Cidade Teste", null);

        artistaDAO.create(artista);
        cidadeDAO.create(cidade);

        Apresentacao apresentacao1 = new Apresentacao(1, data1, artista, cidade, 50.0, 120, 100);
        Apresentacao apresentacao2 = new Apresentacao(2, data2, artista, cidade, 70.0, 90, 200);

        apresentacaoDAO.create(apresentacao1);
        apresentacaoDAO.create(apresentacao2);

        List<Apresentacao> apresentacoesNaData1 = apresentacaoDAO.listarApresentacoesPorData(data1);
        assertEquals(1, apresentacoesNaData1.size());
        assertEquals(data1, apresentacoesNaData1.get(0).getData());
    }
}
