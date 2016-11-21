package main.javaSrc.DBHelpers;

import java.sql.Connection;

/**
 * Created by User on 11/7/2016.
 */
public interface DbConnHelper {

    void disableAutoCommit(Connection conn);
    void enableAutoCommit(Connection conn);

    void commit(Connection conn);
    void rollback(Connection conn);

    Connection getConnection();

}
