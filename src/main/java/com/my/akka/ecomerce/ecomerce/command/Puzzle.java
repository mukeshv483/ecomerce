package com.my.akka.ecomerce.ecomerce.command;


import akka.actor.typed.ActorRef;
import com.my.akka.ecomerce.ecomerce.reservation.Command;
import lombok.Builder;
import lombok.Data;



@Builder
@Data
public class Puzzle implements Command {
    private final String message;
    private final ActorRef<Object> replyTo;
    public Puzzle(String message, ActorRef<Object> replyTo) {
        this.message = message;
        this.replyTo = replyTo;
    }



}
