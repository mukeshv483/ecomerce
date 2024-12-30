package com.my.akka.ecomerce.ecomerce.actors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String message;

    public Response(String message) {
        this.message = message;
    }
}
