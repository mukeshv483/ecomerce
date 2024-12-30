package com.my.akka.ecomerce.ecomerce.prime;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

public class PrimeActor extends AbstractBehavior<PrimeActor.Problem> {


    public PrimeActor(ActorContext<Problem> context) {
        super(context);
    }

    @Override
    public Receive<Problem> createReceive() {
        return null;
    }

    interface Problem {
    }

}
