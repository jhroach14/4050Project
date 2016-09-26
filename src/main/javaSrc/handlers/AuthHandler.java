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
    }

    //TODO implement non mock
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Exchange exchange = new Exchange(httpExchange);

        String user = exchange.getParam("user");
        String pass = exchange.getParam("pass");
        String[] resopnse = auth.isValidCredentials(user,pass);

        exchange.respondStr((resopnse[1]+"?token="+resopnse[0]), "text/html");
    }
}
