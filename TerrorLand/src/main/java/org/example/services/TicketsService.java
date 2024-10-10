package org.example.services;

import org.example.database.PropertiesDaoMySql;
import org.example.entities.PlayerEntity;
import org.example.entities.TicketEntity;
import org.example.exceptions.MySqlEmptyResultSetException;
import org.example.util.IO;

import java.math.BigDecimal;
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

    public void buyTickets(PlayerEntity player){
        System.out.printf("Each ticket costs %.2f.%n", getPurchasePrice());
        int quantity = IO.readInt("How Many Tickets do you wish to buy?\n>");
        for (int i = 0; i < quantity; i++){
            TicketEntity ticket = new TicketEntity(getPurchasePrice()).createTicket(player);
            player.addTicket(ticket);
        }
    }

    private static BigDecimal getPurchasePrice() {
        return (new PropertiesDaoMySql()).getTicketPrice();
    }

    private static void setPurchasePrice(String price) {
        (new PropertiesDaoMySql()).setTicketPrice(price);
    }





}
