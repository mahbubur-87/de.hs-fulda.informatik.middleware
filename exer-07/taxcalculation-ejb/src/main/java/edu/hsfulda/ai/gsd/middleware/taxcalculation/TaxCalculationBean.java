/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware.taxcalculation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 *
 * @author mahbuburrahman
 */
@Stateless
public class TaxCalculationBean implements TaxCalculationBeanRemote {

    private Connection con;
    
    @PostConstruct
    public void initBean () {
        
        System.out.println("edu.hsfulda.ai.gsd.middleware.taxcalculation.TaxCalculationBean.initBean()");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gsd_middleware", "gsd", "gsd");
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    @PreDestroy
    public void clearBean () {
        
        System.out.println("edu.hsfulda.ai.gsd.middleware.taxcalculation.TaxCalculationBean.clearBean()");
        
        try {
            con.close();
            con = null;
        } catch (SQLException ex) {
        }
    }
    
    @Override
    public double doCalculation(double amount, double taxPercentage) {
        
        if (0d >= amount) {
            throw new RuntimeException("Amount can not be zero or less");
        }
        
        if (0d > taxPercentage) {
            throw new RuntimeException("Tax Percentage can not be zero or less");
        }
        
        return (amount + (amount * taxPercentage * 0.01));
    }

    @Override
    public void save(Tax tax) throws SQLException {
        
        String sql = "INSERT INTO tax(amount, tax_rate, total_amount, currency, created_date) VALUES(?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setDouble(1, tax.getAmount());
        stmt.setDouble(2, tax.getTaxPercentage());
        stmt.setDouble(3, tax.getTotalAmountWithTax());
        stmt.setString(4, tax.getCurrency());
        stmt.setTimestamp(5, Timestamp.valueOf(tax.getCreatedDate()));
        
        stmt.execute();
        stmt.close();
    }

    @Override
    public List<Tax> findAll() throws SQLException {
        String sql = "SELECT * FROM tax t ORDER BY t.created_date DESC";
        
        PreparedStatement stmt = con.prepareStatement(sql);
        
        ResultSet results = stmt.executeQuery();
        
        List<Tax> taxs = new LinkedList<>();
        
        if (null != results) {
            results.beforeFirst();
            
            Tax tax;
            
            while (results.next()) {
                tax = Tax.getInstance(results.getDouble(2), results.getDouble(3), results.getString(5))
                                .setTotalAmountWithTax(results.getDouble(4))
                                .setCreatedDate(results.getTimestamp(6).toLocalDateTime());
                
                taxs.add(tax);
            }
            
            results.close();
        }
        
        stmt.close();
        
        return taxs;
    }
    
}
