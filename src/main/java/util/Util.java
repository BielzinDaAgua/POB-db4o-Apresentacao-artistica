package util;

import com.db4o.ObjectContainer;
import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;
import model.Artista;
import model.Apresentacao;
import model.Cidade;

public class Util {
    private static ObjectContainer db;

    public static ObjectContainer getConnection() {
        if (db == null || db.ext().isClosed()) {
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();

            config.common().objectClass(Artista.class).cascadeOnDelete(true);
            config.common().objectClass(Artista.class).cascadeOnUpdate(true);
            config.common().objectClass(Artista.class).cascadeOnActivate(true);

            config.common().objectClass(Cidade.class).cascadeOnDelete(true);
            config.common().objectClass(Cidade.class).cascadeOnUpdate(true);
            config.common().objectClass(Cidade.class).cascadeOnActivate(true);

            config.common().objectClass(Apresentacao.class).cascadeOnDelete(false);
            config.common().objectClass(Apresentacao.class).cascadeOnUpdate(true);
            config.common().objectClass(Apresentacao.class).cascadeOnActivate(true);

            db = Db4oEmbedded.openFile(config, "database.db4o");
        }
        return db;
    }

    public static void closeConnection() {
        if (db != null && !db.ext().isClosed()) {
            db.close();
        }
    }
}
