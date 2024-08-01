package dao;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import model.Apresentacao;
import util.Util;

import java.util.Date;
import java.util.List;

public class ApresentacaoDAO {
    private ObjectContainer db;

    public ApresentacaoDAO() {
        db = Util.getConnection();
    }

    public void create(Apresentacao apresentacao) {
        if (apresentacao.getArtista() == null || apresentacao.getCidade() == null) {
            throw new IllegalArgumentException("Apresentação deve ter um artista e uma cidade.");
        }
        for (Apresentacao existente : read()) {
            System.out.println("Verificando apresentação: " + existente.getId());
            if (existente.getId() == apresentacao.getId()) {
                throw new IllegalArgumentException("Apresentação duplicada não é permitida.");
            }
        }
        db.store(apresentacao);
    }

    public List<Apresentacao> read() {
        Query query = db.query();
        query.constrain(Apresentacao.class);
        return query.execute();
    }

    public void update(Apresentacao apresentacao) {
        db.store(apresentacao);
    }

    public void delete(Apresentacao apresentacao) {
        db.delete(apresentacao);
    }

    public List<Apresentacao> listarApresentacoesPorData(Date data) {
        Query query = db.query();
        query.constrain(Apresentacao.class);
        query.descend("data").constrain(data);
        return query.execute();
    }

    public void close() {
        Util.closeConnection();
    }
}
