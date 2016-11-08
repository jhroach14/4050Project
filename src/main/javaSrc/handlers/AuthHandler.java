package main.javaSrc.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.javaSrc.HttpClasses.Exchange;
import main.javaSrc.helpers.Logger;
import main.javaSrc.services.AuthService;

import java.io.IOException;

//handles auth request
public class AuthHandler extends Handler {

    private static Logger log = new Logger(AuthHandler.class);

    public AuthHandler(AuthService auth){
        this.auth = auth;
    }//takes in auth service instance from server

    //TODO implement non mock
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Exchange exchange = new Exchange(httpExchange);//create exchange to handle nitty gritty

        String user = exchange.getParam("user");
        String pass = exchange.getParam("pass");
        String[] resopnse = auth.isValidCredentials(user,pass); //use service to validate credintials

        exchange.respondStr((resopnse[1]+"?token="+resopnse[0]), "text/html"); //return redirect
    }
}
