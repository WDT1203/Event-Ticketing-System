import com.google.gson.Gson;
import java.io.*;
public class Configuration {
    private int totalTickets;
    private int ticketsRelRate;
    private int customerRetRate;
    private int maxTicketCapacity;

    public Configuration(int totalTickets, int ticketsRelRate, int customerRetRate, int maxTicketCapacity) {
        this.setTotalTickets(totalTickets);
        this.setTicketsRelRate(ticketsRelRate);
        this.setCustomerRetRate(customerRetRate);
        this.setMaxTicketCapacity(maxTicketCapacity);
    }
    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        if (totalTickets > 0) {
            this.totalTickets = totalTickets;
        }else {
            throw new IllegalArgumentException("Total tickets must be greater than 0");
        }
    }
    public int getTicketsRelRate() {
        return ticketsRelRate;
    }
    public void setTicketsRelRate(int ticketsRelRate) {
        if (ticketsRelRate > 0) {
            this.ticketsRelRate = ticketsRelRate;
        }else {
            throw new IllegalArgumentException("Tickets release rate must be greater than 0");
        }
    }
    public int getCustomerRetRate() {
        return customerRetRate;
    }
    public void setCustomerRetRate(int customerRetRate) {
        if (customerRetRate > 0) {
            this.customerRetRate = customerRetRate;
        }else {
            throw new IllegalArgumentException("Customer retrieval rate must be greater than 0");
        }
    }
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        if (maxTicketCapacity > 0) {
            this.maxTicketCapacity = maxTicketCapacity;
        }else {
            throw new IllegalArgumentException("Max ticket capacity must be greater than 0");
        }
    }
    public void saveToFile(String filePath) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        }
    }

    // Load configuration from JSON file
    public static Configuration loadFromFile(String filePath) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        }
    }


    @Override
    public String toString() {
        return String.format("Configuration: [Total Tickets: %d, Ticket Release Rate: %d, Customer Retrieval Rate: %d, Max Ticket Capacity: %d]",
                getTotalTickets(), getTicketsRelRate(), getCustomerRetRate(), getMaxTicketCapacity());
    }

}
