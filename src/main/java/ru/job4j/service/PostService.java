package ru.job4j.service;

import ru.job4j.model.Post;
import ru.job4j.store.PostStore;

import java.util.Collection;

public class PostService {

    PostStore store = PostStore.instOf();

    private static final PostService POST_SERVICE = new PostService();

    private PostService() {

    }

    public static PostService instOf() {
        return POST_SERVICE;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }

}
