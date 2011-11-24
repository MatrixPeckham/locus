/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bill
 */
public class LocusImage extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/png");
        try {
            int w = Integer.parseInt(getParam(request,"width","200"));
            int h = Integer.parseInt(getParam(request,"height","200"));
            int n = Integer.parseInt(getParam(request,"points","50"));
            int smallrad = 10;
            BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D)bi.getGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, w, h);
            ArrayList<Ellipse2D.Double> list = new ArrayList<Ellipse2D.Double>();
            for(int i = 0; i<n; i++){
                double angle = Math.random()*Math.PI*2;
                double rad = Math.min(w, h)/2-20;
                double x = Math.cos(angle)*rad+w/2;
                double y = Math.sin(angle)*rad+h/2;
                list.add(new Ellipse2D.Double(x,y,smallrad,smallrad));
            }
            for(int i =0; i<n; i+=4){
                for(int j=2; j<n; j+=4){
                    Ellipse2D.Double e1 = list.get(i);
                    Ellipse2D.Double e2 = list.get(j);
                    if(e1!=e2){
                        g.setColor(Color.red);
                        g.draw(new Line2D.Double(e1.getX()+smallrad/2,e1.getY()+smallrad/2,e2.getX()+smallrad/2,e2.getY()+smallrad/2));
                    }
                }
            }
            for(int i = 0; i<n; i++){
                float r = Math.random()<0.5 ? 0 : 1;
                float gr = (float)Math.random()<0.5 ? 0 : 1;
                float b = (float)Math.random()<0.5 ? 0 : 1;
                while(r==1&&gr==1&&b==1){
                    r = Math.random()<0.5 ? 0 : 1;
                    gr = (float)Math.random()<0.5 ? 0 : 1;
                    b = (float)Math.random()<0.5 ? 0 : 1;
                }
                g.setColor(new Color(r,gr,b));
                g.fill(list.get(i));
            }
            Font f = new Font("serif", Font.BOLD, 40);
            g.setFont(f);
            FontMetrics met = g.getFontMetrics(f);
            Rectangle2D b = met.getStringBounds("Locus", g);
            double x = w/2 - b.getWidth()/2;
            double y = h/2 + b.getHeight()/2;
            g.setColor(Color.white);
//            g.fillRect((int)x, (int)y-(int)b.getWidth()/2, (int)b.getWidth(), (int)b.getHeight());
//            Paint p = g.getPaint();
//            g.setPaint(new GradientPaint(0, 20, Color.blue, 0, 0, Color.green,
//                    true));
            g.setColor(new Color(0,0,255));
            g.drawString("Locus", (int)x, (int)y);
  //          g.setPaint(p);
            g.dispose();
            ImageIO.write(bi, "png", response.getOutputStream());
        } finally {            
        }
    }
    private String getParam(HttpServletRequest request, String param, String def) {
        String parameter = request.getParameter(param);
        if (parameter == null || "".equals(parameter)) {
            return def;
        } else {
            return parameter;
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
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
