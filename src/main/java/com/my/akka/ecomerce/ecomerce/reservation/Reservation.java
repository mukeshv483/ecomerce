package com.my.akka.ecomerce.ecomerce.reservation;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Reservation extends AbstractBehavior<Command> {

    private static final int INITIAL_STOCK = 100;
    private  int availableQuantity = INITIAL_STOCK;

    public Reservation(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(context -> new Reservation(context).createBehavior());
    }

    private Behavior<Command> createBehavior() {
        return Behaviors.receive((context, message) -> {
            if (message instanceof Reserve) {
                return onReserve((Reserve) message);
            } else if (message instanceof Cancel) {
                return onCancel((Cancel) message);
            } else {
                return Behaviors.unhandled();
            }
        });
    }

    private Behavior<Command> onReserve(Reserve reserve) {
        boolean success = false;
        String messageText;
        if (reserve.quantity <= availableQuantity) {
            availableQuantity -= reserve.quantity;
            success = true;
            messageText = "Reservation successful!";
            System.out.println(messageText);
        } else {
            messageText = "Not enough stock available.";
        }
        reserve.replyTo.tell(new ReservationResult(success, messageText));
        return Behaviors.same();
    }

    private Behavior<Command> onCancel(Cancel cancel) {
        boolean success = false;
        String messageText;
        if (cancel.quantity > 0) {
            availableQuantity += cancel.quantity;
            success = true;
            messageText = "Cancellation successful!";
        } else {
            messageText = "Invalid cancellation quantity.";
        }
        cancel.replyTo.tell(new ReservationResult(success, messageText));
        return Behaviors.same();
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(Reserve.class,reserve->{
                   return onReserve(reserve);
                })
                .onMessage(Cancel.class,cancel->{
                    return onCancel(cancel);
                }).build();
    }
    static public class ReservationResult {
        public final boolean success;
        public final String message;

        public ReservationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
}
}