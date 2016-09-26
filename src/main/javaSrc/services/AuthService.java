package main.javaSrc.services;

import main.javaSrc.helpers.Logger;

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
        if (user.equals("officer@gmail.com") && pass.equals("officerpassword")) {
            response[0] = "testToken123";
            response[1] = "https://localhost:9001/officerIndex.html";
        } else if (user.equals("voter@gmail.com") && pass.equals("voterpassword")){
            response[0] = "testToken123";
            response[1] = "https://localhost:9001/voterIndex.html";
        } else {
            response[0] = "testToken123";
            response[1] = "https://localhost:9001/officerIndex.html";
        }

        return response;
    }
}
