package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

@Path("/hello")
public class GreetingsResource {
    @Inject
    MeterRegistry registry;

    private int requests = 0;

    @GET
    @Path("/{name}")
    public String sayHello(@PathParam(value = "name") String name) {
        registry.counter("greeting_counter", Tags.of("name", name)).increment();

        // Fot test stdout in Kibana (EFK Stack)
        requests++;
        System.out.println(String.format("Received request %d with parameter: %s", requests, name));

        return String.format("Hello %s!", name);
    }
}
