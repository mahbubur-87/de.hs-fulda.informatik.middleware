/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.util.Collection;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author mahbuburrahman
 */
@Remote
public interface ShoppingCartBeanRemote {

    Collection<Item> getItems();

    void addItem(Map<String, String[]> data);

    int removeItem(String itemName, int quantity);

    int getItemCount();
    
}
