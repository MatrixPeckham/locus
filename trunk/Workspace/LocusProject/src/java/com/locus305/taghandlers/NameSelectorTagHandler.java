/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.AdBean;
import com.locus305.beans.UserBean;
import com.locus305.database.DBManager;
import java.util.ArrayList;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class NameSelectorTagHandler extends SimpleTagSupport {

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {

            ArrayList<UserBean> list = DBManager.get().getAllUsers();
            
            JspFragment f = getJspBody();
            
            out.print("Item: <select id=\"nameselect\" onchange=\"changeTransByName()\">");
            out.print("<option value=\"\" selected=\"selected\"></option>");
            for(UserBean b : list){
                out.print("<option value=\""+b.getUserid()+"\">"+b.getUsername()+"</option>");
            }
            
            out.print("</select>");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in NameSelectorTagHandler tag", ex);
        }
    }
}
