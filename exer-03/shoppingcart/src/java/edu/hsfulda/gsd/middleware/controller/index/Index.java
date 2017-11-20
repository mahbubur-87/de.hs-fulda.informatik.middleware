/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.gsd.middleware.controller.index;

import edu.hsfulda.gsd.middleware.controller.shoppingcart.ShoppingCart;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
public class Index extends HttpServlet {

    private final String HTML_PAGE_NAME = "index.html";
    private final String COOKIE_NAME = "shopping_cart_" + (Math.random() % 100);
    
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
        
        request.getSession().setAttribute("COOKIE_NAME", COOKIE_NAME);
        
        File htmlFile = null;
        try {
            htmlFile = new File(this.getClass().getResource(HTML_PAGE_NAME).toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(ShoppingCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BufferedReader br = new BufferedReader(new FileReader(htmlFile));
        StringBuffer sb = new StringBuffer();
        
        while (br.ready()) {
            sb.append(br.readLine());
        }
        
        br.close();
        
        String indexHtml = sb.toString();
        
        response.setContentType("text/html;charset=UTF-8");
            
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(indexHtml);
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
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
