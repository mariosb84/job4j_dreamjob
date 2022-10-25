package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Post;
import ru.job4j.store.PostDbStore;

import java.util.List;

@ThreadSafe
@Service
public class PostService {

        private final PostDbStore store;

    private final CityService cityService;

        public PostService(PostDbStore store, CityService cityService) {
            this.store = store;
            this.cityService = cityService;
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

        public List<Post> findAll() {
        List<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return posts;
    }

}
