/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.CircleBean;
import com.locus305.database.DBManager;
import java.util.ArrayList;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class ListCirclesTagHandler extends SimpleTagSupport {
    private String user=null;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        JspContext context = getJspContext();
        try {
            ArrayList<CircleBean> list;
            
            if(user!=null){
                list=DBManager.get().getCircles(user);
            } else {
                list=DBManager.get().getCircles();
            }
            JspFragment f = getJspBody();
            for(CircleBean b : list){
                context.setAttribute("curCircle", b);
                if (f != null) {
                    f.invoke(out);
                }
            }
            context.removeAttribute("curCircle");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in ListCirclesTagHandler tag", ex);
        }
    }

    public void setUser(String user) {
        this.user = user;
    }
}
