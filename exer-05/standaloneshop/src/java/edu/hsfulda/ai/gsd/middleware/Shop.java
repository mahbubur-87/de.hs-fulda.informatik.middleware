/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.EJB;

/**
 *
 * @author mahbuburrahman
 */
public class Shop {

    @EJB
    private static ShoppingCartBeanRemote cart;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        doCartOperation();
    }
    
    public static void doCartOperation () {
        
        System.out.println("Before: cart items count: " + cart.getItemCount());
        System.out.println("Before: cart items: " + cart.getItems().toString());
        
        Map<String, String[]> data = new LinkedHashMap<>();
        
        data.put("name", new String[] {"item 1"});
        data.put("price", new String[] {"10.00"});
        data.put("quantity", new String[] {"2"});
        
        cart.addItem(data);
        
        System.out.println("After: cart items count: " + cart.getItemCount());
        System.out.println("After: cart items: " + cart.getItems().toString());
    }
}
