package HttpClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import helpers.Logger;
import helpers.ParamMap;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Scanner;

//Wrapper class for an httpExchange providing related functionality
public class Exchange {

    private static Logger log = new Logger(Exchange.class);

    private String root = "resources/";
    private String request;
    private HttpExchange httpExchange;
    private ParamMap paramMap;

    public Exchange(HttpExchange httpExchange){

        this.httpExchange = httpExchange;
        this.request = getRequest();
        String spacer = "\n\n*********************************************";
        log.out(spacer+"Received request:\n"+request);
        try {
            this.paramMap = new ParamMap(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isHtmlRequest(){
        String path = httpExchange.getRequestURI().getPath();
        return path.substring(path.length() - 5).equals(".html");
    }

    //responds with resource asked for in request
    public void respondFile(){

        String path = httpExchange.getRequestURI().getPath();
        respondFile(path);

    }

    //responds with given file
    public void respondFile(String path){

        try {
            log.out("looking for: " + root + path);
            File file = new File(root + path).getCanonicalFile();

            if (!file.isFile()) {
                pageNotFound();

            } else {

                // Object exists and is a file: accept with response code 200.
                String mime = getMime(path);
                Headers h = httpExchange.getResponseHeaders();
                h.set("Content-Type", mime);
                h.add("Connection", "close");
                httpExchange.sendResponseHeaders(200, 0);

                OutputStream os = httpExchange.getResponseBody();
                FileInputStream fs = new FileInputStream(file);

                final byte[] buffer = new byte[0x10000];
                int count = 0;
                while ((count = fs.read(buffer)) >= 0) {
                    os.write(buffer, 0, count);
                }
                fs.close();
                os.flush();
                os.close();
                log.out("serving "+file.getName());

            }
        } catch (IOException e) {
            log.error(e.getStackTrace().toString());
        }

    }
    //responds with given string
    public void respondStr(String response, String mime){

        try {

            Headers h = httpExchange.getResponseHeaders();
            h.set("Content-Type", mime);
            h.add("Connection", "close");
            httpExchange.sendResponseHeaders(200, 0);

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes(),0,response.getBytes().length);
            os.flush();
            os.close();
            log.out("serving response " + response);

        }catch (Exception e){
            log.out("response error "+e.getStackTrace().toString());
        }
    }

    public String getRequest(){

        return httpExchange.getRequestURI().toString();
    }

    public String getParam(String param){
        return paramMap.getParam(param);
    }

    public void invalidToken(){

        try {

            log.error("Invalid token");
            String response = "401 (Unauthorized)\n";
            httpExchange.sendResponseHeaders(401, response.length());

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        }catch (Exception e){
            log.error("error invalid token "+e.getStackTrace().toString());
        }
    }

    public void pageNotFound(){

        try {

            log.error("not a file ");
            String response = "404 (Not Found)\n";
            httpExchange.sendResponseHeaders(404, response.length());

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        }catch (Exception e){
            log.error("error During page not found "+e.getStackTrace().toString());
        }
    }

    private String getMime(String path){

        String mime = "text/html";

        if (path.substring(path.length() - 3).equals(".js")){
            mime = "application/javascript";
        }

        if (path.substring(path.length() - 3).equals("css")) {
            mime = "text/css";
        }

        return mime;
    }

}
