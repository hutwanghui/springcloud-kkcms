package com.kk.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by msi- on 2018/3/1.
 */
public class Thread implements Serializable {

    @Id
    private String id;
    private String name;
    private String avatarSrc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }

    public Thread() {
    }

    public Thread(String id, String name, String avatarSrc) {
        this.id = id;
        this.name = name;
        this.avatarSrc = avatarSrc;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatarSrc='" + avatarSrc + '\'' +
                '}';
    }
}
