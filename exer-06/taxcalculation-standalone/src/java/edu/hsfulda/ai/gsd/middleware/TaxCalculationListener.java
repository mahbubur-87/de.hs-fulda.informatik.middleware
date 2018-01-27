/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author mahbuburrahman
 */
public class TaxCalculationListener implements MessageListener {

    //@EJB
    private TaxCalculationBeanRemote tcb;

    public TaxCalculationListener setTcb(TaxCalculationBeanRemote tcb) {
        this.tcb = tcb;
        return this;
    }
   
    @Override
    public void onMessage(Message message) {
        
        try {
            if (!(message instanceof MapMessage)) {
                System.out.println("Invalid message is received of type: " + message.getJMSType());
                return;
            }
            
            System.out.println("Message Received ...");
            
            MapMessage mapMsg = (MapMessage) message;
            
            Tax tax = new Tax(mapMsg.getDouble("amount"), mapMsg.getDouble("taxRate"), 
                                    mapMsg.getString("currency"), new Date(mapMsg.getLong("mdbCallDate")));
            
            double totalAmount = tcb.doCalculation(tax.getAmountAsDouble(), tax.getTaxRateAsDouble());
            tax.setTotalAmount(BigDecimal.valueOf(totalAmount));
            
//            System.out.println("Latest Record: " + tax.toStringAsCommaSeperated());
            
            tcb.save(tax);
            
            System.out.println("Tax List from CSV");
            System.out.println(tax.toStringAsCommaSeperated());
            tcb.readFile().forEach(t -> System.out.println(t.toStringAsCommaSeperated()));
            
            System.out.println("Tax List from DB");
            tcb.findAll().forEach(t -> System.out.println(t.toStringAsCommaSeperated()));
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
    
}
