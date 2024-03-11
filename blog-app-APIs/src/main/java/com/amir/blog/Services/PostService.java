package com.amir.blog.Services;

import com.amir.blog.Entities.Post;
import com.amir.blog.Payloads.PostDTO;
import com.amir.blog.Payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    PostDTO updatePost(PostDTO postDTO, Integer id);

    void deletePost(Integer id);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String SortDirection);

    PostDTO getPostById(Integer id);

    PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

    PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

    List<PostDTO> searchPosts(String keyword);
}
