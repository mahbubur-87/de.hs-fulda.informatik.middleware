/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.basicservlet.model;

/**
 *
 * @author mahbuburrahman
 */
public class Tax {
    private final float amount;
    private final float taxInPercentage;
    
    private Tax (float amount, float taxInPercentage) {
        this.amount = amount;
        this.taxInPercentage = taxInPercentage;
    }
    
    public static Tax getInstance (final float amount, final float taxInPercentage) {
        return new Tax(amount, taxInPercentage);
    }
    
    public float calculateTax () {
        return amount * taxInPercentage * 0.01f;
    }
    
    public float getTotalAmount () {
        return amount + calculateTax();
    }
}
