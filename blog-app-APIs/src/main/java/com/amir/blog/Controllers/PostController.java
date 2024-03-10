package com.amir.blog.Controllers;

import com.amir.blog.Payloads.ApiResponse;
import com.amir.blog.Payloads.CategoryDTO;
import com.amir.blog.Payloads.PostDTO;
import com.amir.blog.Services.CategoryService;
import com.amir.blog.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDTO post = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {
        List<PostDTO> postByUser = postService.getPostByUser(userId);
        return ResponseEntity.ok(postByUser);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> postByCategory = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok((postByCategory));
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> allPosts = postService.getAllPosts();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        PostDTO postById = postService.getPostById(id);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post is successfully deleted", true), HttpStatus.OK);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer id) {
        PostDTO postDTO1 = postService.updatePost(postDTO, id);
        return new ResponseEntity<>(postDTO1, HttpStatus.OK);
    }
}
