package com.my.akka.ecomerce.ecomerce.reservation;
import akka.actor.typed.ActorRef;
import lombok.Builder;

// Base command interface
// Reserve command
@Builder
public class Reserve implements Command {
    public final int quantity;
    public final ActorRef<Object> replyTo;

    public Reserve(int quantity, ActorRef<Object> replyTo) {
        this.quantity = quantity;
        this.replyTo = replyTo;
    }
}



