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
public class LoopItemTransactionsTagHandler extends SimpleTagSupport {

    private int item;

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

            ArrayList<TransactionBean> list = DBManager.get().getItemTransactions(item);
            int total = 0;

            for (TransactionBean b : list) {
                context.setAttribute("curTrans", b);
                if (f != null) {
                    f.invoke(out);
                }
                total+=b.getTotal();
            }
            context.removeAttribute("curTrans");
            context.setAttribute("itemTotal", total);

        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopItemTransactionsTagHandler tag", ex);
        }
    }

    public void setItem(int item) {
        this.item = item;
    }
}
