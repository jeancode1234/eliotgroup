package dhi.training.dev;

import dhi.training.dev.ticket.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        final TicketManager manager = new TicketManager(new CSVTicketRepository());

        manager.openTicket(
                "Imprimante en panne",
                "L'imprimante n'imprime plus",
                Priority.HIGH,
                "M Conclusion",
                "Service RH",
                LocalDateTime.of(2026, 4, 1, 12, 30)
        );

        manager.openTicket(
                "Scanner en panne",
                "L'imprimante n'imprime plus",
                Priority.CRITICAL,
                "M Introduction",
                "Direction Général",
                LocalDateTime.of(2026, 4, 1, 12, 30)
        );

        manager.presentAllTickets();

//        Optional<Ticket> optionalTicket = manager.findTicketByTitle("Erreur");
//        if (optionalTicket.isPresent()) {
//            Ticket ticket = optionalTicket.get();
//
//            System.out.println("Ticket trouvé !!!");
//
//            System.out.println(ticket);
//            System.out.println(ticket.getDescription());
//        }
//        else {
//            System.out.println("Ticket n'existe pas");
//        }
//
      Optional<Ticket> newoptionalTicket = manager.presentTicketById("102fc5f5-865d-4562-8045-2e5908a12af6");
      if (newoptionalTicket.isPresent()){
          Ticket tick = newoptionalTicket.get();
//
            System.out.println("Ticket trouvé !!!");

            System.out.println(tick);
            System.out.println(tick.getDescription());
      }

    }
}
