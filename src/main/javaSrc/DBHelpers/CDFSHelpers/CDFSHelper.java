package main.javaSrc.DBHelpers.CDFSHelpers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.DBHelpers.ObjectLayerImpl;
import main.javaSrc.DBHelpers.PersistenceLayerImpl;
import main.javaSrc.Entities.Entity;
import main.javaSrc.HttpClasses.DBExchange;

import java.sql.Connection;

/**
 * Created by User on 10/30/2016.
 */
public abstract class CDFSHelper {
    Entity entity;
    ObjectLayer objectLayer;
    DBExchange dbExchange;
    DbConnHelper dbConnHelper;
    Connection connection;
    public CDFSHelper(DBExchange dbExchange, DbConnHelper dbConnHelper){
        this.dbExchange = dbExchange;
        this.objectLayer = new ObjectLayerImpl();
        this.dbConnHelper = dbConnHelper;
        connection = dbConnHelper.getConnection();
        dbConnHelper.disableAutoCommit(connection);
        objectLayer.setPersistenceLayer( new PersistenceLayerImpl(connection,objectLayer));
    }
    public abstract Entity execute();
}
