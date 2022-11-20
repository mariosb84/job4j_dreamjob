package ru.job4j;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.controller.PostController;
import ru.job4j.model.City;
import ru.job4j.model.Post;
import ru.job4j.service.CityService;
import ru.job4j.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    @Test
    public void whenPosts() {
        HttpSession session = mock(HttpSession.class);
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
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Ignore
    @Test
    public void whenPostsCrash() {
        HttpSession session = mock(HttpSession.class);
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
        String page = postController.posts(model, session);
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
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        Model model = mock(Model.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formAddPost(model, session);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenFormUpdatePost() {
        HttpSession session = mock(HttpSession.class);
        Post input = new Post(1, "New post", "New post desc", LocalDateTime.now(), true, new City());
        PostService postService = mock(PostService.class);
        Model model = mock(Model.class);
        when(postService.findById(input.getId())).thenReturn(input);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, input.getId(), session);
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