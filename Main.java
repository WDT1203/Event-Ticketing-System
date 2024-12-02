import java.util.Scanner;
import java.io.*;
import com.google.gson.Gson;



public class Main {

    private static final String BASE_URL = "http://localhost:8080";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("CLI of Ticketing System");
            System.out.println("01. Configure System.");
            System.out.println("02. Start System.");
            System.out.println("03. Stop System.");
            System.out.println("04. Check System Status.");
            System.out.println("05. Exit.");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    systemConfigure(input);
                    break;
                case 2:
                    try {
                        sendPostRequest("/system/start", null);
                    } catch (IOException e) {
                        System.out.println("Error starting the system: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        sendPostRequest("/system/stop", null);
                    } catch (IOException e) {
                        System.out.println("Error stopping the system: " + e.getMessage());
                    }
                    break;
                case 4:
                    checkSystemStatus();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");

            }
        }

    }

    private static void systemConfigure(Scanner input){
        System.out.println("Enter number of Total Tickets: ");
        int totalTickets = input.nextInt();
        System.out.println("Enter the rate of Ticket Release: ");
        int ticketsRelRate = input.nextInt();
        System.out.println("Enter the rate of Customer Retrieval: ");
        int customerRetRate = input.nextInt();
        System.out.println("Enter the max Ticket capacity: ");
        int maxTicketCapacity = input.nextInt();

        Configuration config = new Configuration(totalTickets, ticketsRelRate, customerRetRate, maxTicketCapacity);

        if (!config.isValid()) {
            System.out.println("Invalid configuration! Please try again.");
            return;
        }

        String jsonPayload = new Gson().toJson(config);
        try {
            String response = HTTPComm.sendPostRequest(BASE_URL + "/config", jsonPayload);
            System.out.println("Response: " + response);
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void sendPostRequest(String endpoint, String jsonPayload) throws IOException {
        String response = HTTPComm.sendPostRequest(BASE_URL + endpoint, jsonPayload);
        System.out.println("Response: " + response);
    }

    private static void checkSystemStatus() {
        // Example implementation (this should likely call HTTP API to check system status)
        try {
            String status = HTTPComm.sendGetRequest(BASE_URL + "/status");
            System.out.println("System Status: " + status);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
