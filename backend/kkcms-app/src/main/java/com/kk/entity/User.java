package com.kk.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by msi- on 2018/3/1.
 */
public class User implements Serializable {
    @Id
    private String name;
    private String avatarSrc;

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

    public User(String name, String avatarSrc) {
        this.name = name;
        this.avatarSrc = avatarSrc;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", avatarSrc='" + avatarSrc + '\'' +
                '}';
    }
}
