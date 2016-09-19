package helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//helper class for logger
public class Logger {

    private Calendar cal;
    private SimpleDateFormat sdf;
    private String c;

    public Logger(Class cl){
        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss" );
        c = cl.getName();
    }

    public void out(String log){
        String message =time()+" - "+c+" - "+log;
        System.out.println(message);
    }
    public void error(String log){
        System.out.println(time()+" - "+c+" - ERROR "+log);
    }
    public String time(){
        return sdf.format(cal.getTime());
    }
}
