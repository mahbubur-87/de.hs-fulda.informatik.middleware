/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.taxcalculation.controller;

import edu.hsfulda.ai.gsd.middleware.remote.TaxCalculation;
import edu.hsfulda.gsd.middleware.TaxCalculationBean;
import edu.hsfulda.gsd.middleware.local.TaxCalculationBeanLocal;
import edu.hsfulda.taxcalculation.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */

@WebServlet(urlPatterns = {"/"})
public class TaxForm extends HttpServlet {

    private final String TAX_FORM_HTML = this.getClass().getResource("tax-form.html").getPath();

    @EJB
    private TaxCalculationBean tcb; 
    
    @EJB(beanName = "TaxCalculationLocal")
    private TaxCalculationBeanLocal tcbLocal;
    
    //@EJB(lookup = "java:app/taxcalculation-ejb/TaxCalculationRemote") //!edu.hsfulda.ai.gsd.middleware.remote.TaxCalculation
    @EJB(beanName = "TaxCalculationRemote")
    private TaxCalculation tcbRemote;

    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
        double amount = Double.parseDouble(request.getParameter("amount"));
        double taxInPercentage = Double.parseDouble(request.getParameter("tax"));
        String currency = request.getParameter("currency");
        
        StringBuffer resultText = new StringBuffer();
        
        try {
            double taFrmDirBean = tcb.calculateTotalAmountWithTax(amount, taxInPercentage);
            double taFrmLocIntf = tcbLocal.calculateTotalAmountWithTax(amount, taxInPercentage);
            double taFrmRemIntf = tcbRemote.calculateTotalAmountWithTax(amount, taxInPercentage);
            
            resultText.append("Direct Bean: The total amount is ").append(taFrmDirBean).append(" ").append(currency).append(".<br>");
            resultText.append("Local Interface: The total amount is ").append(taFrmLocIntf).append(" ").append(currency).append(".<br>");
            resultText.append("Remote Interface: The total amount is ").append(taFrmRemIntf).append(" ").append(currency).append(".");
        } catch (RuntimeException re) {
            resultText.append("HTTP 505: Internal Server Error");
        }    
        
        response.setContentType("text/html;charset=UTF-8");
       
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            StringBuffer sb = Utils.readFormFile(TAX_FORM_HTML);
            sb.insert(sb.indexOf("<hr>") + 5, "<p>").append(resultText).append("</p>");
            out.print(sb.toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.print(Utils.readFormFile(TAX_FORM_HTML).toString());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Tax Form - calculates tax value from amount and tax percentage. "
                + "Then add tax value with amount and displays total amount as final result";
    }// </editor-fold>

}
