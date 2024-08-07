package fachada;

import dao.ApresentacaoDAO;
import dao.ArtistaDAO;
import dao.CidadeDAO;
import excecoes.ExcecaoNegocio;
import model.Apresentacao;
import model.Artista;
import model.Cidade;

import java.util.Date;
import java.util.List;

public class Fachada {
    private ArtistaDAO artistaDAO;
    private ApresentacaoDAO apresentacaoDAO;
    private CidadeDAO cidadeDAO;
    private static final double PRECO_MINIMO_INGRESSO = 10.0;
    private static final int IDADE_MINIMA_ARTISTA = 18;

    public Fachada() {
        artistaDAO = new ArtistaDAO();
        cidadeDAO = new CidadeDAO();
        apresentacaoDAO = new ApresentacaoDAO();

    }

    public void createArtista(Artista artista) {
        if (artista.getIdade() < IDADE_MINIMA_ARTISTA) {
            throw ExcecaoNegocio.validacao("Artista deve ter no mínimo " + IDADE_MINIMA_ARTISTA + " anos.");
        }
        try {
            artistaDAO.create(artista);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar criar o artista: " + e.getMessage());
        }
    }

    public void createApresentacao(Apresentacao apresentacao) {
        if (apresentacao.getPrecoIngresso() < PRECO_MINIMO_INGRESSO) {
            throw ExcecaoNegocio.validacao("Preço do ingresso deve ser no mínimo " + PRECO_MINIMO_INGRESSO);
        }

        List<Apresentacao> apresentacoesExistentes = listarApresentacoesPorData(apresentacao.getData());
        for (Apresentacao a : apresentacoesExistentes) {
            if (a.getCidade().getNome().equals(apresentacao.getCidade().getNome()) &&
                    a.getArtista().getNome().equals(apresentacao.getArtista().getNome())) {
                throw ExcecaoNegocio.validacao("Artista já tem uma apresentação nesta cidade na data especificada.");
            }
        }

        if (apresentacao.getNumeroDeIngressosVendidos() > apresentacao.getCidade().getCapacidadePublico()) {
            throw ExcecaoNegocio.validacao("Número de ingressos vendidos excede a capacidade do local.");
        }

        try {
            apresentacaoDAO.create(apresentacao);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar criar a apresentação: " + e.getMessage());
        }
    }

    public void createCidade(Cidade cidade) {
        try {
            cidadeDAO.create(cidade);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar criar a cidade: " + e.getMessage());
        }
    }

    public List<Artista> listarArtistas() {
        try {
            return artistaDAO.read();
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao listar artistas: " + e.getMessage());
        }
    }

    public List<Apresentacao> listarApresentacoes() {
        try {
            return apresentacaoDAO.read();
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao listar apresentações: " + e.getMessage());
        }
    }

    public List<Cidade> listarCidades() {
        try {
            return cidadeDAO.read();
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao listar cidades: " + e.getMessage());
        }
    }

    public void updateArtista(Artista artista) {
        try {
            artistaDAO.update(artista);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar atualizar o artista: " + e.getMessage());
        }
    }

    public void updateApresentacao(Apresentacao apresentacao) {
        try {
            apresentacaoDAO.update(apresentacao);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar atualizar a apresentação: " + e.getMessage());
        }
    }

    public void updateCidade(Cidade cidade) {
        try {
            cidadeDAO.update(cidade);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar atualizar a cidade: " + e.getMessage());
        }
    }

    public void deleteArtista(Artista artista) {
        try {
            // Primeiro exclua as apresentações associadas ao artista
            List<Apresentacao> apresentacoes = apresentacaoDAO.listarApresentacoesPorArtista(artista.getNome());
            for (Apresentacao apresentacao : apresentacoes) {
                apresentacaoDAO.delete(apresentacao);
            }
            // Depois exclua o artista
            artistaDAO.delete(artista);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar deletar o artista: " + e.getMessage());
        }
    }

    public void deleteApresentacao(Apresentacao apresentacao) {
        try {
            apresentacaoDAO.delete(apresentacao);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar deletar a apresentação: " + e.getMessage());
        }
    }

    public void deleteCidade(Cidade cidade) {
        try {
            // Primeiro exclua as apresentações associadas à cidade
            List<Apresentacao> apresentacoes = apresentacaoDAO.listarApresentacoesPorCidade(cidade.getNome());
            for (Apresentacao apresentacao : apresentacoes) {
                apresentacaoDAO.delete(apresentacao);
            }
            // Depois exclua a cidade
            cidadeDAO.delete(cidade);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao tentar deletar a cidade: " + e.getMessage());
        }
    }

    public List<Apresentacao> listarApresentacoesPorData(Date data) {
        try {
            return apresentacaoDAO.listarApresentacoesPorData(data);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao listar apresentações por data: " + e.getMessage());
        }
    }

    public List<Artista> listarArtistasPorDataECidade(String cidadeNome, Date data) {
        try {
            return artistaDAO.listarArtistasPorDataECidade(cidadeNome, data);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao listar artistas por data e cidade: " + e.getMessage());
        }
    }

    public List<Artista> listarArtistasComMaisDeNApresentacoes(int n) {
        try {
            return artistaDAO.listarArtistasComMaisDeNApresentacoes(n);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao listar artistas com mais de N apresentações: " + e.getMessage());
        }
    }

    public Artista buscarArtistaPorNome(String nome) {
        try {
            return artistaDAO.read().stream()
                    .filter(artista -> artista.getNome().equalsIgnoreCase(nome))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao buscar artista: " + e.getMessage());
        }
    }

    public Cidade buscarCidadePorNome(String nome) {
        try {
            return cidadeDAO.read().stream()
                    .filter(cidade -> cidade.getNome().equalsIgnoreCase(nome))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw ExcecaoNegocio.conexao("Erro ao buscar cidade: " + e.getMessage());
        }
    }


    public void close() {
        artistaDAO.close();
        apresentacaoDAO.close();
        cidadeDAO.close();
    }
}
