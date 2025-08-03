package Rutas;
import AgreggationStrategy.MyAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;

public class RutaInicial extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:ejemploSplit")
                .log("Peticion de entrada: ${body}")
                .unmarshal().json()
                .split().body()
                .log("Body separado: ${body}")
                .end()
                .transform(simple("PETICION RECIBIDA!"))
                .end();


        from("direct:ejemploAggregate")
                .log("Peticion recibida: ${body}")
                .aggregate(header("id"), new MyAggregationStrategy())
                .completionSize(3) // Agrupar 3 mensajes con el mismo ID
                .log("Agregado completo: ${body}")
                .end();

        from("direct:ejemploMulticast")
                .log("Peticion recibida: ${body}")
                .multicast().to("direct:multicastRuta1","direct:multicastRuta2")
                .end();
        from("direct:multicastRuta1")
                .log("Estoy en ruta multicast1")
                .end();
        from("direct:multicastRuta2")
                .log("Estoy en ruta multicast2")
                .end();

        from("direct:ejemploFiltro")
                .log("Peticion recibida: ${body}")
                .convertBodyTo(String.class)
                .filter(simple("${body} contains 'valido'"))
                .log("Body valido: ${body}")
                .end()
                .end();

        from("direct:ejemploChoice")
                .log("Peticion recibida: ${body}")
                .convertBodyTo(String.class)
                .choice().when(simple("${body} contains 'valido'"))
                .log("Condicion correcta")
                .transform(simple("Condicion correcta!"))
                .otherwise()
                .log("Condicion erronea.")
                .transform(simple("Condicion erronea!"))
                .end()
                .end();

        from("direct:ejemploWireTap")
                .log("Peticion recibida: ${body}")
                .wireTap("direct:rutaWireTap")
                .transform(simple("Peticion recibida!"))
                .end();
        from("direct:rutaWireTap")
                .log("Entrando a ruta por debajo.")
                .delay(5000)
                .log("Despues de los 5 segundos.")
                .end();

    }
}

