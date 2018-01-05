package edu.hsfulda.ai.gsd.middleware;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.Queue;

/**
 * Enterprise Application Client main class.
 *
 */
public class Main {
    
    @Resource(mappedName = "taxcalculation")
    private ConnectionFactory connection;
    
    @Resource(mappedName = "taxQueue")
    private Queue taxQueue;
    
    private void consumeMessage () {
        
        try (JMSConsumer consumer = connection.createContext().createConsumer(taxQueue)) {
            consumer.setMessageListener(new TaxCalculationListener());
        }
    }
    
    public static void main( String[] args ) {
        
        System.out.println( "Hello World Enterprise Application Client!" );
        new Main().consumeMessage();
    }
}
