public class Customer implements Runnable {
    private final TicketPool ticketPool;  // Shared resource
    private final int customerRetrievalRate; // Delay in ticket retrieval
    private final int quantity;  // Number of tickets to retrieve

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = ticketPool.buyTicket(); // Retrieve ticket from the pool
            if (ticket != null) {
                LogWriter.log("Customer " + Thread.currentThread().getName() + " bought: " + ticket);
            } else {
                LogWriter.log("Customer " + Thread.currentThread().getName() + " could not buy a ticket (none available).");
            }
            try {
                Thread.sleep(customerRetrievalRate * 1000L); // Delay between retrievals
            } catch (InterruptedException e) {
                LogWriter.log("Customer interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}