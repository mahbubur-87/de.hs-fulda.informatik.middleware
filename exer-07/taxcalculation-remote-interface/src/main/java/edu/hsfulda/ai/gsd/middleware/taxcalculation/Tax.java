/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware.taxcalculation;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author mahbuburrahman
 */
public class Tax implements Serializable {
    
    private final double amount;
    private final double taxPercentage;
    private final String currency;
    
    private double totalAmountWithTax;
    private LocalDateTime createdDate;
    
    private Tax (double amount, double taxPercentage, String currency) {
        this.amount = amount;
        this.taxPercentage = taxPercentage;
        this.currency = currency;
        this.createdDate = LocalDateTime.now();
    }
    
    public static Tax getInstance (final double amount, final double taxPercentage, final String currency) {
        return new Tax(amount, taxPercentage, currency);
    }

    public Tax setTotalAmountWithTax(double totalAmountWithTax) {
        this.totalAmountWithTax = totalAmountWithTax;
        return this;
    }
    
    public Tax setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public double getTaxPercentage() {
        return taxPercentage;
    }

    public double getTotalAmountWithTax() {
        return totalAmountWithTax;
    }

    public String getCurrency() {
        return currency;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Tax{" + "amount=" + amount + ", taxPercentage=" + taxPercentage + ", currency=" + currency + ", totalAmountWithTax=" + totalAmountWithTax + ", createdDate=" + createdDate + '}';
    }
    
}
