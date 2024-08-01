package util;

import com.db4o.ObjectContainer;
import com.db4o.Db4oEmbedded;

public class Util {
    private static ObjectContainer db;

    public static ObjectContainer getConnection() {
        if (db == null || db.ext().isClosed()) {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "database.db4o");
        }
        return db;
    }

    public static void closeConnection() {
        if (db != null && !db.ext().isClosed()) {
            db.close();
        }
    }
}
