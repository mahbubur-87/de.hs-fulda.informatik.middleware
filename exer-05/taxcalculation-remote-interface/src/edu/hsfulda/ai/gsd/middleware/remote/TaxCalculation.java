/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware.remote;

import javax.ejb.Remote;

/**
 *
 * @author mahbuburrahman
 */
@Remote
public interface TaxCalculation {
    
    public double calculateTotalAmountWithTax(double amount, double taxPercentage);
}
