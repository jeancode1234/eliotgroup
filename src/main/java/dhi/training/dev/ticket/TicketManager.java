package dhi.training.dev.ticket;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TicketManager {

    private final TicketRepository repository;

    public TicketManager(TicketRepository repository) {
        this.repository = repository;
    }

    public void openTicket(String title,
                           String description,
                           Priority priority,
                           String requester,
                           String service,
                           LocalDateTime occurredAt) {

        Ticket newTicket = Ticket.openTicket(title, description, priority, requester, service, occurredAt);
        repository.saveTicket(newTicket);
    }

    public Optional<Ticket> findTicketByTitle(String query) {

        for (Ticket ticket : repository.getTickets()) {
            if (ticket.getTitle().contains(query))
                return Optional.of(ticket);
        }

        return Optional.empty();
    }

    public Set<Ticket> findAllByPriority(Priority priority) {

        Set<Ticket> result = new HashSet<>();
        for (Ticket ticket : repository.getTickets()) {
            if (ticket.getPriority() == priority) result.add(ticket);
        }

        return result;
    }
    public Optional<Ticket> findTicketByStatus(TicketStatus status) {

        for (Ticket ticket : repository.getTickets()) {
            if (ticket.getStatus().equals(status))
                return Optional.of(ticket);
        }

        return Optional.empty();
    }
    public Set<Ticket> findAllByStatus(TicketStatus status) {

        Set<Ticket> result = new HashSet<>();
        for (Ticket ticket : repository.getTickets()) {
            if (ticket.getStatus() == status) result.add(ticket);
        }

        return result;
    }

    public void presentAllTickets() {

        for (Ticket ticket : repository.getTickets()) {
            System.out.println(ticket);
        }
    }

//    public Optional<Ticket> presentTicketById(String id) {
//        for (Ticket ticket : repository.getTickets()) {
//            if (ticket.getId().equals(id)) {
//                System.out.println(ticket);
//                return Optional.of(ticket);
//            }
//        }
//        System.out.println("Ticket n'existe pas ");
//        return Optional.empty();
//    }

    public Optional<Ticket> rechercherTicket(String id) {
            return Optional.of(repository.getTickets()
                    .stream()
                    .filter(ticket -> ticket.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new TicketNotFoundException("Ticket introuvable avec id: " + id)));
        }

    public Ticket assignTicketToTechnician(String ticketId, String technician) {
        Optional<Ticket> ticket = rechercherTicket(ticketId);
        ticket.get().assignTo(technician);
        return ticket.orElse(null);
    }

    public Ticket changeTicketStatus(String ticketId, TicketStatus newStatus) {
        Optional<Ticket> ticket = rechercherTicket(ticketId);
        ticket.get().changeStatus(newStatus);
        return ticket.orElse(null);
    }
}
