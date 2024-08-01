package dao;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import model.Artista;
import model.Apresentacao;
import util.Util;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistaDAO {
    private ObjectContainer db;

    public ArtistaDAO() {
        db = Util.getConnection();
    }

    public void create(Artista artista) {
        db.store(artista);
    }

    public List<Artista> read() {
        Query query = db.query();
        query.constrain(Artista.class);
        return query.execute();
    }

    public void update(Artista artista) {
        db.store(artista);
    }

    public void delete(Artista artista) {
        db.delete(artista);
    }

    public List<Artista> listarArtistasPorDataECidade(String cidadeNome, Date data) {
        Query query = db.query();
        query.constrain(Apresentacao.class);
        query.descend("cidade").descend("nome").constrain(cidadeNome);
        query.descend("data").constrain(data);

        List<Apresentacao> apresentacoes = query.execute();
        return apresentacoes.stream().map(Apresentacao::getArtista).distinct().collect(Collectors.toList());
    }

    public List<Artista> listarArtistasComMaisDeNApresentacoes(int n) {
        List<Artista> artistas = read();
        return artistas.stream().filter(artista -> {
            List<Apresentacao> apresentacoes = artista.getListaApresentacao();
            return apresentacoes != null && apresentacoes.size() > n;
        }).collect(Collectors.toList());
    }

    public void close() {
        Util.closeConnection();
    }
}
