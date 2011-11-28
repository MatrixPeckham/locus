/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.AccountBean;
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
public class LoopUserAccountsTagHandler extends SimpleTagSupport {
    private String var;
    private String ivar;
    private String username;

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

            
            ArrayList<AccountBean> accounts = DBManager.get().getUserAccounts(username);
            JspFragment f = getJspBody();
            
            if(accounts==null)
                return;
            int i=0;
            for(AccountBean b : accounts){ 
                context.setAttribute(ivar,i);
                context.setAttribute(var, b);
                if (f != null) {
                    f.invoke(out);
                }
                i++;
            }
            context.setAttribute(ivar, null);
            context.setAttribute(var, null);

            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in ListUsersAccountsTagHandler tag", ex);
        }
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setIvar(String ivar) {
        this.ivar = ivar;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
