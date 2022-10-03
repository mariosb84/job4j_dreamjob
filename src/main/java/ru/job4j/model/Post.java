package ru.job4j.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post implements Serializable {

    private int id;
    private String name;
    private String description;
    private LocalDateTime created = LocalDateTime.now();
    private boolean visible;
    private City city;

    private Post() {
    }

    public Post(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Post(int id, String name, String description, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        Post post = (Post) o;
        return getId() == post.getId() && getName().equals(post.getName())
                && getDescription().equals(post.getDescription())
                && getCreated().equals(post.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getCreated());
    }

}