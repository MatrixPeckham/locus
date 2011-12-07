/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.AdBean;
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
public class LoopUserAdsTagHandler extends SimpleTagSupport {
    private int userid;

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
            ArrayList<AdBean> list = DBManager.get().getAdsForUser(userid);
            for(AdBean b : list){
                context.setAttribute("curAd", b);
                if (f != null) {
                    f.invoke(out);
                }
            }

            context.removeAttribute("curAd");
            
        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopUserAdsTagHandler tag", ex);
        }
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
