/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.basicservlet.controller;

import edu.hsfulda.basicservlet.model.Tax;
import edu.hsfulda.basicservlet.service.TaxService;
import edu.hsfulda.basicservlet.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
public class TaxFormV2 extends HttpServlet {

    protected final String TAX_FORM_HTML = this.getClass().getResource("tax-form_1.html").getPath();
    
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
        
        float amount = Float.parseFloat(request.getParameter("amount"));
        float taxInPercentage = Float.parseFloat(request.getParameter("tax"));
        String currency = request.getParameter("currency");
       
        TaxService.getInstance()
                  .saveResultInLogFile(amount, taxInPercentage, currency);
        
        response.setContentType("text/html;charset=UTF-8");
       
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            StringBuffer sb = Utils.readFormFile(TAX_FORM_HTML);
            sb.insert(sb.indexOf("<hr>") + 5, "<p>The total amount is saved into log file.</p>");
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
                + "Then add tax value with amount and save total amount as final result in log file";
    }// </editor-fold>

}
