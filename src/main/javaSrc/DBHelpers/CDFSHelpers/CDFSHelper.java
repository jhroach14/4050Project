package main.javaSrc.DBHelpers.CDFSHelpers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.DBHelpers.ObjectLayerImpl;
import main.javaSrc.DBHelpers.PersistenceLayerImpl;
import main.javaSrc.Entities.Entity;
import main.javaSrc.HttpClasses.DBExchange;

/**
 * Created by User on 10/30/2016.
 */
public abstract class CDFSHelper {
    Entity entity;
    ObjectLayer objectLayer;
    DBExchange dbExchange;
    DbConnHelper dbConnHelper;
    public CDFSHelper(DBExchange dbExchange, DbConnHelper dbConnHelper){
        this.objectLayer = new ObjectLayerImpl(new PersistenceLayerImpl());
        this.dbExchange = dbExchange;
    }
    public abstract Entity execute();
}
