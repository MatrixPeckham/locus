/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.servlets.employees;

import com.locus305.beans.EmployeeBean;
import com.locus305.beans.UserBean;
import com.locus305.database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class EditEmployee extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String addr = request.getParameter("addr");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        int zip = Integer.parseInt(request.getParameter("zip"));
        int hourly = Integer.parseInt(request.getParameter("hourly"));
        int manager = ((UserBean)request.getSession().getAttribute("userInfo")).getUserid();
        try {
            UserBean b = new UserBean();
            b.setUsername(username);
            b.setFname(fname);
            b.setLname(lname);
            b.setAddr(addr);
            b.setCity(city);
            b.setState(state);
            b.setPhone(phone);
            b.setZip(zip);
            b.setType(1);

            int id = DBManager.get().getUserIDFromName( username );
            b.setUserid(id);
            DBManager.get().updateUserData(b);

            EmployeeBean eb = new EmployeeBean();
            eb.setUsr(b);
            eb.setHourly(hourly);
            eb.setManager(manager);
            DBManager.get().editEmployee(eb);
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
