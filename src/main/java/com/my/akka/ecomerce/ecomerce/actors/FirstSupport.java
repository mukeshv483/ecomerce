package com.my.akka.ecomerce.ecomerce.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.logging.Logger;


public class FirstSupport extends AbstractActor {
    public FirstSupport(String message) {
        this.message = message;
    }

    private String message;
    Logger logger = Logger.getLogger(FirstSupport.class.getName());

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, meggase -> {
                    logger.info("message: " + meggase);
                    ActorRef ref=getContext().getSystem().actorOf(Props.create(SecondSupport.class));
                  ref.tell(meggase,ActorRef.noSender());
                }).build();
    }
}