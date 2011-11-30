/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.MessageBean;
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
public class ListMessagesTagHandler extends SimpleTagSupport {

    private int user;

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
            ArrayList<MessageBean> list = DBManager.get().getMessages(user);

            JspFragment f = getJspBody();
            int i = 0;
            for (MessageBean b : list) {
                context.setAttribute("curMessage", b);
                context.setAttribute("curIndex",i);
                if (f != null) {
                    f.invoke(out);
                }
                i++;
            }
            context.removeAttribute("curMessage");
            context.removeAttribute("curIndex");
            out.println("<form class=\"hidden\"><input type=\"hidden\" id=\"nummessages\" value=\""+i+"\" /></form>");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in ListMessagesTagHandler tag", ex);
        }
    }

    public void setUser(int user) {
        this.user = user;
    }
}
