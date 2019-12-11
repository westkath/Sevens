package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static Log log;
    private static Path pathToLogFile;

    private Log() {
        this.pathToLogFile = Paths.get("C:\\Users\\GA1\\Desktop\\test\\testing\\src\\main\\resources\\log.txt");
    }

    public static Log getInstance() {
        if (log == null) {
            log = new Log();
        }
        return log;
    }

    public void writeToLog(String origin, String message) {
        try {
            StringBuilder content = new StringBuilder();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dateS = formatter.format(date);
            origin = String.format("[%s] [%s] ", origin, dateS);

            content.append(origin);
            content.append(message);
            content.append("\n");

            Files.write(pathToLogFile, content.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
