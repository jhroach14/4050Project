package main.javaSrc.services;

import main.javaSrc.helpers.Logger;

//used to preform authentication based services
public interface  AuthService {

    public boolean isValidToken(String token);

    public String[] isValidCredentials(String user, String pass);
}
