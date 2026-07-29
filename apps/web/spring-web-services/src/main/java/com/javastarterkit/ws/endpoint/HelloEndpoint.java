// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ws.endpoint;

import com.javastarterkit.ws.generated.GetHelloRequest;
import com.javastarterkit.ws.generated.GetHelloResponse;
import com.javastarterkit.ws.generated.ObjectFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HelloEndpoint {

    private static final String NAMESPACE_URI = "http://javastarterkit.com/ws/sample";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHelloRequest")
    @ResponsePayload
    public GetHelloResponse getHello(@RequestPayload GetHelloRequest request) {
        ObjectFactory factory = new ObjectFactory();
        GetHelloResponse response = factory.createGetHelloResponse();
        response.setMessage("Hello, " + request.getName() + "!");
        return response;
    }
}
