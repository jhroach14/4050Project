package main.javaSrc.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.javaSrc.DBHelpers.CDFSTHelpers.*;
import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.DBHelpers.DbConnHelperImpl;
import main.javaSrc.Entities.Entity;
import main.javaSrc.HttpClasses.DBExchange;
import main.javaSrc.helpers.Logger;
import main.javaSrc.services.AuthService;

import java.util.List;

//handler for processing data requests
public class DataHandler extends Handler {

    private static Logger log = new Logger(DataHandler.class);

    public DataHandler(AuthService auth){
        this.auth = auth;
    }

    @Override
    public void handle(HttpExchange httpExchange){
        DBExchange exchange = new DBExchange(httpExchange);
        token = exchange.getParam("token");
        DbConnHelper dbConnHelper = new DbConnHelperImpl();

        if(auth.isValidToken(token,dbConnHelper)){

            CDFSTHelper helper=null;
            String actionType = exchange.getDBRequestType();



            switch (actionType){

                case "create":
                    helper = new CreateHelper(exchange,dbConnHelper);
                    break;

                case "delete":
                    helper = new DeleteHelper(exchange,dbConnHelper);
                    break;

                case "find":
                    helper = new FindHelper(exchange,dbConnHelper);
                    break;

                case "store":
                    helper = new StoreHelper(exchange,dbConnHelper);
                    break;

                case "traverse":
                    helper = new TraverseHelper(exchange,dbConnHelper);
                    break;

                default:
                    exchange.pageNotFound();
                    break;
            }
            if(helper != null){
                List<Entity> entities = helper.execute();

                if(entities.get(0) != null) {
                    exchange.returnObjectList(entities);
                }else{
                    exchange.respondStr("200 success","text/html");
                }
            }
        }else{
            exchange.invalidToken();
        }
    }


}
