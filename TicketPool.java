import java.util.LinkedList;
import java.util.Queue;


public class TicketPool {
    private final int maxTktCapacity; // Maximum capacity of the ticket pool
    private final Queue<Ticket> tktQueue; // Ticket storage

    public TicketPool(int maxTktCapacity) {
        this.maxTktCapacity = maxTktCapacity;
        this.tktQueue = new LinkedList<>();
    }

    // Producer (Vendor) adds tickets
    public synchronized void addTicket(Ticket ticket) {
        while (tktQueue.size() >= maxTktCapacity) {
            try {
                LogWriter.log("Vendor waiting: Pool is full.");
                wait(); // Wait until there's space in the pool
            } catch (InterruptedException e) {
                LogWriter.log("Vendor interrupted while waiting: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        tktQueue.add(ticket);
        notifyAll(); // Notify waiting customers
        LogWriter.log("Ticket added: " + ticket + " | Current Pool Size: " + tktQueue.size());
    }

    // Consumer (Customer) buys tickets
    public synchronized Ticket buyTicket() {
        while (tktQueue.isEmpty()) {
            try {
                LogWriter.log("Customer waiting: No tickets available.");
                wait(); // Wait until tickets are added
            } catch (InterruptedException e) {
                LogWriter.log("Customer interrupted while waiting: " + e.getMessage());
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Ticket ticket = tktQueue.poll(); // Retrieve ticket
        notifyAll(); // Notify waiting vendors
        LogWriter.log("Ticket bought: " + ticket + " | Remaining Pool Size: " + tktQueue.size());
        return ticket;
    }

    public synchronized int getTktCount() {
        return tktQueue.size();
    }
}
