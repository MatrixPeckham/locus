/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Comedy Option
 */
public class AdBean implements Serializable{
    private int id = -1;
    private int empId = -1;
    private String cat = "";
    private Date date = null;
    private String company = "";
    private String item = "";
    private String adContent = "";
    private int unitPrice = -1;
    private int available = -1;

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
}
