/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mahbuburrahman
 */
@Stateless
public class TaxCalculationBean implements TaxCalculationBeanRemote {

    private static final String CSV_FILE = "/tmp/tax.csv";
    
    @PersistenceContext(unitName = "gsd_middleware_pu")
    private EntityManager em;
    
    @PreDestroy
    public void clearBean () {
        
        System.out.println("Clearing Bean ...");
        em = null;
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
    public void save(Tax tax) {
        
        // save into db
        System.out.println("Save into DB ...");
        em.persist(tax);
        
        // save into csv
        System.out.println("Save into CSV ...");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(CSV_FILE), true))) {
            bw.append(tax.toStringAsCommaSeperated());
            bw.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Tax> findAll() {
        
        String jpql = "SELECT t FROM Tax t ORDER BY t.createdDate DESC";
        return em.createQuery(jpql).getResultList();
    }

    @Override
    public List<Tax> readFile() {
        
        File file = new File(CSV_FILE);
        
        if (!file.exists()) {
            return Collections.EMPTY_LIST;
        }
        
        List<Tax> taxs = new LinkedList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String strTaxRecord = br.readLine();
            String[] recordParts = strTaxRecord.split(",");
            
            Tax tax = new Tax(Double.parseDouble(recordParts[1]), Double.parseDouble(recordParts[2]), recordParts[4], Tax.sdf.parse(recordParts[0]));
            tax.setTotalAmount(BigDecimal.valueOf(Double.parseDouble(recordParts[3])));
            
            taxs.add(tax);
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        
        return taxs;
    }
}
