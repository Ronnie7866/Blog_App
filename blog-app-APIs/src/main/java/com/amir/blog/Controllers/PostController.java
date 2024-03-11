package com.amir.blog.Controllers;

import com.amir.blog.Configurations.AppConstants;
import com.amir.blog.Payloads.ApiResponse;
import com.amir.blog.Payloads.CategoryDTO;
import com.amir.blog.Payloads.PostDTO;
import com.amir.blog.Payloads.PostResponse;
import com.amir.blog.Repositories.PostRepo;
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
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
                                                      @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        PostResponse postByUser = postService.getPostByUser(userId, pageNumber, pageSize);
        return ResponseEntity.ok(postByUser);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                          @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        PostResponse postByCategory = postService.getPostByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(postByCategory, HttpStatus.OK);
    }

    @GetMapping("posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) { // we use requestParam to extract parameter from url url se parameter nikalne k liye

        PostResponse allPosts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
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

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords) {
        List<PostDTO> postDTOS = postService.searchPosts(keywords);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

}
