/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locus305.beans;

import com.locus305.database.DBManager;
import java.io.Serializable;

/**
 *
 * @author Owner
 */
public class UserBean implements Serializable {
    private String username="";
    private int userid=-1;
    private String fname="";
    private String lname="";
    private String addr ="";
    private String city="";
    private String state="";
    private int zip = -1;
    private String phone="";
    private String preferences;
    private int type=0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    public UserBean() {
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
    public static boolean doesLikePost(UserBean usr, int post){
        if(usr==null) return false;
        return DBManager.get().userLikesPost(usr.userid,post);
    }
    public static boolean doesLikeComment(UserBean usr, int comment){
        if(usr==null) return false;
        return DBManager.get().userLikesComment(usr.userid,comment);
    }
}
