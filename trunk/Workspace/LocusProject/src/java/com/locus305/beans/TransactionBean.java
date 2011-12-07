/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class TransactionBean implements Serializable{
    private AdBean b=null;
    private Date date = null;
    private int numUnits=-1;
    private int total=0;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void setNumUnits(int numUnits) {
        this.numUnits = numUnits;
    }

    public AdBean getB() {
        return b;
    }

    public void setB(AdBean b) {
        this.b = b;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
