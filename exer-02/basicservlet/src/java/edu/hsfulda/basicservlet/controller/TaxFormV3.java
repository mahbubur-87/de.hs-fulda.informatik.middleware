/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.basicservlet.controller;

import edu.hsfulda.basicservlet.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
public class TaxFormV3 extends TaxFormV2 {
    
    private final String COOKIE_NAME = "usr_hit_count";
    private static int accessCounter = 0;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest();

        int usrHitCount = 1;
        
        if (null != request.getCookies()) {
            List<Cookie> cookies =  Arrays.asList(request.getCookies());
            
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName())) {
                    usrHitCount = Integer.parseInt(c.getValue()) + 1;
                    break;
                }
            }
        }
        
        response.addCookie(new Cookie(COOKIE_NAME, Integer.toString(usrHitCount)));
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            StringBuffer sb = Utils.readFormFile(TAX_FORM_HTML);
            sb.insert(sb.indexOf("<div id=\"counter\">") + 19, 
                    "<h3>Total User Hit: " + usrHitCount + " times</h3>");
            out.print(sb.toString());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        processRequest();
        super.doPost(request, response);
    }

    private void processRequest() {
        
        accessCounter++;
        System.out.println("Process Request access count: " + accessCounter + " times");
    }
}
