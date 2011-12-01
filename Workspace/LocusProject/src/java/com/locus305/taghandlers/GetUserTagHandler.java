/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.UserBean;
import com.locus305.database.DBManager;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class GetUserTagHandler extends SimpleTagSupport {
    private String var;
    private String usr;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        UserBean b = new UserBean();
        DBManager.get().fillUserBean(b, usr);
        getJspContext().setAttribute(var, b);
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }
}
