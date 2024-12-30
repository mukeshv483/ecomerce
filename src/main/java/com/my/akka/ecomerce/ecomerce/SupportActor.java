package com.my.akka.ecomerce.ecomerce;




import akka.actor.typed.ActorRef;


import akka.actor.typed.ActorSystem;
import akka.actor.typed.Props;
import akka.actor.typed.javadsl.AskPattern;
import akka.actor.typed.javadsl.Behaviors;
import akka.util.Timeout;
import com.my.akka.ecomerce.ecomerce.reservation.*;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class SupportActor {
    public static void main(String[] args) {
        // Create the actor system
        ActorSystem<Command> system = ActorSystem.create(Reservation.create(),"name");
        // Create an actor ref for the Reservation
        ActorRef<Command> reservationActor = (ActorRef<Command>) system;

        // Reserve a quantity
        CompletionStage<Reservation.ReservationResult> reserveFuture = AskPattern.ask(
                reservationActor,
                replyTo->new Reserve(7,replyTo),
                Duration.ofSeconds(30),
                system.scheduler()
        ).thenApply(response -> (Reservation.ReservationResult) response);

        // Handle the reservation response
        reserveFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to get response: " + ex);
            } else {
                System.out.println("Reservation result: " + result.message);
            }

            // Cancel a quantity
            CompletionStage<Reservation.ReservationResult> cancelFuture = AskPattern.ask(
                    reservationActor,
                    replyTo -> new Cancel(10,  replyTo),
                    Duration.ofSeconds(5),
                    system.scheduler()
            ).thenApply(response -> (Reservation.ReservationResult) response);

            // Handle the cancellation response
            cancelFuture.whenComplete((cancelResult, cancelEx) -> {
                if (cancelEx != null) {
                    System.err.println("Failed to get response: " + cancelEx);
                } else {
                    System.out.println("Cancellation result: " + cancelResult.message);
                }
                system.terminate();
            });
       });
    }
}
