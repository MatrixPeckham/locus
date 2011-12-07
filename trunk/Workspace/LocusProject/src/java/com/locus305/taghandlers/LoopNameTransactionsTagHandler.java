/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.TransactionBean;
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
public class LoopNameTransactionsTagHandler extends SimpleTagSupport {
    private int name;

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

            ArrayList<TransactionBean> list = DBManager.get().getNameTransactions(name);
            int total = 0;

            for (TransactionBean b : list) {
                context.setAttribute("curTrans", b);
                if (f != null) {
                    f.invoke(out);
                }
                total+=b.getTotal();
            }
            context.removeAttribute("curTrans");
            context.setAttribute("nameTotal", total);

        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopItemTransactionsTagHandler tag", ex);
        }
    }

    public void setName(int name) {
        this.name = name;
    }
}
