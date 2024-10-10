package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.entities.Player;
import org.example.entities.Ticket;
import org.example.util.IO;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class TicketsService {

    public void setTicketPrice() {
        System.out.printf("Each ticket costs %.2f.%n", getPurchasePrice());
        String price = "";
        do {
            if (!price.isEmpty()) System.out.println("Wrong format, values between 0 and 99 accepted.");
            price = IO.readString("New price: ").replace(',', '.');
        }while(!Pattern.matches("^\\d{1,2}(\\.\\d{1,2})?$", price));
        //REGEX: Number up to 99 with optional 1 or 2 digit decimal
        setPurchasePrice(price);
    }

    public void buyTickets(Player player){
        System.out.printf("Each ticket costs %.2f.%n", getPurchasePrice());
        int quantity = IO.readInt("How Many Tickets do you wish to buy?\n>");
        for (int i = 0; i < quantity; i++){
            Ticket ticket = createTicket(player, getPurchasePrice());
            player.addTicket(ticket);
        }
    }

    private static BigDecimal getPurchasePrice() {
        return DatabaseFactory.get().createPropertiesDao().getTicketPrice();
    }

    private static void setPurchasePrice(String price) {
        DatabaseFactory.get().createPropertiesDao().setTicketPrice(price);
    }

    public Ticket createTicket(Player player, BigDecimal price){
        Ticket ticket = new Ticket(price);
        DatabaseFactory.get().createTicketDao().saveTicket(ticket, player);
        return ticket;
    }

    public void cash(Ticket ticket){
        DatabaseFactory.get().createTicketDao().cashTicket(ticket);
    }

    public BigDecimal getTotalIncome(){
        return DatabaseFactory.get().createTicketDao().getTotalIncome();
    }

    public boolean cashTicket(Player player) {
        List<Ticket> tickets = player.getTickets();
        if (tickets.isEmpty()) return false;
        Ticket ticket = tickets.removeFirst();
        cash(ticket);
        return true;
    }
}
