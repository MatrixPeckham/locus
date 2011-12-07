/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.database.DBManager;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class DateSelectorTagHandler extends SimpleTagSupport {

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();

        try {

            out.print("");
            Date d1 = DBManager.get().getEaliestTransactionDate();
            Date d2 = new Date();

            Calendar c1 = Calendar.getInstance();
            c1.setTime(d1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(d2);


            out.print("Month: <select onchange=\"changeSalesReport()\" id=\"monthselect\">");

            for (int i = 0; i <= 11; i++) {
                out.print("<option value=\"" + (i+1) + "\""+(i==c2.get(Calendar.MONTH)?"selected=\"selected\"":"")+">" + (i+1) + "</option>");
            }

            out.print("</select>");

            
            out.print("Year: <select onchange=\"changeSalesReport()\" id=\"yearselect\">");

            for (int i = c1.get(Calendar.YEAR); i <= c2.get(Calendar.YEAR); i++) {
                out.print("<option value=\"" + i + "\""+(i==c2.get(Calendar.YEAR)?"selected=\"selected\"":"")+">" + i + "</option>");
            }

            out.print("</select>");



        } catch (java.io.IOException ex) {
            throw new JspException("Error in DateSelectorTagHandler tag", ex);
        }
    }
}
