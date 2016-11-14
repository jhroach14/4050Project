package main.javaSrc.DBHelpers.CDFSHelpers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.DBHelpers.ObjectLayerImpl;
import main.javaSrc.DBHelpers.PersistenceLayerImpl;
import main.javaSrc.Entities.Entity;
import main.javaSrc.HttpClasses.DBExchange;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10/30/2016.
 */
public abstract class CDFSTHelper {
    Entity entity;
    List<Entity> entities;
    ObjectLayer objectLayer;
    DBExchange dbExchange;
    DbConnHelper dbConnHelper;
    Connection connection;
    public CDFSTHelper(DBExchange dbExchange, DbConnHelper dbConnHelper){
        entities = new ArrayList<>();
        this.dbExchange = dbExchange;
        this.objectLayer = new ObjectLayerImpl();
        this.dbConnHelper = dbConnHelper;
        connection = dbConnHelper.getConnection();
        dbConnHelper.disableAutoCommit(connection);
        objectLayer.setPersistenceLayer( new PersistenceLayerImpl(connection,objectLayer));
    }
    public String[] parseObjects(String requestBody) {

        String[] strArr = new String[2];

        strArr[0] = requestBody.substring(0,requestBody.indexOf("**|**"));
        strArr[1] = requestBody.substring(requestBody.indexOf("**|**")+5,requestBody.length());

        return strArr;

    }
    public abstract List<Entity> execute();
}
