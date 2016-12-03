package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class TokenImpl extends EntityImpl implements Token {

    private String tokenValue;

    public TokenImpl(String token) {
        this.tokenValue = token;
    }

    public TokenImpl() {

    }

    @JsonIgnore
    @Override
    public String getRestoreString(){

        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select User_Name, Token from Token";

        condition.setLength( 0 );
        query.append( restoreStr );


        if( getTokenValue() != null )
            condition.append( " where Token = '" + getTokenValue() + "'" );


        query.append( condition );

        return query.toString();
    }

    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        return null;
    }

    @JsonIgnore
    @Override
    public String getType() {
        return "Token";
    }

    @Override
    public String getTokenValue() {
        return tokenValue;
    }

    @Override
    public void setTokenValue(String token) { this.tokenValue = token; }



}