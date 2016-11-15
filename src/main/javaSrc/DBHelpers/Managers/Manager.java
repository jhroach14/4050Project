package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by User on 11/14/2016.
 */
public abstract class Manager {

    protected ObjectLayer objectLayer = null;
    protected Connection conn = null;

    public Manager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    public Manager(){

    }

    public Entity setId(PreparedStatement stmt,Entity entity) throws SQLException {

        String sql = "select last_insert_id()";
        if( stmt.execute( sql ) ) { // statement returned a result

            // retrieve the result
            ResultSet r = stmt.getResultSet();

            // we will use only the first row!
            //
            while( r.next() ) {

                // retrieve the last insert auto_increment value
                int id = r.getInt( 1 );
                if( id > 0 )
                    entity.setId( id ); // set this person's db id (proxy object)
            }
        }
        return entity;
    }

}
