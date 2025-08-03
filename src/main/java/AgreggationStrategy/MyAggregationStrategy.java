package AgreggationStrategy;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.ArrayList;
import java.util.List;

public class MyAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        List<String> list;

        if (oldExchange == null) {
            list = new ArrayList<>();
            String newBody = newExchange.getIn().getBody(String.class);
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(List.class);
            String newBody = newExchange.getIn().getBody(String.class);
            list.add(newBody);
            return oldExchange;
        }
    }
}

