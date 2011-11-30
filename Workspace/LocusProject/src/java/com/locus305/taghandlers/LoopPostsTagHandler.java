/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.taghandlers;

import com.locus305.beans.PostBean;
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
public class LoopPostsTagHandler extends SimpleTagSupport {
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
            ArrayList<PostBean> posts = DBManager.get().getPosts(circle);
            int i = 0;
            for(PostBean post : posts){
                context.setAttribute("curPost", post);
                context.setAttribute("curIndext", i);
                if (f != null) {
                    f.invoke(out);
                }
                i++;
            }
            context.removeAttribute("curPost");
            context.removeAttribute("curIndex");
            
        } catch (java.io.IOException ex) {
            throw new JspException("Error in LoopPostsTagHandler tag", ex);
        }
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }
}
