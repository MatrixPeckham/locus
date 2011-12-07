/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.BestSellerBean;
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
public class LoopBestSellersTagHandler extends SimpleTagSupport {

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

            
            JspFragment f = getJspBody();
            ArrayList<BestSellerBean> list = DBManager.get().getBestSellers();
            
            for(int i = 0; i<Math.min(list.size(), 3); i++){
                context.setAttribute("curBest", list.get(i));
                context.setAttribute("curInd", i+1);
                if (f != null) {
                    f.invoke(out);
                }
            }
            context.removeAttribute("curBest");
            context.removeAttribute("curInd");
     
        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopBestSellersTagHandler tag", ex);
        }
    }
}
