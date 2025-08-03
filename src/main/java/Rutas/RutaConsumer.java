package Rutas;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RutaConsumer extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("netty-http")
                .host("0.0.0.0")
                .port(8080)
                .bindingMode(RestBindingMode.off);

        rest("/split")
                .post()
                .consumes("application/json")
                .to("direct:ejemploSplit");

        rest("/aggregation")
                .post()
                .consumes("application/json")
                .to("direct:ejemploAggregate");

        rest("/multicast")
                .post()
                .consumes("application/json")
                .to("direct:ejemploMulticast");

        rest("/filter")
                .post()
                .consumes("application/json")
                .to("direct:ejemploFiltro");

        rest("/choice")
                .post()
                .consumes("application/json")
                .to("direct:ejemploChoice");

        rest("/wiretap")
                .post()
                .consumes("application/json")
                .to("direct:ejemploWireTap");
    }
}
