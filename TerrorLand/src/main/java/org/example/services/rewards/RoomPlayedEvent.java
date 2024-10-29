package org.example.services.rewards;

import org.example.entities.Attempt;

public class RoomPlayedEvent implements Event{
    Attempt attempt;

    public RoomPlayedEvent(Attempt attempt) {
        this.attempt = attempt;
    }

    public Attempt getAttempt() {
        return attempt;
    }
}
