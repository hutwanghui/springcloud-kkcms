package com.kk.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by msi- on 2018/3/1.
 */
public class Message implements Serializable {
    @Id
    private String id;
    private Boolean isRead;
    private String text;
    private Date sentAt;
    private Thread thread;
    private User author;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Message(String id, Boolean isRead, String text, Date sentAt) {
        this.id = id;
        this.isRead = isRead;
        this.text = text;
        this.sentAt = sentAt;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", isRead=" + isRead +
                ", text='" + text + '\'' +
                ", sentAt=" + sentAt +
                ", thread=" + thread +
                ", author=" + author +
                '}';
    }
}
