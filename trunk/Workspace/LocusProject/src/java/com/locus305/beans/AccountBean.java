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
public class AccountBean implements Serializable {
    private String ccn="";
    private int accnum=-1;

    public int getAccnum() {
        return accnum;
    }

    public void setAccnum(int accnum) {
        this.accnum = accnum;
    }

    public String getCcn() {
        return ccn;
    }

    public void setCcn(String ccn) {
        this.ccn = ccn;
    }
}
