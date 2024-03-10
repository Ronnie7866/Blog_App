package com.amir.blog.Implementation;

import com.amir.blog.Entities.Category;
import com.amir.blog.Entities.Post;
import com.amir.blog.Entities.User;
import com.amir.blog.Exceptions.ResourceNotFoundException;
import com.amir.blog.Payloads.PostDTO;
import com.amir.blog.Repositories.CategoryRepo;
import com.amir.blog.Repositories.PostRepo;
import com.amir.blog.Repositories.UserRepo;
import com.amir.blog.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post save = postRepo.save(post);
        return modelMapper.map(save, PostDTO.class);
    }
    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", id));
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setImageName(post.getImageName());
        Post save = postRepo.save(post);
        return modelMapper.map(save, PostDTO.class);
    }
    @Override
    public void deletePost(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", id));
        postRepo.delete(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> all = postRepo.findAll();
        return all.stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", id));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> byCategory = postRepo.findByCategory(category);
        return byCategory.stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostByUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> byUser = postRepo.findByUser(user);
        return byUser.stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        return null;
    }
}
