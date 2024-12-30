package com.my.akka.ecomerce.ecomerce;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.my.akka.ecomerce.ecomerce.command.Puzzle;
import com.my.akka.ecomerce.ecomerce.command.Quiz;
import com.my.akka.ecomerce.ecomerce.reservation.Command;

public class Solver extends AbstractBehavior<Command> {
    public Solver(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(Solver::new);

    }


    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder().
                onMessage(Puzzle.class, puzzle -> {
                    System.out.println("puzzle: " + puzzle);
                    String res = puzzle.getMessage() + "is solved";
                    puzzle.getReplyTo().tell(res);
                    return Behaviors.same();
                })
                .onMessage(Quiz.class, quiz -> {
                    System.out.println("quiz: " + quiz);
                    String res = quiz.getMessage() + "is solved";
                    quiz.getReplyTo().tell(res);
                    ActorRef child = getContext().spawn(Solver.create(), "Solve-child");
                    child.tell(Puzzle.builder().message("child puzzle").replyTo(quiz.getReplyTo()).build());
                    return Behaviors.same();
                })
                .build();

    }
}
