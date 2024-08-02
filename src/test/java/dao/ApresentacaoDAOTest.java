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


    //Este teste verifica a criação e leitura de uma apresentação.

    //Cria uma data, um artista e uma cidade.
    //Cria uma apresentação associada ao artista e à cidade.
    //Salva a apresentação no DAO.
    //Verifica se a leitura do DAO retorna exatamente uma apresentação e se o nome do artista associado é "Artista Teste".
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


    //Este teste verifica a atualização de uma apresentação.

    //Cria uma data, um artista e uma cidade.
    //Cria uma apresentação associada ao artista e à cidade.
    //Salva a apresentação no DAO.
    //Atualiza o preço do ingresso da apresentação.
    //Verifica se a leitura do DAO retorna a apresentação com o preço do ingresso atualizado para 60.0.
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

    //Este teste verifica a exclusão de uma apresentação.

    //Cria uma data, um artista e uma cidade.
    //Cria uma apresentação associada ao artista e à cidade.
    //Salva a apresentação no DAO.
    //Exclui a apresentação do DAO.
    //Verifica se a leitura do DAO retorna uma lista vazia, indicando que a apresentação foi excluída com sucesso.
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


    //Este teste verifica a listagem de apresentações por data.

    //Cria duas datas, um artista e uma cidade.
    //Cria duas apresentações em datas diferentes associadas ao mesmo artista e cidade.
    //Salva ambas as apresentações no DAO.
    //Lista as apresentações para a primeira data.
    //Verifica se a listagem retorna exatamente uma apresentação e se a data dessa apresentação é igual à primeira data.
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
