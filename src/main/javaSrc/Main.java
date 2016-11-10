package main.javaSrc;

/**
 * Created by User on 8/29/2016.
 */
public class Main {

    public static void main(String[] args) {

        Server server = new Server(9001);
        //server.initiateSecure();
        server.start();

    }

}
