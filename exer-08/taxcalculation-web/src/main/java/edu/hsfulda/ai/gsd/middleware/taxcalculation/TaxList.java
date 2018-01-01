/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.ai.gsd.middleware.taxcalculation;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
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
@WebServlet(urlPatterns = {"/results"})
public class TaxList extends HttpServlet {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    
    @EJB
    private TaxCalculationBeanRemote tcb;
    
    private StringBuilder generateHtmlTable () {
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
                .append("<td>").append(sdf.format(t.getCreatedDate())).append("</td>")
                .append("<td>").append(df.format(t.getAmount())).append("</td>")
                .append("<td>").append(df.format(t.getTaxRate())).append("</td>")
                .append("<td>").append(df.format(t.getTotalAmount())).append("</td>")
                .append("<td>").append(t.getCurrency()).append("</td>")
                .append("</tr>"));
        }

        dataTable.append("</tbody></table>");
        
        return dataTable;
    }
    
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TaxList</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TaxList at " + request.getContextPath() + "</h1>");
            out.println("<div> <a href=\"../taxcalculation-web\"> Back </a> <br><br>");
            out.println(this.generateHtmlTable());
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
        processRequest(request, response);
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
        return "Short description";
    }// </editor-fold>

}
