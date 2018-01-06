package edu.hsfulda.ai.gsd.middleware;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.Message;
import javax.jms.Queue;

/**
 * Enterprise Application Client main class.
 *
 */
public class Main {
    
    @Resource(mappedName = "taxcalculation")
    private static ConnectionFactory connection;
    
    @Resource(mappedName = "taxQueue")
    private static Queue taxQueue;
    
    private void consumeMessage () {
        
        try (JMSConsumer consumer = connection.createContext().createConsumer(taxQueue)) {
            //consumer.setMessageListener(new TaxCalculationListener());
            System.out.println("Waiting for Message ...");
            Message message = consumer.receive(10);
            System.out.println("Message Received ...");
            new TaxCalculationListener().onMessage(message);
        }
    }
    
    public static void main( String[] args ) {
        
        System.out.println( "Hello World Enterprise Application Client!" );
        new Main().consumeMessage();
    }
}
