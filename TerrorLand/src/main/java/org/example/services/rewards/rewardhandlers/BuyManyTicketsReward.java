package org.example.services.rewards.rewardhandlers;

import org.example.services.NotificationsService;
import org.example.services.TicketsService;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.BuyTicketsEvent;

import java.math.BigDecimal;

public class BuyManyTicketsReward extends PatternReward {


    public BuyManyTicketsReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof BuyTicketsEvent) {
            int quantity = ((BuyTicketsEvent) request.getEvent()).getQuantity();
            if (quantity > 5) {
                super.grantReward(request.getPlayer());
                new TicketsService(super.getDatabaseFactory()).createTickets(request.getPlayer(), 5, new BigDecimal(0));
                new NotificationsService(super.getDatabaseFactory()).notifyAndUpdatePlayer(request.getPlayer(),
                        "Here's your prize!\nYou have received 5 free tickets for winning the 'Investor' Badge.");
            }
        }
    }
}
