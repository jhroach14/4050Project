package main.javaSrc.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.javaSrc.DBHelpers.DbConnHelperImpl;
import main.javaSrc.HttpClasses.Exchange;
import main.javaSrc.helpers.Logger;
import main.javaSrc.services.AuthService;


//handles all resource/misc requests
public class IndexHandler extends Handler {

    private static Logger log = new Logger(IndexHandler.class);

    public IndexHandler(AuthService auth){
        this.auth =auth;
    }// get auth instance from server

    //default control flow for https requests
    @Override
    public void handle(HttpExchange httpExchange){

        Exchange exchange = new Exchange(httpExchange); //create exchange to handle dirty work
        token = exchange.getParam("token");

        if (exchange.isHtmlRequest()){//if they request an html page

            if (auth.isValidToken(token,new DbConnHelperImpl())){//respond with resource if valid token
                exchange.respondFile();
            }else{
                exchange.respondFile("login.html");//requests with invalid token sent to login
            }
        }else {
            exchange.respondFile();// else respond with non html resource
        }
    }
}
