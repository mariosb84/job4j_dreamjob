package ru.job4j.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Post;
import ru.job4j.store.PostStore;

@Controller
class PostController {

    private final PostStore postStore = PostStore.instOf();

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postStore.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String formAddPost(Model model) {
        model.addAttribute("post", new Post(0, "Заполните поле", "Заполните поле"));
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        postStore.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", postStore.findById(id));
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        postStore.update(post);
        return "redirect:/posts";
    }

}