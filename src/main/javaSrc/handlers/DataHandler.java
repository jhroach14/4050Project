package main.javaSrc.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.javaSrc.DBHelpers.CDFSHelpers.*;
import main.javaSrc.Entities.Entity;
import main.javaSrc.HttpClasses.DBExchange;
import main.javaSrc.helpers.Logger;
import main.javaSrc.services.AuthService;

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

        if(auth.isValidToken(token)){
            CDFSHelper helper=null;
            String actionType = exchange.getDBRequestType();
            switch (actionType){

                case "create":
                    helper = new CreateHelper(exchange);
                    break;

                case "delete":
                    helper = new DeleteHelper(exchange);
                    break;

                case "find":
                    helper = new FindHelper(exchange);
                    break;

                case "store":
                    helper = new StoreHelper(exchange);
                    break;

                default:
                    exchange.pageNotFound();
                    break;
            }
            if(helper != null){
                Entity entity = helper.execute();
                exchange.returnObject(entity);
            }
        }else{
            exchange.invalidToken();
        }
    }


}
