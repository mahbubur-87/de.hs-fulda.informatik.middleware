/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author mahbuburrahman
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "taxQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class TaxCalculationMDB implements MessageListener {

    @EJB
    private TaxCalculationBeanRemote tcb;
    
    public TaxCalculationMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        try {
            if (!(message instanceof MapMessage)) {
                System.out.println("Invalid message is received of type: " + message.getJMSType());
            }
            
            System.out.println("Message Received ...");
            
            MapMessage mapMsg = (MapMessage) message;
            
            Tax tax = new Tax(mapMsg.getDouble("amount"), mapMsg.getDouble("taxRate"), 
                                    mapMsg.getString("currency"), new Date(mapMsg.getLong("mdbCallDate")));
            
            double totalAmount = tcb.doCalculation(tax.getAmountAsDouble(), tax.getTaxRateAsDouble());
            tax.setTotalAmount(BigDecimal.valueOf(totalAmount));
            
            tcb.save(tax);
            
            System.out.println("Message Saved ...");
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
    
}
