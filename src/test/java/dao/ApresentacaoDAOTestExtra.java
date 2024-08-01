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

import static org.junit.jupiter.api.Assertions.*;

class ApresentacaoDAOTestExtras {
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
    void testCreateApresentacaoWithoutArtistaOrCidade() throws ParseException {
        Date data = sdf.parse("01/08/2024");

        Apresentacao apresentacaoSemArtista = new Apresentacao(1, data, null, new Cidade("Cidade Teste", null), 50.0, 120, 100);
        Apresentacao apresentacaoSemCidade = new Apresentacao(1, data, new Artista("Artista Teste", "Rock", 25, null), null, 50.0, 120, 100);

        assertThrows(IllegalArgumentException.class, () -> apresentacaoDAO.create(apresentacaoSemArtista));
        assertThrows(IllegalArgumentException.class, () -> apresentacaoDAO.create(apresentacaoSemCidade));
    }


    @Test
    void testCreateDuplicateApresentacao() throws ParseException {
        Date data = sdf.parse("01/08/2024");
        Artista artista = new Artista("Artista Teste", "Rock", 25, null);
        Cidade cidade = new Cidade("Cidade Teste", null);

        artistaDAO.create(artista);
        cidadeDAO.create(cidade);

        Apresentacao apresentacao1 = new Apresentacao(1, data, artista, cidade, 50.0, 120, 100);
        Apresentacao apresentacao2 = new Apresentacao(1, data, artista, cidade, 70.0, 90, 200);

        apresentacaoDAO.create(apresentacao1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> apresentacaoDAO.create(apresentacao2));
        assertEquals("Apresentação duplicada não é permitida.", exception.getMessage());
    }


    @Test
    void testReadPerformance() {
        for (int i = 0; i < 1000; i++) {
            apresentacaoDAO.create(new Apresentacao(i, new Date(), new Artista("Artista" + i, "Rock", 25, null), new Cidade("Cidade" + i, null), 50.0, 120, 100));
        }

        long startTime = System.currentTimeMillis();
        List<Apresentacao> apresentacoes = apresentacaoDAO.read();
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        assertTrue(duration < 1000);
    }

    @Test
    void testConcurrentUpdates() throws InterruptedException, ParseException {
        Date data = sdf.parse("01/08/2024");
        Artista artista = new Artista("Artista Teste", "Rock", 25, null);
        Cidade cidade = new Cidade("Cidade Teste", null);

        artistaDAO.create(artista);
        cidadeDAO.create(cidade);

        Apresentacao apresentacao = new Apresentacao(1, data, artista, cidade, 50.0, 120, 100);
        apresentacaoDAO.create(apresentacao);

        Thread thread1 = new Thread(() -> {
            apresentacao.setPrecoIngresso(60.0);
            apresentacaoDAO.update(apresentacao);
        });

        Thread thread2 = new Thread(() -> {
            apresentacao.setPrecoIngresso(70.0);
            apresentacaoDAO.update(apresentacao);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        Apresentacao updatedApresentacao = apresentacaoDAO.read().get(0);
        assertTrue(updatedApresentacao.getPrecoIngresso() == 60.0 || updatedApresentacao.getPrecoIngresso() == 70.0);
    }
}
