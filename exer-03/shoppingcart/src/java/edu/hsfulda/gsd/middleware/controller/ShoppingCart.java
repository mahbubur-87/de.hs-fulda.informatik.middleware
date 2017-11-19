/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.gsd.middleware.controller;

import edu.hsfulda.gsd.middleware.model.Item;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
public class ShoppingCart extends HttpServlet {

    private final String HTML_PAGE_NAME = "shopping-cart.html";
    private final String COOKIE_NAME = "shopping_cart_" + (Math.random() % 100);
    private final Collection<Item> items = Collections.emptySet();
    
    private String readCookie (HttpServletRequest request) {
        if (null == request.getCookies()) {
            return "";
        }
        
        Optional<Cookie> cookie = Stream.of(request.getCookies())
                                        .filter(c -> COOKIE_NAME.equals(c.getName()))
                                        .findFirst();
        
        return (cookie.isPresent() ? cookie.get().getValue() : "");
    }   
    
    private void writeCookie (HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        
        for (Item itm : items) {
            sb.append(itm.getName());
            sb.append(",");
            sb.append(itm.getUnitPrice());
            sb.append(",");
            sb.append(itm.getQuantity());
            sb.append("|");
        }
        
        response.addCookie(new Cookie(COOKIE_NAME, sb.toString()));
    }
    
    private Set<Item> getItems (String cookieItems) {
        if (cookieItems.isEmpty()) {
            return Collections.emptySet();
        }
            
        String[] itms = cookieItems.split("\\|");
        Set<Item> cartItems = new HashSet<>();
        
        for (String itm : itms) {
            String[] itmParts = itm.split(",");
            cartItems.add(Item.getInstance(itmParts[0], Double.valueOf(itmParts[1]))
                                .setQuantity(Integer.valueOf(itmParts[2])));
        }
        
        return cartItems;
    }
    
    private void addItem (Map<String, String[]> data) {
        Item itm = Item.getInstance(data.get("name")[0], Double.valueOf(data.get("price")[0]))
                        .setQuantity(Integer.valueOf(data.get("quantity")[0]));
        
        items.add(itm);
        
        boolean isExist = false;
        
        for (Item i : items) {
            if (itm.getName().equals(i.getName())) {
                i.setQuantity(i.getQuantity() + itm.getQuantity());
                isExist = true;
                break;
            }
        }
        
        if (!isExist) {
            items.add(itm);
        }
    }
    
    private void removeItem (Map<String, String[]> data) {
        Item itm = Item.getInstance(data.get("name")[0], Double.valueOf(data.get("price")[0]))
                        .setQuantity(Integer.valueOf(data.get("quantity")[0]));
        
        boolean isQuantityZero = false;
        Item itmToRemove = null;
        
        for (Item i : items) {
            if (itm.getName().equals(i.getName())) {
                i.setQuantity(i.getQuantity() - itm.getQuantity());
                if (0 == i.getQuantity()) {
                    isQuantityZero = true;
                    itmToRemove = i;
                }
                break;
            }
        }
        
        if (isQuantityZero) {
            items.remove(itmToRemove);
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
        
        String cookieItems = readCookie(request);
        items.addAll(getItems(cookieItems));
        
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
        
        String cartHtmlPage = sb.toString()
                                .replace("#itemCount#", "" + items.size());
        
        String itmList = "";
        
        if (0 < items.size()) {
            itmList = "<table>\n" +
"                <thead>\n" +
"                    <tr>\n" +
"                        <th>Name</th>\n" +
"                        <th>Unit Price</th>\n" +
"                        <th>Quantity</th>\n" +
"                        <th>Total Price</th>\n" +                    
"                        <th>Action</th>\n" +
"                    </tr>\n" +
"                </thead>\n" +
"                <tbody>\n";
            
            String strItem = "";
            int i;
            
            for (Item itm : items) {
                strItem = "                    <tr>\n" +
"                        <td>" + itm.getName() + "</td>\n" +
"                        <td>" + itm.getUnitPrice() + "</td>\n" +
"                        <td>" + itm.getQuantity()+ "</td>\n" +
"                        <td>" + itm.getTotalPrice()+ "</td>\n" +                        
"                        <td>\n" +
"                            <select>\n";
                
                for (i = itm.getQuantity(); i > 0; i--) {
                    strItem = strItem.concat("<option value=\"" + i + "\"" + (itm.getQuantity() == i ? (" selected=\"selected\"") : "") + ">" + i + "</option>\n");
                }
                
                strItem = strItem.concat("</select><br>\n" +
"                        <input type=\"button\" value=\"Remove From Shopping Cart\" onclick=\"removeFromCart()\"></td>\n" +
"                    </tr>\n");
                
                itmList = itmList.concat(strItem);
            }
            
            itmList = itmList.concat("                </tbody>\n" +
"            </table>");
        }
        
        cartHtmlPage = cartHtmlPage.replace("#itemList#", itmList);
        
        response.setContentType("text/html;charset=UTF-8");
            
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(cartHtmlPage);
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
        
        String cookieItems = readCookie(request);
        items.addAll(getItems(cookieItems));
        
        String action = request.getParameter("action");
        
        if (Action.ADD.equals(Action.valueOf(action))) {
            addItem(request.getParameterMap());
            writeCookie(response);
            
            response.setContentType("text/plain;charset=UTF-8");
            
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println(items.size());
            }
        }
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

    private enum Action {
        ADD, REMOVE
    }
}