/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.basicservlet.service;

import edu.hsfulda.basicservlet.model.Tax;
import edu.hsfulda.basicservlet.util.Utils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mahbuburrahman
 */
public class TaxService {
    
    private final String LOG_FILE = this.getClass().getResource("result.log").getPath();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm");
    
    public static TaxService getInstance () {
        return new TaxService();
    }
    
    public String doTaxCalculation (float amount, float taxInPercentage, String currency) {
        float totalAmount = Tax.getInstance(amount, taxInPercentage)
                               .getTotalAmount();
        
        return "The total amount is " + totalAmount + " " + currency + ".";
    }
    
    public boolean saveResultInLogFile (float amount, float taxInPercentage, String currency) throws IOException {
        String resultToSave = sdf.format(new Date()) + " => " + doTaxCalculation(amount, taxInPercentage, currency);
        Utils.writeToFile(LOG_FILE, resultToSave);
        return true;
    }
    
    public String getResultsFromLogFile () throws IOException{
        return Utils.readFormFile(LOG_FILE)
                    .toString();
    }
}
