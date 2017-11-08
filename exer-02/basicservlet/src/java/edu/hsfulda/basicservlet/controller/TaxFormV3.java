/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.basicservlet.controller;

import edu.hsfulda.basicservlet.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
public class TaxFormV3 extends TaxFormV2 {
    
    private static int pageHitCounter = 0;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        pageHitCounter++;
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            StringBuffer sb = Utils.readFormFile(TAX_FORM_HTML);
            sb.insert(sb.indexOf("<div id=\"counter\">") + 19, "<h3>Total Page Hit: " + pageHitCounter + " times.</h3>");
            out.print(sb.toString());
        }
    }
}
