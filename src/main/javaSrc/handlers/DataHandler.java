package main.javaSrc.handlers;

import com.sun.net.httpserver.HttpExchange;
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
        //TODO implement
    }


}
