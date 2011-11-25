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
 * @author Owner
 */
public class LocusLogo extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/png");
        try {
            int w = Integer.parseInt(getParam(request,"width","200"));
            int h = Integer.parseInt(getParam(request,"height","200"));
            int n = Integer.parseInt(getParam(request,"points","50"));
            int nl = Integer.parseInt(getParam(request,"lines","50"));
            int smallrad = 4;
            BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D)bi.getGraphics();
            Font f = getFontWithHeight(h, 5, g);
            FontMetrics met = g.getFontMetrics(f);
            
            
            
            
            Rectangle2D b = met.getStringBounds("Locus", g);
            
            
            w=(int)b.getWidth();
            int yf = met.getAscent();
            
            int lWid = (int) met.getStringBounds("L", g).getWidth();
            int oWid = (int) met.getStringBounds("o", g).getWidth();
            int loWid = (int) met.getStringBounds("L0", g).getWidth();
            
            g.dispose();
            bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D)bi.getGraphics();
            
            
            
            g.setColor(Color.white);
//            g.fillRect((int)x, (int)y-(int)b.getWidth()/2, (int)b.getWidth(), (int)b.getHeight());
//            Paint p = g.getPaint();
//            g.setPaint(new GradientPaint(0, 20, Color.blue, 0, 0, Color.green,
//                    true));
  //          g.setPaint(p);
            Color logoColor = new Color(12,12,90);
            g.setColor(logoColor);
            g.setFont(f);
            g.drawString("L", 0, yf);
            g.drawString("cus", loWid, yf);
            
            ArrayList<Ellipse2D.Double> list = new ArrayList<Ellipse2D.Double>();
 
            for(int i = 0; i<n; i++){
                double angle = Math.random()*Math.PI*2;
                double rad = (oWid-smallrad)/2;
                double x = Math.cos(angle)*rad+lWid+oWid/2;
                double y = Math.sin(angle)*rad+yf-oWid/2;
                list.add(new Ellipse2D.Double(x-smallrad/2,y-smallrad/2,smallrad,smallrad));
    
            }
            
            for(int i = 0; i<nl; i++){
                int l1 = (int)(Math.random()*n);
                int l2 = (int)(Math.random()*n);
                while(l1==l2){
                    l2 = (int)(Math.random()*n);
                }
                Ellipse2D.Double e1 = list.get(l1);
                Ellipse2D.Double e2 = list.get(l2);
                if(e1!=e2){
                    g.setColor(Color.lightGray); 
                    g.draw(new Line2D.Double(e1.getX()+smallrad/2,e1.getY()+smallrad/2,e2.getX()+smallrad/2,e2.getY()+smallrad/2));
                }

            }
            
            for(int i = 0; i<n; i++){
                float r = Math.random()<0.5 ? 0 : 1;
                float gr = (float)Math.random()<0.5 ? 0 : 1;
                float bl = (float)Math.random()<0.5 ? 0 : 1;
                while(r==1&&gr==1&&bl==1){
                    r = Math.random()<0.5 ? 0 : 1;
                    gr = (float)Math.random()<0.5 ? 0 : 1;
                    bl = (float)Math.random()<0.5 ? 0 : 1;
                }
                g.setColor(new Color(r,gr,bl));
                g.fill(list.get(i));
            }
    
            g.dispose();
            ImageIO.write(bi, "png", response.getOutputStream());
        } finally {            
        }
    }
    
    private Font getFontWithHeight(int h, int thresh, Graphics g){
        int pts = 24;
        int pts2 = 24;
        Font f;
        FontMetrics met=null;
        do{
            if(met!=null){
                int npts = pts;
                int he = met.getHeight();
                if(h>he){
                    if(pts2>pts){
                        npts=pts2+pts;
                        npts/=2;
                    } else {
                        npts=pts*2;
                    }
                } else {
                    if(pts2<pts){
                        npts=pts2+pts;
                        npts/=2;
                    } else {
                        pts/=2;
                    }
                }
                pts2=pts;
                pts=npts;
            }
            f = new Font("serif", Font.ITALIC, pts);
            met = g.getFontMetrics(f);
        } while(h-met.getHeight()>thresh||h-met.getHeight()<0);
        return f;
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
