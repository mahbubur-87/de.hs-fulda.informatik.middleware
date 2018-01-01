/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware.taxcalculation;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
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

    @PersistenceContext(unitName = "gsd_middleware_pu")
    private EntityManager em;
    
    @PostConstruct
    public void initBean () {
        
        System.out.println("edu.hsfulda.ai.gsd.middleware.taxcalculation.TaxCalculationBean.initBean()");
    }
    
    @PreDestroy
    public void clearBean () {
        
        System.out.println("edu.hsfulda.ai.gsd.middleware.taxcalculation.TaxCalculationBean.clearBean()");
        
        try {
            em.close();
            em = null;
        } catch (IllegalStateException ex) {
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
    public void save(Tax tax) {
        
        em.persist(tax);
    }

    @Override
    public List<Tax> findAll() {
        
        String jpql = "SELECT t FROM Tax t ORDER BY t.createdDate DESC";
        return em.createQuery(jpql).getResultList();
    }
    
}
