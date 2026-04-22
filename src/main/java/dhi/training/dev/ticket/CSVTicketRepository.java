package dhi.training.dev.ticket;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class CSVTicketRepository implements TicketRepository {

    private static final String FILE_NAME = "tickets.csv";

    public Set<Ticket> getTickets() {

        Set<Ticket> tickets = new HashSet<>();

        try {

            // Création d'un fileReader pour lire le fichier
            FileReader fileReader = new FileReader(FILE_NAME);

            // Création d'un bufferedReader qui utilise le fileReader
            BufferedReader reader = new BufferedReader(fileReader);

            String line = reader.readLine();
            int lineNumber = 0;

            while (line != null) {

                if (lineNumber != 0) {
                    Ticket ticket = parseTicket(line);
                    tickets.add(ticket);
                }

                line = reader.readLine();
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    private static Ticket parseTicket(String line) {
        String[] strings = line.split(",");

        return new Ticket(
                strings[0],
                strings[1],
                strings[2],
                Priority.valueOf(strings[3]),
                strings[4],
                strings[5],
                parseDateTime(strings, 6),
                TicketStatus.valueOf(strings[8]),
                parseDateTime(strings, 7),
                parseDateTime(strings, 9),
                parseDateTime(strings, 10),
                strings[11],
                parseDateTime(strings, 12),
                parseDateTime(strings, 13),
                parseDateTime(strings, 14)
        );
    }

    private static LocalDateTime parseDateTime(String[] strings, int pos) {
        return Optional.ofNullable(strings[pos]).filter(s -> !s.isBlank()).map(LocalDateTime::parse).orElse(null);
    }

    public void saveTicket(Ticket ticket) {

        try {
            // Création d'un fileWriter pour écrire dans un fichier
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);

            // Création d'un bufferedWriter qui utilise le fileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);
            // Retour à la ligne
            writer.newLine();

            // ajout d'un texte à notre fichier
            writer.write(writeTicket(ticket));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String writeTicket(Ticket ticket) {

        String[] strings = new String[15];

        strings[0] = ticket.getId();
        strings[1] = ticket.getTitle();
        strings[2] = ticket.getDescription();
        strings[3] = ticket.getPriority().name();
        strings[4] = ticket.getRequester();
        strings[5] = ticket.getService();
        strings[6] = ticket.getOccurredAt().toString();
        strings[7] = ticket.getOpenedAt().toString();
        strings[8] = ticket.getStatus().name();
        strings[9] = ticket.getClosedAt() == null ? "" : ticket.getClosedAt().toString();
        strings[10] = ticket.getResolvedAt() == null ? "" : ticket.getResolvedAt().toString();
        strings[11] = ticket.getAssignedTo() == null ? "" : ticket.getAssignedTo();
        strings[12] = ticket.getAssignedAt() == null ? "" : ticket.getAssignedAt().toString();
        strings[13] = ticket.getCreatedAt().toString();
        strings[14] = ticket.getUpdatedAt().toString();

        return String.join(",", strings);
    }

    public void deleteTicket(Ticket ticket) {

    }

    public void saveTickets(Collection<Ticket> tickets) {

    }

}
