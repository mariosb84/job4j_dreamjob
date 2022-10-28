package ru.job4j;

import org.junit.Test;
import ru.job4j.model.City;
import ru.job4j.model.Post;
import ru.job4j.store.PostDbStore;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "Java Job Description",
                LocalDateTime.now(), true, new City(1, "Moscow"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }
}
