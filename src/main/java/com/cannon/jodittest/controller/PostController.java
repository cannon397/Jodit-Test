package com.cannon.jodittest.controller;

import com.cannon.jodittest.model.Post;
import com.cannon.jodittest.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {



    @Autowired
    private PostRepository postRepository; // 게시글 저장소
    // 게시글 작성 화면
    @GetMapping
    public String showForm(Model model) {
        List<Post> posts = postRepository.findAll(); // 모든 게시글 조회
        model.addAttribute("posts", posts);
        return "posts"; // posts.jsp로 리다이렉트
    }

    // 게시글 저장
    @PostMapping
    public String createPost(@RequestParam("content") String content) {
        Post post = new Post();
        post.setContent(content); // 게시글 내용 저장
        postRepository.save(post); // 데이터베이스에 저장
        return "redirect:/posts"; // 게시글 목록으로 리다이렉트
    }
}
