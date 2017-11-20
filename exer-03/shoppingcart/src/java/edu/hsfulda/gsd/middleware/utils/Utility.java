/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.gsd.middleware.utils;

import edu.hsfulda.gsd.middleware.model.Item;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
public class Utility {
    
    public static Collection<Item> readCookie (HttpServletRequest request, String cookieName) {
        
        if (null == request.getCookies()) {
            return Collections.emptySet();
        }
        
        Optional<Cookie> cookie = Stream.of(request.getCookies())
                                        .filter(c -> cookieName.equals(c.getName()))
                                        .findFirst();
        
        if (!cookie.isPresent()) {
            return Collections.emptySet();
        }
        
        String cookieValue = cookie.get().getValue();
        
        if (cookieValue.isEmpty()) {
            return Collections.emptySet();
        }
        
        String[] itms = cookieValue.split("\\|");
        Set<Item> cartItems = new HashSet<>();
        
        for (String itm : itms) {
            String[] itmParts = itm.split(",");
            cartItems.add(Item.getInstance(itmParts[0], Double.valueOf(itmParts[1]))
                                .setQuantity(Integer.valueOf(itmParts[2])));
        }
        
        return cartItems;
    }   
    
    public static void writeCookie (HttpServletResponse response, String cookieName, Collection<Item> items) {
        
        StringBuffer sb = new StringBuffer();
        
        for (Item itm : items) {
            sb.append(itm.getName());
            sb.append(",");
            sb.append(itm.getUnitPrice());
            sb.append(",");
            sb.append(itm.getQuantity());
            sb.append("|");
        }
        
        response.addCookie(new Cookie(cookieName, sb.toString()));
    }
}
