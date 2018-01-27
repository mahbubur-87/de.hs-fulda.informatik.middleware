package edu.hsfulda.ai.gsd.middleware;

import javax.annotation.Resource;
import javax.ejb.EJB;
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
    
    @EJB
    private static TaxCalculationBeanRemote tcb;
    
    private void consumeMessage () {
        
        try (JMSConsumer consumer = connection.createContext().createConsumer(taxQueue)) {
            System.out.println("Waiting for Message ...");
            //consumer.setMessageListener(new TaxCalculationListener());
            Message message = consumer.receive();
            new TaxCalculationListener().setTcb(tcb).onMessage(message);
        }
    }
    
    public static void main( String[] args ) {
        
        System.out.println( "Hello World Enterprise Application Client!" );
        new Main().consumeMessage();
    }
}
