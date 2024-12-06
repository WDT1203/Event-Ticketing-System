import java.util.Scanner;

public class Main {
    private static TicketPool ticketPool;
    private static Vendor[] vendors;
    private static Customer[] customers;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Configuration config = null;

        while (true) {
            System.out.println("CLI of Ticketing System");
            System.out.println("01. Configure System.");
            System.out.println("02. Start System.");
            System.out.println("03. Stop System.");
            System.out.println("04. Check System Status.");
            System.out.println("05. Save Configuration");
            System.out.println("06. Load Configuration");
            System.out.println("07. Display Logs.");
            System.out.println("08. Exit.");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    config = systemConfigure(input);
                    break;
                case 2:
                    startVendors(config);
                    break;
                case 3:
                    startCustomers(config);
                    break;
                case 4:
                    checkSystemStatus();
                    break;
                case 5:
                    saveConfiguration(config);
                    break;
                case 6:
                    config = loadConfiguration();
                    break;
                case 7:
                    LogReader.displayLogs();
                    break;
                case 8:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");

            }
        }

    }

    private static Configuration systemConfigure(Scanner input){
        System.out.println("Enter number of Total Tickets: ");
        int totalTickets = input.nextInt();
        System.out.println("Enter the rate of Ticket Release: ");
        int ticketsRelRate = input.nextInt();
        System.out.println("Enter the rate of Customer Retrieval: ");
        int customerRetRate = input.nextInt();
        System.out.println("Enter the max Ticket capacity: ");
        int maxTicketCapacity = input.nextInt();

        Configuration config = new Configuration(totalTickets, ticketsRelRate, customerRetRate, maxTicketCapacity);

        ticketPool = new TicketPool(config.getMaxTicketCapacity()); // Initialize the TicketPool
        System.out.println("System configured successfully.");
        return config;
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
            System.out.println("Please configure the system first!");
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

