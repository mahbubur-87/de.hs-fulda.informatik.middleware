/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.gsd.middleware.local;

import javax.ejb.Stateless;

/**
 *
 * @author mahbuburrahman
 */
@Stateless(name = "TaxCalculationLocal")
public class TaxCalculationBean implements TaxCalculationBeanLocal {

    @Override
    public double calculateTotalAmountWithTax(double amount, double taxPercentage) {
        
        if (0d >= amount) {
            throw new RuntimeException("Amount can not be zero or less");
        }
        
        if (0d > taxPercentage) {
            throw new RuntimeException("Tax Percentage can not be zero or less");
        }
        
        return (amount + (amount * taxPercentage * 0.01));
    }
}
