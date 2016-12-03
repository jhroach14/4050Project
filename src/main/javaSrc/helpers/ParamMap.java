package main.javaSrc.helpers;

import java.util.LinkedHashMap;
import java.util.Map;

//wrapper for request parameters
public class ParamMap {

    private static Logger log = new Logger(ParamMap.class);

    private Map<String, String> paramMap;

    public ParamMap(String query){

            this.paramMap = createParamMap(query);
    }

    //parses url for parameters
    public Map<String, String> createParamMap(String query){
        Map<String, String> query_pairs= new LinkedHashMap<>();
        int start = query.indexOf("?");
        if(start!=-1){
            try {
                query = query.substring(start+1);
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    if (idx!=-1){
                        query_pairs.put(pair.substring(0, idx),pair.substring(idx + 1));
                    }
                }
            }catch (Exception e){
                log.error("Failed to create paramMap\n"+e.getMessage());
                System.exit(0);
            }
        }
        log.out("created paramMap: "+query_pairs.toString());
        return query_pairs;// returns key value map
    }

    public Map<String,String> getParamMap(){

        return paramMap;
    }

    public String getParam(String param){
        boolean present =paramMap.containsKey(param);
        if(present){
            return paramMap.get(param);
        }else{
            log.error("Parameter "+param+" not found returning empty string");
            return "";
        }

    }
}
