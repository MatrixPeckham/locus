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
public class CommentBean implements Serializable{
    private int id=-1;
    private Date date=null;
    private String content="";
    private int post=-1;
    private int author=-1;
    private String authorName="";
    private int likes=0;

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }
}
