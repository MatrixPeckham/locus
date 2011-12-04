/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.EmployeeBean;
import com.locus305.database.DBManager;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class GetEmployeeTagHandler extends SimpleTagSupport {
    private int empl;
    private String var;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        

        EmployeeBean b = new EmployeeBean();
        DBManager.get().fillEmployeeBean(b,empl);
        getJspContext().setAttribute(var, b);
            
    }

    public void setEmpl(int empl) {
        this.empl = empl;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
