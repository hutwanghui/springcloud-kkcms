package com.kk.entity;

import java.util.Date;
import javax.persistence.*;

public class Message {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "THREAD")
    private String thread;

    @Column(name = "SENDTIME")
    private Date sendtime;

    /**
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return AUTHOR
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return TEXT
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return THREAD
     */
    public String getThread() {
        return thread;
    }

    /**
     * @param thread
     */
    public void setThread(String thread) {
        this.thread = thread;
    }

    /**
     * @return SENDTIME
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * @param sendtime
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                ", thread='" + thread + '\'' +
                ", sendtime=" + sendtime +
                '}';
    }
}