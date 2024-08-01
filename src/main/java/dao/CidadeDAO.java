package dao;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import model.Cidade;
import util.Util;

import java.util.List;

public class CidadeDAO {
    private ObjectContainer db;

    public CidadeDAO() {
        db = Util.getConnection();
    }

    public void create(Cidade cidade) {
        db.store(cidade);
    }

    public List<Cidade> read() {
        Query query = db.query();
        query.constrain(Cidade.class);
        return query.execute();
    }

    public void update(Cidade cidade) {
        db.store(cidade);
    }

    public void delete(Cidade cidade) {
        db.delete(cidade);
    }

    public void close() {
        Util.closeConnection();
    }
}
