import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class LogReader {
    public static void displayLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader("logs.json"))) {
            String line;
            Gson gson = new Gson();

            while ((line = reader.readLine()) != null) {
                // Parse each line of JSON into a Map
                Map<String, Object> logEntry = gson.fromJson(line, Map.class);

                // Display log in human-readable format
                System.out.printf("[%s] %s: %s (Source: %s)%n",
                        logEntry.get("timestamp"),
                        logEntry.get("level"),
                        logEntry.get("message"),
                        logEntry.get("source"));
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON log file: " + e.getMessage());
        }
    }
}
