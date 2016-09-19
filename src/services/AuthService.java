package services;

import helpers.Logger;

//used to preform authentication based services
public class AuthService {

    private static Logger log = new Logger(AuthService.class);

    //TODO implement non mock version
    public boolean isValidToken(String token) {
        if(token==null){
            log.out("Null token");
            return false;
        }
        if(token.equals("testToken123")){
            log.out("Token "+token+" valid");
            return true;
        }
        log.out("token "+token+"invalid");
        return false;
    }

    //TODO implement non mock
    public String[] isValidCredentials(String user, String pass) {

        String[] response = new String[2];
        response[0] = "testToken123";
        response[1] = "https://localhost:9001/officerIndex.html";

        return response;
    }
}
