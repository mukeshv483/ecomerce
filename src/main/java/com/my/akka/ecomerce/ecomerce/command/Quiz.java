package com.my.akka.ecomerce.ecomerce.command;


import akka.actor.typed.ActorRef;
import com.my.akka.ecomerce.ecomerce.reservation.Command;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class Quiz implements Command {
    public Quiz(String message, ActorRef<Object> replyTo) {
        this.message = message;
        this.replyTo = replyTo;
    }

    private final  String message;
    private final ActorRef<Object> replyTo;

}
