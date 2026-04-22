package dhi.training.dev.ticket;

import java.util.Collection;
import java.util.Set;

public class ExcelTicketRepository implements TicketRepository {


    @Override
    public Set<Ticket> getTickets() {
        return Set.of();
    }

    @Override
    public void saveTicket(Ticket ticket) {

    }

    @Override
    public void deleteTicket(Ticket ticket) {

    }

    @Override
    public void saveTickets(Collection<Ticket> tickets) {

    }
}
