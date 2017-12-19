/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.gsd.middleware.controller.shoppingcart;

import edu.hsfulda.ai.gsd.middleware.Item;
import edu.hsfulda.ai.gsd.middleware.ShoppingCartBeanRemote;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
@WebServlet(urlPatterns = {"/cart"})
public class ShoppingCart extends HttpServlet {

    private final String HTML_PAGE_NAME = "shopping-cart.html";
    
    @EJB
    private ShoppingCartBeanRemote cart; 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public void displayCartItems (HttpServletResponse response) throws ServletException, IOException {
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
        
        Collection<Item> items = cart.getItems();
        
        String cartHtmlPage = sb.toString()
                                .replace("#itemCount#", "" + items.size());
        
        String itmList = "";
        
        if (0 < items.size()) {
            itmList = "<table id=\"cartItems\">\n" +
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
            int i, index;
            double grandTotalPrice = 0d;
            
            index = 1;
            
            for (Item itm : items) {
                strItem = "                    <tr>\n" +
"                        <td>" + itm.getName() + "</td>\n" +
"                        <td>" + itm.getUnitPrice() + "</td>\n" +
"                        <td>" + itm.getQuantity()+ "</td>\n" +
"                        <td>" + itm.getTotalPrice()+ "</td>\n" +                        
"                        <td>\n" +
"                            <select id=\"itm" + index + "Qty\">\n";
                
                for (i = itm.getQuantity(); i > 0; i--) {
                    strItem = strItem.concat("<option value=\"" + i + "\"" + (itm.getQuantity() == i ? (" selected=\"selected\"") : "") + ">" + i + "</option>\n");
                }
                
                strItem = strItem.concat("</select>&nbsp;&nbsp;&nbsp;\n" +
"                        <input type=\"button\" value=\"Remove\" onclick=\"removeFromCart(" + index + ")\"></td>\n" +
"                    </tr>\n");
                
                itmList = itmList.concat(strItem);
                
                grandTotalPrice += itm.getTotalPrice().doubleValue();
                
                index++;
            }
            
            itmList = itmList.concat("                </tbody>\n" +
"            </table>\n" +
              "<hr>\n" +
               "<h5>Grand Total Price: <span id=\"grandTotalPrice\">" + grandTotalPrice + "</span> [<a href=\"\">Checkout</a>]</h5>");
        }
        
        cartHtmlPage = cartHtmlPage.replace("#itemList#", itmList);
        
        response.setContentType("text/html;charset=UTF-8");
            
        PrintWriter out = response.getWriter();
        out.println(cartHtmlPage);
        out.close();
    }
    
    public void getCartItemsCount (HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(cart.getItemCount());
        out.close();
    }
    
    public void resetCart (HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.reset();
        request.getSession().invalidate();
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(1);
        out.close();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (null == action) {
            displayCartItems(response);
            return;
        }
        
        switch (Action.valueOf(action)) {
            case GET_ITM_COUNT: getCartItemsCount(response);
                                break;
                                
            case RESET: resetCart(request, response);
                        break;
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
        
        Action action = Action.valueOf(request.getParameter("action"));
        
        switch (action) {
            case ADD: doAdd(request.getParameterMap(), response);
                      break;
                      
            case REMOVE: doRemove(request.getParameterMap(), response);
                         break;
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

    private void doAdd(Map<String, String[]> reqParamMap, HttpServletResponse response) throws IOException {
        
        cart.addItem(reqParamMap);
        
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(cart.getItemCount());
        out.close();
    }
    
    private void doRemove(Map<String, String[]> reqParamMap, HttpServletResponse response) throws IOException {
        
        String itemName = reqParamMap.get("name")[0];
        int qty = Integer.parseInt(reqParamMap.get("quantity")[0]);
        
        int reaminingItemQty = cart.removeItem(itemName, qty);
 
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();    
        out.println(reaminingItemQty + "," + cart.getItemCount());
        out.close();
    }

    private enum Action {
        ADD, REMOVE, GET_ITM_COUNT, RESET
    }
}