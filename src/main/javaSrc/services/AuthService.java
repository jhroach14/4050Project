package main.javaSrc.services;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.HttpClasses.Exchange;
import main.javaSrc.helpers.Logger;

//used to preform authentication based services
public interface  AuthService {

    public boolean isValidToken(String token,DbConnHelper dbConnHelper);

    public String[] isValidCredentials(Exchange exchange, DbConnHelper dbConnHelper);
}
