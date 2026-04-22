package dhi.training.dev.ticket;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Ticket {

    private final String id;
    private final String title;
    private final String description;

    private final Priority priority;
    private final String requester; // demandeur
    private final String service;
    private final LocalDateTime occurredAt; // date de l'incident
    private final LocalDateTime openedAt;

    private TicketStatus status;
    private LocalDateTime closedAt;
    private LocalDateTime resolvedAt;

    private String assignedTo;
    private LocalDateTime assignedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Ticket(String id, String title, String description, Priority priority, String requester, String service, LocalDateTime occurredAt, TicketStatus status, LocalDateTime openedAt, LocalDateTime closedAt, LocalDateTime resolvedAt, String assignedTo, LocalDateTime assignedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.requester = requester;
        this.service = service;
        this.occurredAt = occurredAt;
        this.status = status;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.resolvedAt = resolvedAt;
        this.assignedTo = assignedTo;
        this.assignedAt = assignedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static Ticket openTicket(String title,
                                    String description,
                                    Priority priority,
                                    String requester,
                                    String service,
                                    LocalDateTime occurredAt) {

        if (occurredAt == null) {
            throw new IllegalArgumentException("The given occurredAt is null");
        }

        if (occurredAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("The given occurredAt is after the given date");
        }

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("The given title is null or empty");
        }

        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("The given description is null or empty");
        }

        if (requester == null || requester.trim().isEmpty()) {
            throw new IllegalArgumentException("The given requester is null or empty");
        }

        if (service == null || service.trim().isEmpty()) {
            throw new IllegalArgumentException("The given service is null or empty");
        }

        return new Ticket(
                UUID.randomUUID().toString(),
                title,
                description,
                priority == null ? Priority.LOW : priority,
                requester,
                service,
                occurredAt,
                TicketStatus.OPEN,
                LocalDateTime.now(),
                null,
                null,
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}
