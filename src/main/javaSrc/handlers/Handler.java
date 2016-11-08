package main.javaSrc.handlers;

import com.sun.net.httpserver.HttpHandler;
import main.javaSrc.helpers.Logger;
import main.javaSrc.services.AuthService;

//superclass for handlers should hold all things common to handlers
public abstract class Handler implements HttpHandler {

    private static Logger log = new Logger(Handler.class);
    protected AuthService auth;
    protected String token;


}
