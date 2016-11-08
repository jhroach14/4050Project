package main.javaSrc.DBHelpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by User on 11/7/2016.
 */
public class DbConnHelperImpl implements DbConnHelper {

    public DbConnHelperImpl(){
    }



    @Override
    public void disableAutoCommit(Connection conn) {

    }

    @Override
    public void enableAutoCommit(Connection conn) {

    }

    @Override
    public void commit(Connection conn) {

    }

    @Override
    public void rollback(Connection conn) {

    }

    @Override
    public Connection getConnection() {
        Connection dbConnection = null;

        try {

            Class.forName(DbConfig.DB_DRIVE_NAME);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DbConfig.DB_CONNECTION_URL, DbConfig.DB_CONNECTION_USERNAME, DbConfig.DB_CONNECTION_PWD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return null;
    }
}
