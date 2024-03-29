/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.TransactionBean;
import com.locus305.database.DBManager;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class LoopTransactionsTagHandler extends SimpleTagSupport {
    private int month;
    private int year;

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
            if(year==0){
                Calendar c = Calendar.getInstance();
                year=c.get(Calendar.YEAR);
                month=c.get(Calendar.MONTH)+1;
            }
            ArrayList<TransactionBean> list = DBManager.get().getSalesForMonth(month,year);
            
            int total = 0;
            for(TransactionBean b : list){
                context.setAttribute("curTrans", b);
                if (f != null) {
                    f.invoke(out);
                }
                total+=b.getTotal();
            }
            context.setAttribute("monthTotal", total);
            context.removeAttribute("curTrans");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopTransactionsTagHandler tag", ex);
        }
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
