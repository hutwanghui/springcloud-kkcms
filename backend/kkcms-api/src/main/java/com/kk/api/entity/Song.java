package com.kk.api.entity;

import javax.persistence.*;

public class Song {
    @Id
    private Integer id;

    private String name;

    private String author;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return author
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

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (getId() != null ? !getId().equals(song.getId()) : song.getId() != null) return false;
        if (getName() != null ? !getName().equals(song.getName()) : song.getName() != null) return false;
        return getAuthor() != null ? getAuthor().equals(song.getAuthor()) : song.getAuthor() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        return result;
    }

    public Song(Integer id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Song(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Song() {
    }
}