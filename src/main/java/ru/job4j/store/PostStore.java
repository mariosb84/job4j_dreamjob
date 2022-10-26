package ru.job4j.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.City;
import ru.job4j.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {

    private final AtomicInteger id = new AtomicInteger();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        add(new Post(1, "Junior Java Job", "Some Text For Junior Java Job", LocalDateTime.now(), true, new City(1, "Москва")));
        add(new Post(2, "Middle Java Job", "Some Text For Middle  Java Job", LocalDateTime.now(), true, new City(2, "СПб")));
        add(new Post(3, "Senior Java Job", "Some Text For Senior  Java Job", LocalDateTime.now(), true, new City(3, "Екб")));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(id.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }

}