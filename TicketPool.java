import java.util.LinkedList;
import java.util.Queue;


public class TicketPool {
    private final int maximumTicketCapacity; // Maximum capacity of the ticket pool
    private final Queue<Ticket> ticketQueue; // Ticket storage

    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    // Producer (Vendor) adds tickets
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumTicketCapacity) {
            try {
                LogWriter.log("Vendor waiting: Pool is full.");
                wait(); // Wait until there's space in the pool
            } catch (InterruptedException e) {
                LogWriter.log("Vendor interrupted while waiting: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        ticketQueue.add(ticket);
        notifyAll(); // Notify waiting customers
        LogWriter.log("Ticket added: " + ticket + " | Current Pool Size: " + ticketQueue.size());
    }

    // Consumer (Customer) buys tickets
    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                LogWriter.log("Customer waiting: No tickets available.");
                wait(); // Wait until tickets are added
            } catch (InterruptedException e) {
                LogWriter.log("Customer interrupted while waiting: " + e.getMessage());
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Ticket ticket = ticketQueue.poll(); // Retrieve ticket
        notifyAll(); // Notify waiting vendors
        LogWriter.log("Ticket bought: " + ticket + " | Remaining Pool Size: " + ticketQueue.size());
        return ticket;
    }

    public synchronized int getTicketCount() {
        return ticketQueue.size();
    }
}
