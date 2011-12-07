/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.beans;

import java.io.Serializable;

/**
 *
 * @author Owner
 */
public class BestSellerBean implements Serializable{
    private AdBean b;
    private int numsold;

    public AdBean getB() {
        return b;
    }

    public void setB(AdBean b) {
        this.b = b;
    }

    public int getNumsold() {
        return numsold;
    }

    public void setNumsold(int numsold) {
        this.numsold = numsold;
    }
}
