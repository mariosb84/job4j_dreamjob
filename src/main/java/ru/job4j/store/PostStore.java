package ru.job4j.store;

import ru.job4j.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private int id;

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        add(new Post(1, "Junior Java Job", "Some Text For Junior Java Job", LocalDateTime.now()));
        add(new Post(2, "Middle Java Job", "Some Text For Middle  Java Job", LocalDateTime.now()));
        add(new Post(3, "Senior Java Job", "Some Text For Senior  Java Job", LocalDateTime.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        posts.put(id++, post);
        id += id;
    }
}