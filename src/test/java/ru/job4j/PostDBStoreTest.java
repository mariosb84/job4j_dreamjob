package ru.job4j;

import org.junit.Test;
import ru.job4j.model.City;
import ru.job4j.model.Post;
import ru.job4j.store.PostDbStore;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(1, "Java Job", "Java Job Description",
                LocalDateTime.now(), true, new City(1, "Moscow"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenFindAllPosts() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(1, "Post1", "Post1 desc", LocalDateTime.now(), true, new City(1, ""));
        Post post2 = new Post(2, "Post2", "Post2 desc", LocalDateTime.now(), true, new City(2, ""));
        Post post3 = new Post(3, "Post3", "Post3 desc", LocalDateTime.now(), true, new City(3, ""));
        store.add(post);
        store.add(post2);
        store.add(post3);
        List<Post> postInDb = store.findAll();
        assertThat(postInDb.contains(post3), is(true));
    }

    @Test
    public void whenUpdatePost() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(1, "Post4", "Post4 desc", LocalDateTime.now(), true, new City(1, ""));
        Post post2 = new Post(1, "Post5", "Post5 desc", LocalDateTime.now(), true, new City(2, ""));
        store.add(post);
        store.update(post2);
        Post postInDb = store.findById(post2.getId());
        assertThat(postInDb.getName(), is(post2.getName()));
    }

    @Test
    public void whenFindByIdPost() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(1, "Post6", "Post6 desc", LocalDateTime.now(), true, new City(1, ""));
        Post post2 = new Post(2, "Post7", "Post7 desc", LocalDateTime.now(), true, new City(2, ""));
        store.add(post);
        store.add(post2);
        Post postInDb = store.findById(post2.getId());
        assertThat(postInDb.getName(), is(post2.getName()));
    }

    @Test
    public void whenDeletePost() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(1, "Post8", "Post8 desc", LocalDateTime.now(), true, new City(1, ""));
        store.add(post);
        store.delete(post);
        assertThat(store.findAll().contains(post), is(false));
    }
}
