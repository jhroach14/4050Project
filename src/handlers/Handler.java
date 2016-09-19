package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import HttpClasses.Exchange;
import helpers.Logger;
import services.AuthService;

//superclass for handlers
public abstract class Handler implements HttpHandler {

    private static Logger log = new Logger(Handler.class);
    protected AuthService auth;
    protected String token;


}
