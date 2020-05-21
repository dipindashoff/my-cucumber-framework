package reusables;

import java.util.logging.Logger;

public class Log {

    private static Logger Log = Logger.getLogger(reusables.Log.class.getName());

    public static void startLog(String message) {Log.info(message);}

    public static void endLog(String message) {Log.info(message);}

    public static void info(String message) {Log.info(message);}

    public static void warn(String message) {Log.info(message);}

    public static void error(String message) {Log.info(message);}

    public static void fatal(String message) {Log.info(message);}

    public static void debug(String message) {Log.info(message);}

}
