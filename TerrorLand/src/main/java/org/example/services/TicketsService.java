package org.example.services;

import org.example.database.DatabaseFactory;
import org.example.entities.Player;
import org.example.entities.Ticket;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.BuyTicketsEvent;
import org.example.util.IOHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class TicketsService {

    private final DatabaseFactory databaseFactory;

    public TicketsService(DatabaseFactory databaseFactory){
        this.databaseFactory = databaseFactory;
    }

    public void setTicketPrice() {
        BigDecimal previousPrice = getPurchasePrice();
        System.out.printf("Each ticket costs %.2f.%n", previousPrice);
        String price = "";
        do {
            if (!price.isEmpty()) System.out.println("Wrong format, values between 0 and 99 accepted.");
            price = IOHelper.readString("New price: ").replace(',', '.');
        }while(!Pattern.matches("^\\d{1,2}(\\.\\d{1,2})?$", price));
        //REGEX: Number up to 99 with optional 1 or 2 digit decimal
        setPurchasePrice(price);
        BigDecimal newPrice = BigDecimal.valueOf(Double.parseDouble(price));
        if (newPrice.compareTo(previousPrice) < 0){
            String notification = String.format("The price is down to %.2f!! Get your tickets now!!", newPrice);
            new NotificationsService(databaseFactory).notifySubscribers(notification);
        }
    }

    public void buyTicketsDialog(Player player){
        System.out.printf("Each ticket costs %.2f.%n", getPurchasePrice());
        int quantity = IOHelper.readBoundedPositiveInt("How Many Tickets do you wish to buy?\n>", 20);
        createTickets(player, quantity, getPurchasePrice());
        RewardsService.getInstance().launchRewardChain(new Request(player, new BuyTicketsEvent(quantity)));
    }

    public void createTickets(Player player, int quantity, BigDecimal purchasePrice) {
        for (int i = 0; i < quantity; i++){
            Ticket ticket = createTicket(player, getPurchasePrice());
            player.addTicket(ticket);
        }
    }

    private BigDecimal getPurchasePrice() {
        return databaseFactory.createPropertiesDao().getTicketPrice();
    }

    private void setPurchasePrice(String price) {
        databaseFactory.createPropertiesDao().setTicketPrice(price);
    }

    private Ticket createTicket(Player player, BigDecimal price){
        Ticket ticket = new Ticket(price);
        databaseFactory.createTicketDao().saveTicket(ticket, player);
        return ticket;
    }

    private void cash(Ticket ticket){
        databaseFactory.createTicketDao().cashTicket(ticket);
    }

    public BigDecimal getTotalIncome(){
        return databaseFactory.createTicketDao().getTotalIncome();
    }

    public boolean cashTicket(Player player) {
        List<Ticket> tickets = player.getTickets();
        if (tickets.isEmpty()) return false;
        Ticket ticket = tickets.removeFirst();
        cash(ticket);
        return true;
    }
}
