package dhi.training.dev.ticket;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TicketManagerTest {


    @Test
    public void openValidTicket() {

        TicketManager ticketManager = new TicketManager(new InMemoryTicketRepository());

        ticketManager.openTicket(
                "Imprimante en panne",
                "L'imprimante n'imprime plus",
                Priority.HIGH,
                "M Conclusion",
                "Service RH",
                LocalDateTime.of(2026, 4, 1, 12, 30)
        );

        Ticket ticket = ticketManager.findTicketByTitle("Imprimante en panne")
                .orElse(null);

        assertNotNull(ticket);
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
        assertEquals(Priority.HIGH, ticket.getPriority());

    }


    @Test()
    public void openInvalidTicketOccurredAt() {

        TicketManager ticketManager = new TicketManager(new InMemoryTicketRepository());

        assertThrows(
                IllegalArgumentException.class,
                () -> ticketManager.openTicket(
                        "Imprimante en panne",
                        "L'imprimante n'imprime plus",
                        Priority.HIGH,
                        "M Conclusion",
                        "Service RH",
                        LocalDateTime.now().plusDays(1)
                ));


    }


        }