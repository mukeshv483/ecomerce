package com.my.akka.ecomerce.ecomerce.reservation;

import akka.actor.typed.ActorRef;


public  class Cancel implements Command {
    public final int quantity;
    public final ActorRef<Object>  replyTo;

    public Cancel(int quantity, ActorRef<Object> replyTo) {
        this.quantity = quantity;
        this.replyTo = replyTo;
    }
}