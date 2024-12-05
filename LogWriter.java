import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogWriter {
    private static final String LOG_FILE = "logs.json";
    private static final Gson gson = new Gson();

    public static void log(String message) {
        // Create a map to hold log information
        String log = String.format("{\"level\":\"INFO\", \"message\":\"%s\", \"source\":\"TicketingSystem\", \"timestamp\":\"%s\"}",
                message, new Date());

        // Print to console
        System.out.println(log);

        // Append to JSON log file
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(log + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error writing to JSON log file: " + e.getMessage());
        }
    }
}