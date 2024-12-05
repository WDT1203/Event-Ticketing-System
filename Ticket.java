import java.math.BigDecimal;

public class Ticket {
    private final int ticketId; // Unique identifier
    private final String eventName; // Event associated with the ticket
    private final BigDecimal ticketPrice; // Price of the ticket

    public Ticket(int ticketId, String eventName, BigDecimal ticketPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return String.format("Ticket{ID=%d, Event='%s', Price=%s}", ticketId, eventName, ticketPrice);
    }
}
