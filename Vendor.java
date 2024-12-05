import java.math.BigDecimal;

public class Vendor implements Runnable {
    private final int totalTickets; // Number of tickets to produce
    private final int ticketReleaseRate; // Delay between adding tickets
    private final TicketPool ticketPool; // Shared resource

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Event-" + i, new BigDecimal("100.00")); // Create a new ticket
            ticketPool.addTicket(ticket); // Add ticket to the pool
            LogWriter.log("Vendor " + Thread.currentThread().getName() + " added: " + ticket);
            try {
                Thread.sleep(ticketReleaseRate * 1000L); // Delay between adding tickets
            } catch (InterruptedException e) {
                LogWriter.log("Vendor interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}