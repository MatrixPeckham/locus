/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.servlets.users;

import com.locus305.beans.AccountBean;
import com.locus305.beans.UserBean;
import com.locus305.database.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class EditUserInfo extends HttpServlet {

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
        UserBean user = (UserBean)request.getSession().getAttribute("userInfo");
        if(user==null) return;
        user.setAddr(request.getParameter("addr"));
        user.setCity(request.getParameter("city"));
        user.setFname(request.getParameter("fname"));
        user.setLname(request.getParameter("lname"));
        user.setPhone(request.getParameter("phone"));
        user.setState(request.getParameter("state"));
        user.setPreferences(request.getParameter("preferences"));
        user.setZip(Integer.parseInt(request.getParameter("zip")));
        ArrayList<AccountBean> accounts = new ArrayList<AccountBean>();
        int numCards = Integer.parseInt(request.getParameter("numCards"));
        for(int i=0;i<=numCards;i++){
            AccountBean b = new AccountBean();
            
            int num = Integer.parseInt(request.getParameter("cardacc"+i));
            String ccn = request.getParameter("cardnum"+i);
            b.setAccnum(num);
            b.setCcn(ccn);
            accounts.add(b);
        }
        try {
            out.print(DBManager.get().updateUserData(user)&&DBManager.get().updateUserAccounts(numCards, accounts));
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
