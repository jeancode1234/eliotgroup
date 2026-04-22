package dhi.training.dev;

import dhi.training.dev.ticket.CSVTicketRepository;
import dhi.training.dev.ticket.Priority;
import dhi.training.dev.ticket.Ticket;
import dhi.training.dev.ticket.TicketManager;
import dhi.training.dev.ticket.TicketNotFoundException;
import dhi.training.dev.ticket.TicketStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        TicketManager manager = new TicketManager(new CSVTicketRepository());
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        createTicket(manager, scanner);
                        break;
                    case "2":
                        manager.presentAllTickets();
                        break;
                    case "3":
                        showTicketById(manager, scanner);
                        break;
                    case "4":
                        listByPriority(manager, scanner);
                        break;
                    case "5":
                        assignTicket(manager, scanner);
                        break;
                    case "6":
                        changeStatus(manager, scanner);
                        break;
                    case "7":
                        manager.printStatistics();
                        break;
                    case "8":
                        manager.exportTicketsToCsv();
                        System.out.println("Export CSV termine.");
                        break;
                    case "9":
                        importTickets(manager, scanner);
                        break;
                    case "0":
                        running = false;
                        System.out.println("Au revoir.");
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (IllegalArgumentException | TicketNotFoundException | IllegalStateException e) {
                System.out.println("Erreur: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("=== Ticket Console Management ===");
        System.out.println("1. Creer un ticket");
        System.out.println("2. Afficher tous les tickets");
        System.out.println("3. Afficher un ticket par id");
        System.out.println("4. Lister par priorite");
        System.out.println("5. Assigner un ticket");
        System.out.println("6. Changer le statut d'un ticket");
        System.out.println("7. Afficher les statistiques");
        System.out.println("8. Exporter en CSV");
        System.out.println("9. Importer depuis CSV");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    private static void createTicket(TicketManager manager, Scanner scanner) {
        System.out.print("Titre: ");
        String title = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Priority priority = askPriority(scanner);

        System.out.print("Demandeur: ");
        String requester = scanner.nextLine();

        System.out.print("Service: ");
        String service = scanner.nextLine();

        System.out.print("Date incident (yyyy-MM-ddTHH:mm), vide = maintenant: ");
        String dateText = scanner.nextLine().trim();
        LocalDateTime occurredAt = dateText.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(dateText);

        manager.openTicket(title, description, priority, requester, service, occurredAt);
        System.out.println("Ticket cree.");
    }

    private static void showTicketById(TicketManager manager, Scanner scanner) {
        System.out.print("Id ticket: ");
        String id = scanner.nextLine().trim();

        Optional<Ticket> ticket = manager.rechercherTicket(id);
        if (ticket.isPresent()) {
            System.out.println(ticket.get());
        } else {
            System.out.println("Ticket introuvable.");
        }
    }

    private static void listByPriority(TicketManager manager, Scanner scanner) {
        Priority priority = askPriority(scanner);
        Set<Ticket> tickets = manager.findAllByPriority(priority);

        if (tickets.isEmpty()) {
            System.out.println("Aucun ticket pour cette priorite.");
            return;
        }

        tickets.forEach(System.out::println);
    }

    private static void assignTicket(TicketManager manager, Scanner scanner) {
        System.out.print("Id ticket: ");
        String ticketId = scanner.nextLine().trim();

        System.out.print("Technicien: ");
        String technician = scanner.nextLine();

        Ticket ticket = manager.assignTicketToTechnician(ticketId, technician);
        System.out.println("Ticket assigne: " + ticket.getId());
    }

    private static void changeStatus(TicketManager manager, Scanner scanner) {
        System.out.print("Id ticket: ");
        String ticketId = scanner.nextLine().trim();

        TicketStatus newStatus = askStatus(scanner);
        Ticket ticket = manager.changeTicketStatus(ticketId, newStatus);

        System.out.println("Statut mis a jour: " + ticket.getStatus());
    }

    private static void importTickets(TicketManager manager, Scanner scanner) {
        System.out.print("Nom du fichier CSV (ex: tickets.csv): ");
        String fileName = scanner.nextLine().trim();

        manager.importTicketsFromCsv(fileName);
        System.out.println("Import termine.");
    }

    private static Priority askPriority(Scanner scanner) {
        System.out.print("Priorite (LOW, MEDIUM, HIGH, CRITICAL): ");
        String value = scanner.nextLine().trim().toUpperCase();
        return Priority.valueOf(value);
    }

    private static TicketStatus askStatus(Scanner scanner) {
        System.out.print("Statut (OPEN, PENDING, RESOLVED, CLOSED): ");
        String value = scanner.nextLine().trim().toUpperCase();
        return TicketStatus.valueOf(value);
    }
}
