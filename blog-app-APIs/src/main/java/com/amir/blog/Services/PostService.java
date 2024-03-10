package com.amir.blog.Services;

import com.amir.blog.Entities.Post;
import com.amir.blog.Payloads.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    PostDTO updatePost(PostDTO postDTO, Integer id);

    void deletePost(Integer id);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Integer id);

    List<PostDTO> getPostByCategory(Integer categoryId);

    List<PostDTO> getPostByUser(Integer userId);

    List<PostDTO> searchPosts(String keyword);
}