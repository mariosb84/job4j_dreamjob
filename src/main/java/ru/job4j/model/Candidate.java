package ru.job4j.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String desc;
    private LocalDateTime created;

    public Candidate() {
    }

    public Candidate(int id, String name, String desc, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return getId() == candidate.getId()
                && getName().equals(candidate.getName())
                && getDesc().equals(candidate.getDesc())
                && getCreated().equals(candidate.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDesc(), getCreated());
    }

}
