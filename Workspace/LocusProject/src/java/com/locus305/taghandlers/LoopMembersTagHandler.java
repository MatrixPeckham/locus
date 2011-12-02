/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.UserBean;
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
public class LoopMembersTagHandler extends SimpleTagSupport {

    private int circle;

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
            ArrayList<UserBean> list = DBManager.get().listMembers(circle);
            for (UserBean b : list) {
                context.setAttribute("curUsr", b);
                if (f != null) {
                    f.invoke(out);
                }
            }

            context.removeAttribute("curUsr");
            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopMembersTagHandler tag", ex);
        }
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }
}
