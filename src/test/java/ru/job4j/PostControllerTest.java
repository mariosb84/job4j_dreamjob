package ru.job4j;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.controller.PostController;
import ru.job4j.model.City;
import ru.job4j.model.Post;
import ru.job4j.service.CityService;
import ru.job4j.service.PostService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    final static HttpSession SESSION = new HttpSession() {
        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(int i) {

        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String s) {
            return null;
        }

        @Override
        public Object getValue(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return new String[0];
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public void putValue(String s, Object o) {

        }

        @Override
        public void removeAttribute(String s) {

        }

        @Override
        public void removeValue(String s) {

        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean isNew() {
            return false;
        }
    };

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "New post desc"),
                new Post(2, "New post", "New post desc")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, SESSION);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Ignore
    @Test
    public void whenPostsCrash() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "New post desc"),
                new Post(2, "New post", "New post desc")
        );
        List<Post> wrongData = Arrays.asList(
                new Post(3, "New post", "New post desc"),
                new Post(4, "New post", "New post desc")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, SESSION);
        verify(model).addAttribute("posts", wrongData);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "New post desc", LocalDateTime.now(), true, new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormAddPost() {
        PostService postService = mock(PostService.class);
        Model model = mock(Model.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formAddPost(model, SESSION);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenFormUpdatePost() {
        Post input = new Post(1, "New post", "New post desc", LocalDateTime.now(), true, new City());
        PostService postService = mock(PostService.class);
        Model model = mock(Model.class);
        when(postService.findById(input.getId())).thenReturn(input);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, input.getId(), SESSION);
        verify(model).addAttribute("post", input);
        assertThat(page, is("updatePost"));
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "New post", "New post desc", LocalDateTime.now(), true, new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page, is("redirect:/posts"));
    }

}