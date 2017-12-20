/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware.taxcalculation;

import java.sql.SQLException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mahbuburrahman
 */
@Remote
public interface TaxCalculationBeanRemote {

    double doCalculation(double amount, double taxPercentage);

    void save(Tax tax) throws SQLException;

    List<Tax> findAll() throws SQLException;
}
