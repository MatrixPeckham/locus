/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.AdBean;
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
public class ItemSelectorTagHandler extends SimpleTagSupport {

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {

            ArrayList<AdBean> list = DBManager.get().getAdsForUser(-1);
            
            JspFragment f = getJspBody();
            
            out.print("Item: <select id=\"itemselect\" onchange=\"changeTransByItem()\">");
            out.print("<option value=\"\" selected=\"selected\"></option>");
            for(AdBean b : list){
                out.print("<option value=\""+b.getId()+"\">"+b.getItem()+" by "+b.getCompany()+"</option>");
            }
            
            out.print("</select>");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in ItemSelectorTagHandler tag", ex);
        }
    }
}
