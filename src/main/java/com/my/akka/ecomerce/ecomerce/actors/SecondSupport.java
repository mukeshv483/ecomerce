package com.my.akka.ecomerce.ecomerce.actors;

import akka.actor.AbstractActor;

import java.util.logging.Logger;

public class SecondSupport extends AbstractActor {
    Logger logger=Logger.getLogger(SecondSupport.class.getName());
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class,msg->{
            logger.warning("second actor msg length: "+msg.length());
        }).build();
    }
}
