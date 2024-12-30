//package com.my.akka.ecomerce.ecomerce.actors;
//
//
//
//import akka.actor.typed.ActorRef;
//import akka.actor.typed.Behavior;
//import akka.actor.typed.javadsl.AbstractBehavior;
//import akka.actor.typed.javadsl.ActorContext;
//import akka.actor.typed.javadsl.Receive;
//import lombok.Builder;
//
//
//@Builder
//public class ResponseActor extends AbstractBehavior<Response> {
//    private String name;
//
//    public ResponseActor(ActorContext context,String name) {
//        super(context);
//        this.name=name;
//    }
//
//
//
//  @Override
//    public Receive<Response> createReceive() {
//        return this.newReceiveBuilder()
//                .onMessage(Response.class, this::logMessage).build();
//    }
//
//    private Behavior<Response> logMessage(Response response) {
//        System.out.println("message"+response.getMessage());
//        return  this;
//    }
//
//
//    @Builder
//    public static class MessageLengthCounter extends com.my.akka.ecomerce.ecomerce.actors.Response {
//     String message;
//     public ActorRef<Response> replyTo;
//
//        public MessageLengthCounter(String message, String message1) {
//            super(message);
//            this.message = message1;
//        }
//        public int getStrLength(String msg){
//            return  msg.length();
//        }
//    }
//}
