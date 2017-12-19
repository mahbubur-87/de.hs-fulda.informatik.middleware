/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.gsd.middleware;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 *
 * @author mahbuburrahman
 */
public class TaxCalculationTest {
    
    @Test
    public void whenTaxPercentageIsZeroThenTotalAmountShouldBeSameAsAmount () {
        
        // arrange
        double amount = 1000;
        double taxInPercentage = 0d;
        
        TaxCalculationBean tcb = new TaxCalculationBean();
        
        // act
        double totalAmount = tcb.calculateTotalAmountWithTax(amount, taxInPercentage);
        
        // assert
        assertThat("total amount is not same as amount", totalAmount, equalTo(amount));
    }
    
    @Test(expected = RuntimeException.class)
    public void whenAmountIsZeroThenThrowRuntimeException () {
        
        // arrange
        double amount = 0d;
        double taxInPercentage = 5d;
        
        TaxCalculationBean tcb = new TaxCalculationBean();
        
        // act
        double totalAmount = tcb.calculateTotalAmountWithTax(amount, taxInPercentage);
        
        // assert
    }
    
    @Test(expected = RuntimeException.class)
    public void whenAmountIsNegativeThenThrowRuntimeException () {
        
        // arrange
        double amount = -1d;
        double taxInPercentage = 5d;
        
        TaxCalculationBean tcb = new TaxCalculationBean();
        
        // act
        double totalAmount = tcb.calculateTotalAmountWithTax(amount, taxInPercentage);
        
        // assert
    }
    
    @Test(expected = RuntimeException.class)
    public void whenTaxPercentageIsNegativeThenThrowRuntimeException () {
        
        // arrange
        double amount = 1000;
        double taxInPercentage = -1d;
        
        TaxCalculationBean tcb = new TaxCalculationBean();
        
        // act
        double totalAmount = tcb.calculateTotalAmountWithTax(amount, taxInPercentage);
        
        // assert
    }
    
    @Test
    public void whenAmountAndTaxPercentageIsGreaterThanZeroThenTotalAmountShouldBeGreaterThanAmount () {
        
        // arrange
        double amount = 1000;
        double taxInPercentage = 10d;
        
        TaxCalculationBean tcb = new TaxCalculationBean();
        
        // act
        double totalAmount = tcb.calculateTotalAmountWithTax(amount, taxInPercentage);
        
        // assert
        assertTrue("total amount is same as amount", totalAmount > amount);
    }
}
