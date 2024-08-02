package dao;

import model.Cidade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CidadeDAOTest {
    private static CidadeDAO cidadeDAO;

    @BeforeAll
    static void setup() {
        cidadeDAO = new CidadeDAO();
    }

    @AfterEach
    void cleanUp() {
        for (Cidade cidade : cidadeDAO.read()) {
            cidadeDAO.delete(cidade);
        }
    }

    @AfterAll
    static void tearDown() {
        cidadeDAO.close();
    }

    //Este teste verifica a criação e leitura de uma cidade.

    //Cria uma cidade e a salva no DAO.
    //Lê todas as cidades do DAO.
    //Verifica se há apenas uma cidade no DAO e se o nome dessa cidade é "Cidade T
    @Test
    void testCreateAndReadCidade() {
        Cidade cidade = new Cidade("Cidade Teste", null);
        cidadeDAO.create(cidade);

        List<Cidade> cidades = cidadeDAO.read();
        assertEquals(1, cidades.size());
        assertEquals("Cidade Teste", cidades.get(0).getNome());
    }

    //Este teste verifica a atualização de uma cidade.

    //Cria uma cidade e a salva no DAO.
    //Atualiza o nome da cidade para "Cidade Atualizada".
    //Lê todas as cidades do DAO.
    //Verifica se o nome da cidade foi atualizado para "Cidade Atualizada".
    @Test
    void testUpdateCidade() {
        Cidade cidade = new Cidade("Cidade Teste", null);
        cidadeDAO.create(cidade);

        cidade.setNome("Cidade Atualizada");
        cidadeDAO.update(cidade);

        List<Cidade> cidades = cidadeDAO.read();
        assertEquals("Cidade Atualizada", cidades.get(0).getNome());
    }

    //Este teste verifica a exclusão de uma cidade.

    //Este teste verifica a exclusão de uma cidade.
    @Test
    void testDeleteCidade() {
        Cidade cidade = new Cidade("Cidade Teste", null);
        cidadeDAO.create(cidade);

        cidadeDAO.delete(cidade);

        List<Cidade> cidades = cidadeDAO.read();
        assertTrue(cidades.isEmpty());
    }
}
