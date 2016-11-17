package main.javaSrc.services;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.DBHelpers.Managers.AuthManager;
import main.javaSrc.HttpClasses.Exchange;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.Logger;

import java.sql.Connection;

//this handler takes care of client authorization
public class AuthServiceImpl implements AuthService{

    private static Logger log = new Logger(AuthServiceImpl.class);
    private AuthManager authManager;

    public AuthServiceImpl(){
        authManager = new AuthManager();
    }

    @Override
    public boolean isValidToken(String token,DbConnHelper dbConnHelper) {

        Connection conn = dbConnHelper.getConnection();
        boolean isValid = authManager.validateToken(token,conn);
        dbConnHelper.commit(conn);
        return isValid;
    }

    @Override
    public String[] isValidCredentials(Exchange exchange, DbConnHelper dbConnHelper) {
        String[] response;
        String userName = exchange.getParam("user");
        String password = exchange.getParam("pass");
        Connection conn = dbConnHelper.getConnection();

        try {
            response = authManager.validateCredentials(userName,password,conn);
        } catch (EVException e) {
            e.printStackTrace();
            response = new String[2];
            response[0]="";
            response[1]="http://localhost:9001/login.html";
        }
        dbConnHelper.commit(conn);
        return response;
    }

    //TODO: implement non mock
    /*@Override
    public boolean isValidToken(String token) {
        if(token==null){
            log.error("Null token");
            return false;
        }
        if(token.equals("testToken123")){
            log.out("Token "+token+" valid");
            return true;
        }
        log.error("token "+token+" invalid");
        return false;
    }

    @Override
    public String[] isValidCredentials(String user, String pass) {
        String[] response = new String[2];
        if (user.equals("officer@gmail.com") && pass.equals("officerpassword")) {
            response[0] = "testToken123";
            response[1] = "http://localhost:9001/officerIndex.html";
        } else if (user.equals("voter@gmail.com") && pass.equals("voterpassword")){
            response[0] = "testToken123";
            response[1] = "http://localhost:9001/voterIndex.html";
        } else {
            response[0] = "testToken123";
            response[1] = "http://localhost:9001/officerIndex.html";
        }

        return response;
    }*/

}
