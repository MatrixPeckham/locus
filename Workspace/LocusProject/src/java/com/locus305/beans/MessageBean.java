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
public class MessageBean implements Serializable{
    final int MAX_CONTENT_LENGTH = 1000;
    final int MAX_SUBJECT_LENGTH = 50;
    private int id=-1;
    private Date date=null;
    private String subject="";
    private String content="";
    private int sender=-1;
    private int receiver=-1;
    private String sendername="";
    private String receivername="";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(content.length() > MAX_CONTENT_LENGTH){
            content = content.substring(0, MAX_CONTENT_LENGTH -1);
        }
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if(subject.length() > MAX_SUBJECT_LENGTH){
            subject = subject.substring(0, MAX_SUBJECT_LENGTH -1);
        }
        this.subject = subject;
    }
    
}
