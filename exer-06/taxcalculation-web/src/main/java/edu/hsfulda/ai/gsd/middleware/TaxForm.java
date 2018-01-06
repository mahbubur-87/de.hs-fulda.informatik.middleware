/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.jms.CompletionListener;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mahbuburrahman
 */

@WebServlet(urlPatterns = {"/"})
public class TaxForm extends HttpServlet {

    private final StringBuilder HTML_PAGE = new StringBuilder()
    .append("<html>")
	.append("<head>")
		.append("<title>Tax Calculator</title>")
	.append("</head>")
	.append("<body>")
            .append("<div>")
                .append("<h1>Tax Form (WS2017)</h1>")
                .append("<form id=\"taxForm\" method=\"POST\" action=\"\">")
                    .append("<table>")
                        .append("<tbody>")
                            .append("<tr>")
                                .append("<td>Amount:</td>")
                                .append("<td><input name=\"amount\" type=\"number\" required></td>")
                            .append("</tr>")
                            .append("<tr>")
                                .append("<td>Tax(%):</td>")
                                .append("<td><input name=\"tax\" type=\"number\" required></td>")
                            .append("</tr>")
                            .append("<tr>")
                                .append("<td>Currency:</td>")
                                .append("<td>")
                                    .append("<select name=\"currency\">")
                                        .append("<option value=\"Dollar\" selected=\"selected\">Dollar</option>")
                                        .append("<option value=\"Euro\">Euro</option>")
                                    .append("</select>")
                                .append("</td>")
                            .append("</tr>")
                            .append("<tr>")
                                .append("<td><input type=\"submit\" value=\"Calculate\"></td>")
                                .append("<td><input type=\"reset\" value=\"Reset\"></td>")
                            .append("</tr>")
                        .append("</tbody>")        
                    .append("</table>")
                .append("</form>")
            .append("</div>")
            .append("<hr>")
            .append("<div>#result#</div>")
//            .append("<hr>")
//            .append("<div>")
//            .append("<a href=\"../taxcalculation-web/results\"> View Details </a>")
//                .append("<input type=\"button\" value=\"Load Data\" onclick=\"loadData()\">")
//                .append("<br>")
//                .append("<p id=\"results\"></p>")
//            .append("</div>")
//            .append("<script type=\"text/javascript\">")
//                .append("function loadData () {")   
//                    .append("var xhttp = new XMLHttpRequest();")
//                    .append("xhttp.onreadystatechange = function() {")
//                        .append("if (this.readyState == 4 && this.status == 200) {")
//                            .append("document.getElementById('results').innerHTML = xhttp.responseText.trim();")
//                        .append("}")
//                    .append("};")
//                    .append("xhttp.open(\"GET\", \"../taxcalculation-web?action=GET_DATA\", true);")
//                    .append("xhttp.send();")
//                .append("}")
//            .append("</script>")
	.append("</body>")
.append("</html>");

    @Resource(mappedName = "taxcalculation")
    private ConnectionFactory connection;
    
    @Resource(mappedName = "taxQueue")
    private Queue taxQueue;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
        Map<String, Object> tax = new LinkedHashMap<>();
        
        tax.put("amount", Double.parseDouble(request.getParameter("amount")));
        tax.put("taxRate", Double.parseDouble(request.getParameter("tax")));
        tax.put("currency", request.getParameter("currency"));
        tax.put("mdbCallDate", new Date().getTime());
 
        try (JMSContext context = connection.createContext()) {
            context.createProducer().send(taxQueue, tax);
            
//            CompletionListener senderListener = MessageSenderListener.newInstance(response, HTML_PAGE.toString());
//            context.createProducer().setAsync(senderListener).send(taxQueue, tax);
        }
        
        String taxHtmlPage = HTML_PAGE.toString()
                                .replace("#result#", "Record is successfuly sent as a Mapped Message");
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.print(taxHtmlPage);
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
        
        String action = request.getParameter("action");
        
        if (null == action) {
            String taxHtmlPage = HTML_PAGE.toString()
                                .replace("#result#", "");
        
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print(taxHtmlPage);
            }
        }
        
//        if ("GET_DATA".equals(action)) {
//            try {
//                List<Tax> taxs = tcb.findAll();
//                
//                StringBuilder dataTable = new StringBuilder();
//                
//                dataTable.append("<table>")
//                        .append("<thead>")
//                        .append("<tr>")
//                        .append("<td>Date</td>")
//                        .append("<td>Amount</td>")
//                        .append("<td>Tax Rate</td>")
//                        .append("<td>Total Amount</td>")
//                        .append("<td>Currency</td>")
//                        .append("</tr>")
//                        .append("</thead>")
//                        .append("<tbody>");
//                
//                if (!taxs.isEmpty()) {
//                    taxs.forEach(t -> dataTable.append("<tr>")
//                        .append("<td>").append(sdf.format(t.getCreatedDate())).append("</td>")
//                        .append("<td>").append(df.format(t.getAmount())).append("</td>")
//                        .append("<td>").append(df.format(t.getTaxRate())).append("</td>")
//                        .append("<td>").append(df.format(t.getTotalAmount())).append("</td>")
//                        .append("<td>").append(t.getCurrency()).append("</td>")
//                        .append("</tr>"));
//                }
//                
//                dataTable.append("</tbody></table>");
//                
//                response.setContentType("text/html;charset=UTF-8");
//                try (PrintWriter out = response.getWriter()) {
//                    out.print(dataTable.toString());
//                }
//            } catch (Exception ex) {
//            }
//        }
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Tax Form - calculates tax value from amount and tax percentage. "
                + "Then add tax value with amount and displays total amount as final result";
    }// </editor-fold>

}
