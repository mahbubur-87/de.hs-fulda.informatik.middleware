/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware.taxcalculation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
            .append("<hr>")
            .append("<div>")
                .append("<input type=\"button\" value=\"Load Data\" onclick=\"loadData()\">")
                .append("<br>")
                .append("<p id=\"results\"></p>")
            .append("</div>")
            .append("<script type=\"text/javascript\">")
                .append("function loadData () {")   
                    .append("var xhttp = new XMLHttpRequest();")
                    .append("xhttp.onreadystatechange = function() {")
                        .append("if (this.readyState == 4 && this.status == 200) {")
                            .append("document.getElementById('results').innerHTML = xhttp.responseText.trim();")
                        .append("}")
                    .append("};")
                    .append("xhttp.open(\"GET\", \"../taxcalculation-web?action=GET_DATA\", true);")
                    .append("xhttp.send();")
                .append("}")
            .append("</script>")
	.append("</body>")
.append("</html>");

    @EJB
    private TaxCalculationBeanRemote tcb; 

    
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
        
        Tax tax = Tax.getInstance(Double.parseDouble(request.getParameter("amount")), 
                                    Double.parseDouble(request.getParameter("tax")), 
                                        request.getParameter("currency"));
        
        StringBuilder resultText = new StringBuilder();
        
        try {
            tax.setTotalAmountWithTax(tcb.doCalculation(tax.getAmount(), tax.getTaxPercentage()));
            
            tcb.save(tax);
            
            resultText.append("Remote Interface: The total amount is ")
                    .append(tax.getTotalAmountWithTax()).append(" ")
                    .append(tax.getCurrency()).append(".<br> Record is Saved Successfuly ...");
            
        } catch (RuntimeException | SQLException ex) {
            resultText.append("HTTP 505: Internal Server Error");
        }    
       
        String taxHtmlPage = HTML_PAGE.toString()
                                .replace("#result#", resultText);
        
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
        
        if ("GET_DATA".equals(action)) {
            try {
                List<Tax> taxs = tcb.findAll();
                
                StringBuilder dataTable = new StringBuilder();
                
                dataTable.append("<table>")
                        .append("<thead>")
                        .append("<tr>")
                        .append("<td>Date</td>")
                        .append("<td>Amount</td>")
                        .append("<td>Tax Rate</td>")
                        .append("<td>Total Amount</td>")
                        .append("<td>Currency</td>")
                        .append("</tr>")
                        .append("</thead>")
                        .append("<tbody>");
                
                if (!taxs.isEmpty()) {
                    taxs.forEach(t -> dataTable.append("<tr>")
                        .append("<td>").append(t.getCreatedDate()).append("</td>")
                        .append("<td>").append(t.getAmount()).append("</td>")
                        .append("<td>").append(t.getTaxPercentage()).append("</td>")
                        .append("<td>").append(t.getTotalAmountWithTax()).append("</td>")
                        .append("<td>").append(t.getCurrency()).append("</td>")
                        .append("</tr>"));
                }
                
                dataTable.append("</tbody></table>");
                
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.print(dataTable.toString());
                }
            } catch (SQLException ex) {
            }
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
