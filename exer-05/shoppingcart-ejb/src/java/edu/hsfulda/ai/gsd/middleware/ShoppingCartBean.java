/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.ejb.Stateful;

/**
 *
 * @author mahbuburrahman
 */
@Stateful
public class ShoppingCartBean implements ShoppingCartBeanRemote {

    private final Collection<Item> items = new LinkedHashSet<>();
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    @Override
    public Collection<Item> getItems() {
        return items;
    }

    @Override
    public void addItem(Map<String, String[]> data) {
        
        Item itm = Item.getInstance(data.get("name")[0], Double.valueOf(data.get("price")[0]))
                        .setQuantity(Integer.valueOf(data.get("quantity")[0]));
        
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

    @Override
    public int removeItem(String itemName, int quantity) {
        
        int reaminingQty = 0;
        boolean isQuantityZero = false;
        Item itmToRemove = null;
        
        for (Item i : items) {
            if (itemName.equals(i.getName())) {
                reaminingQty = i.getQuantity() - quantity; 
                i.setQuantity(reaminingQty);
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
        
        return reaminingQty;
    }
}
