/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.TransactionBean;
import com.locus305.database.DBManager;
import java.util.ArrayList;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Owner
 */
public class LoopAccountTransactionsTagHandler extends SimpleTagSupport {
    private String account;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            
            int acct = Integer.parseInt(account);
            ArrayList<TransactionBean> list = DBManager.get().getAccountTransactions(acct);
            
            JspFragment f = getJspBody();
            for(TransactionBean b : list){
                getJspContext().setAttribute("curTrans", b);
                if (f != null) {
                    f.invoke(out);
                }
            }
            getJspContext().removeAttribute("curTrans");

        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopAccountTransactionsTagHandler tag", ex);
        }
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
