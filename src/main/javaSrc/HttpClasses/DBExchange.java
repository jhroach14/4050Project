package main.javaSrc.HttpClasses;

import com.sun.net.httpserver.HttpExchange;
import main.javaSrc.Entities.Entity;
import main.javaSrc.helpers.ParamMap;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static oracle.net.aso.C00.t;

/**
 * Created by User on 10/30/2016.
 */
public class DBExchange extends Exchange{

    private String dbRequest;

    private String requestBody;
    public DBExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        this.paramMap = new ParamMap(getRequest());
        this.dbRequest = getDbRequest();
        this.requestBody = getDBRequestBody();
        String spacer = "\n\n*********************************************";
        log.out(spacer+"Received DB request:\n"+dbRequest);
    }

    public void returnObject(Entity entity){



    }

    private String getDBRequestBody() {
        String RequestBody=null;
        try {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            int b;
            StringBuilder buf = new StringBuilder(512);
            while ((b = br.read()) != -1) {
                buf.append((char) b);
            }

            br.close();
            isr.close();
            requestBody = buf.toString();
        }catch (Exception e ){
            log.error(e.toString());
        }
        return requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getDBRequestObjectType(){
        int dataLoc = dbRequest.indexOf("data/")+5;
        int typeLoc = dbRequest.indexOf('/',dataLoc)+1;
        int objectTypeLoc = dbRequest.indexOf('/',typeLoc);
        if(objectTypeLoc==-1){
            return dbRequest.substring(typeLoc,dbRequest.length());
        }else{
            return dbRequest.substring(typeLoc,objectTypeLoc);
        }
    }

    public String getDBRequestType(){
        int dataLoc = dbRequest.indexOf("data/")+5;
        String requestType = dbRequest.substring(dataLoc,dbRequest.indexOf('/',dataLoc));
        return requestType;
    }

    private String getDbRequest() {
        dbRequest = httpExchange.getRequestURI().toString();
        int cutoff = dbRequest.indexOf('?');
        if (cutoff == -1) {
            cutoff = dbRequest.length();
        }
        dbRequest = dbRequest.substring(0, cutoff);
        return dbRequest;
    }

}
