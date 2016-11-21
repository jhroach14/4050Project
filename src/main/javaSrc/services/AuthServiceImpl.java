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
        dbConnHelper.disableAutoCommit(conn);
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
        dbConnHelper.disableAutoCommit(conn);

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


}
