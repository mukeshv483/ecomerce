package com.my.akka.ecomerce.ecomerce;


import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import com.my.akka.ecomerce.ecomerce.command.Puzzle;
import com.my.akka.ecomerce.ecomerce.command.Quiz;
import com.my.akka.ecomerce.ecomerce.reservation.Cancel;
import com.my.akka.ecomerce.ecomerce.reservation.Command;
import com.my.akka.ecomerce.ecomerce.reservation.Reservation;
import com.my.akka.ecomerce.ecomerce.reservation.Reserve;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class SolverMain {
    public static void main(String[] args) {
        // Create the actor system
        ActorSystem<Command> system = ActorSystem.create(Solver.create(),"name");
        // Create an actor ref for the Reservation
        ActorRef<Command> solverActor = (ActorRef<Command>) system;

        // Reserve a quantity
        CompletionStage<String> reserveFuture = AskPattern.ask(
                solverActor,
                replyTo->new Puzzle("Sudoku Puzzle ",replyTo),
                Duration.ofSeconds(30),
                system.scheduler()
        ).thenApply(response -> (String) response);

        // Handle the reservation response
        reserveFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to get response: " + ex);
            } else {
                System.out.println("Puzzle result: " + result);
            }

            // Cancel a quantity
            CompletionStage<String> cancelFuture = AskPattern.ask(
                    solverActor,
                    replyTo -> new Quiz("Multiple choice quiz ",  replyTo),
                    Duration.ofSeconds(5),
                    system.scheduler()
            ).thenApply(response -> (String) response);

            // Handle the cancellation response
            cancelFuture.whenComplete((cancelResult, cancelEx) -> {
                if (cancelEx != null) {
                    System.err.println("Failed to get response: " + cancelEx);
                } else {
                    System.out.println("quiz result: " + cancelResult);
                }
                system.terminate();
            });
       });
    }
}
