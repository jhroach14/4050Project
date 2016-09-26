package main.javaSrc.HttpClasses;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import main.javaSrc.helpers.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

//more https stuff
public class Configurator extends HttpsConfigurator{
    private static Logger log = new Logger(Configurator.class);
    public Configurator(SSLContext sslContext) {
        super(sslContext);
    }

    @Override
    public void configure(HttpsParameters params){
        try{
            SSLContext c = SSLContext.getDefault();
            SSLEngine engine = c.createSSLEngine();
            params.setNeedClientAuth(false);
            params.setCipherSuites(engine.getEnabledCipherSuites());

            SSLParameters defaultParams = c.getDefaultSSLParameters();
            params.setSSLParameters(defaultParams);
        }catch (Exception e){
            log.error("Failed to create https port\n"+e.getStackTrace().toString());
        }
    }
}