import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static TicketPool ticketPool;
    private static Vendor[] vendors;
    private static Customer[] customers;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Configuration config = null;

        while (true) {
            System.out.println("************************");
            System.out.println("CLI of Ticketing System");
            System.out.println("01. Configure System.");
            System.out.println("02. Start System.");
            System.out.println("03. Check System Status.");
            System.out.println("04. Save Configuration");
            System.out.println("05. Load Configuration");
            System.out.println("06. Display Logs.");
            System.out.println("07. Exit.");
            System.out.println("**-----------------------------**");
            System.out.print("Enter your choice: ");

            try {
                int choice = input.nextInt();

            switch (choice) {
                case 1:
                    config = systemConfigure(input);
                    break;
                case 2:
                    startVendors(config);
                    startCustomers(config);
                    break;
                case 3:
                    checkSystemStatus();
                    break;
                case 4:
                    saveConfiguration(config);
                    break;
                case 5:
                    config = loadConfiguration();
                    break;
                case 6:
                    LogReader.displayLogs();
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.out.println("\"Invalid input. Enter a valid option(1-7 choices/) !\"");

            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Enter a valid option(1-7 choices/) !");
            input.nextLine();
        }
        }

    }

    private static Configuration systemConfigure(Scanner input){
        System.out.print("Enter number of Total Tickets: ");
        int totalTickets = input.nextInt();
        System.out.print("Enter the rate of Ticket Release: ");
        int ticketsRelRate = input.nextInt();
        System.out.print("Enter the rate of Customer Retrieval: ");
        int customerRetRate = input.nextInt();
        System.out.print("Enter the max Ticket capacity: ");
        int maxTicketCapacity = input.nextInt();

        Configuration config;
        try {
            config = new Configuration(totalTickets, ticketsRelRate, customerRetRate, maxTicketCapacity);
            ticketPool = new TicketPool(config.getMaxTicketCapacity()); // Initialize the TicketPool
            System.out.println("System configured successfully.");
            return config;
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return null; // Indicate configuration failure
        }



    }

    private static void startVendors(Configuration config) {
        if (config == null) {
            System.out.println("Please configure the system first!");
            return;
        }

        vendors = new Vendor[5]; // Example: 5 vendors
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(config.getTotalTickets(), config.getTicketsRelRate(), ticketPool);
            new Thread(vendors[i], "Vendor-" + i).start();
        }
        System.out.println("Vendors started.");
    }

    private static void startCustomers(Configuration config) {
        if (config == null) {
            return;
        }

        customers = new Customer[5]; // Example: 5 customers
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(ticketPool, config.getCustomerRetRate(), 10);
            new Thread(customers[i], "Customer-" + i).start();
        }
        System.out.println("Customers started.");
    }

    private static void checkSystemStatus() {
        if (ticketPool == null) {
            System.out.println("Ticket pool is not initialized.");
            return;
        }

        System.out.println("Tickets currently available: " + ticketPool.getTktCount());
    }

    private static void saveConfiguration(Configuration config) {
        if (config == null) {
            System.out.println("No configuration to save!");
            return;
        }

        try {
            config.saveToFile("config.json");
            System.out.println("Configuration saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    private static Configuration loadConfiguration() {
        try {
            Configuration config = Configuration.loadFromFile("config.json");
            System.out.println("Configuration loaded successfully: " + config);
            return config;
        } catch (Exception e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            return null;
        }
    }
}

