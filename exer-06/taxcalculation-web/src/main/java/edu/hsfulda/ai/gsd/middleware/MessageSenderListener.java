/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jms.CompletionListener;
import javax.jms.Message;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */
public class MessageSenderListener implements CompletionListener {

    private final HttpServletResponse response;
    private final String pageHtml;

    public MessageSenderListener(HttpServletResponse response, String pageHtml) {
        this.response = response;
        this.pageHtml = pageHtml;
    }

    public static MessageSenderListener newInstance (final HttpServletResponse response, final String pageHtml) {
        
        return new MessageSenderListener(response, pageHtml);
    } 
    
    private void sendHttpResponse (String clientMessage) {
        
        String taxHtmlPage = pageHtml
                                .replace("#result#", clientMessage);
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.print(taxHtmlPage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
    @Override
    public void onCompletion(Message message) {
        
        sendHttpResponse("Record is successfuly sent as a Mapped Message");
    }

    @Override
    public void onException(Message message, Exception exception) {
        
        sendHttpResponse("Record is failed to sent. Error: " + exception.getMessage());
    }
    
}
