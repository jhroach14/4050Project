package main.javaSrc.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.javaSrc.HttpClasses.Exchange;
import main.javaSrc.helpers.Logger;
import main.javaSrc.services.AuthService;


//handles all resource/misc requests
public class IndexHandler extends Handler {

    private static Logger log = new Logger(IndexHandler.class);

    public IndexHandler(AuthService auth){
        this.auth =auth;
    }

    //default control flow for https requests
    @Override
    public void handle(HttpExchange httpExchange){

        Exchange exchange = new Exchange(httpExchange);
        token = exchange.getParam("token");

        if (exchange.isHtmlRequest()){

            if (auth.isValidToken(token)){//respond with resource if valid token
                exchange.respondFile();
            }else{
                exchange.respondFile("login.html");//requests with invalid token sent to login
            }
        }else {
            exchange.respondFile();//respond with non html resource
        }
    }
}
