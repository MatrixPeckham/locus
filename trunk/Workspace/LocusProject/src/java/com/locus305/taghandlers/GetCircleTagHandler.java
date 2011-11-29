/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.database.DBManager;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class GetCircleTagHandler extends SimpleTagSupport {
    private String var;
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
        context.setAttribute(var, DBManager.get().getCircle(circle));
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }
}
