package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.RandomString;

import java.sql.*;


/**
 * Created by User on 11/16/2016.
 */
public class AuthManager {

    public String[] validateCredentials(String userName, String password, Connection conn) throws EVException {

        try {

            String queryStr = "select User_Password from ElectionsOfficer where Username = '"+userName+"'";
            Statement stmt = conn.createStatement();
                stmt.execute(queryStr);
                ResultSet rs = stmt.getResultSet();
                String userPass="";
            if(rs.next()){
                //while(rs.next()){
                    userPass = rs.getString(1);
                //}
                if (password.equals(userPass)){
                    String[] response = new String[2];
                    response[0] = createToken(userName,conn);
                    response[1] = "http://localhost:9001/officerIndex.html";
                    return response;
                }else{
                    throw new EVException("incorrect password for officer "+userName);
                }
            }else{
                queryStr = "select User_Password from Voter where Username = '"+userName+"'";
                stmt = conn.createStatement();
                if (stmt.execute(queryStr)){
                    ResultSet rsv = stmt.getResultSet();
                    String userPassv ="";
                    while(rsv.next()){
                        userPassv = rsv.getString(1);
                    }
                    if (password.equals(userPassv)){
                        String[] response = new String[2];
                        response[0] = createToken(userName,conn);
                        response[1] = "http://localhost:9001/voterIndex.html";
                        return response;
                    }else{
                        throw new EVException("incorrect password for voter "+userName);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new EVException("AuthManager: could not find user with username "+userName);
    }

    private String createToken(String userName, Connection conn) throws EVException {

        RandomString randStr = new RandomString(4);
        String token = userName+"Token"+randStr.nextString();

        checkUserHasExistingToken(userName,conn);

        String query = "insert into Token values ( '"+userName+"', '"+token+"')";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            if(stmt.executeUpdate()>=1){
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new EVException("could not insert new Token "+token);
    }

    private void checkUserHasExistingToken(String userName, Connection conn) {

        try {

            Statement stmt= conn.createStatement();
            String query = "delete from Token where User_Name = '"+userName+"'";
            stmt.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateToken(String token, Connection connection) {
        String query = "select Token from Token where Token = '"+token+"'";
        boolean isValid = false;
        try {
            Statement stmt = connection.createStatement();
            //I don't think this logic is correct, this if statement only checks if the query ran okay
            //i.e. there were no sql syntax errors, not that it actually returns something.
            if(stmt.execute(query)){
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
