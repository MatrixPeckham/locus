/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.AdBean;
import com.locus305.beans.UserBean;
import com.locus305.database.DBManager;
import java.util.ArrayList;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Comedy Option
 */
public class GetAdForUserTagHandler extends SimpleTagSupport {

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
            UserBean currentUser = (UserBean)context.getAttribute("userInfo", PageContext.SESSION_SCOPE);
            int userId = currentUser.getUserid();
            ArrayList<AdBean> adsForUser = DBManager.get().getAdsForUser(userId);
            if(adsForUser.size() > 0){
                int randomAd = (int)(Math.random() * adsForUser.size());
                context.setAttribute("curAd", adsForUser.get(randomAd));
            }
            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }


        } catch (java.io.IOException ex) {
            throw new JspException("Error in GetAdTagHandler tag", ex);
        }
    }
}
