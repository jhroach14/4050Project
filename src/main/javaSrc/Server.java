package main.javaSrc;

import com.sun.net.httpserver.HttpsServer;
import main.javaSrc.handlers.AuthHandler;
import main.javaSrc.handlers.DataHandler;
import main.javaSrc.handlers.IndexHandler;
import main.javaSrc.HttpClasses.Configurator;
import main.javaSrc.helpers.Logger;
import main.javaSrc.services.AuthService;
import main.javaSrc.services.AuthServiceImpl;


import javax.net.ssl.*;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//server class built around HttpsServer
public class Server {

    private static Logger log = new Logger(Server.class);
    private int port;

    private HttpsServer server;
    private IndexHandler indexHandler;
    private DataHandler dataHandler;
    private AuthHandler authHandler;

    public Server(int port){
        this.port=port;
        try {

            log.out("Creating Server Instance at "+port+"...");
            server = HttpsServer.create(new InetSocketAddress(port),0);

        } catch (Exception e) {
            log.error("Server Instantiation failed\n"+e.getMessage());
            System.exit(0);
        }

        log.out("server Instantiated at port "+ port);
    }


    public void start(){

        log.out("Starting Server...");

        AuthService authService = new AuthServiceImpl();

        indexHandler = new IndexHandler(authService);
        dataHandler = new DataHandler(authService);
        authHandler = new AuthHandler(authService);


        server.createContext("/", indexHandler);
        server.createContext("/data",dataHandler);  //bind handlers
        server.createContext("/auth",authHandler);

        final Executor multiThread = Executors.newFixedThreadPool(15); //executor spins off new thread for every exchange
        server.setExecutor(multiThread);
        server.start();

        log.out("Server Started");
    }

    //configures server for HTTPS using selfsigned jks ssl key
    public void initiateSecure(){
        try {

            log.out("Initiating secure HTTPS connection...");

            char[] pass = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("key.jks");

            ks.load(fis,pass);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks,pass);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            server.setHttpsConfigurator(new Configurator(sslContext));

            log.out("Initiated Secure HTTPS connection ");

        }catch (Exception e){
            log.error("HTTPS initiation failed" +e.getMessage());
        }
    }


}
