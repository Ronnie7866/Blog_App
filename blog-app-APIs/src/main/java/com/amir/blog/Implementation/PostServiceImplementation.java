package com.amir.blog.Implementation;

import com.amir.blog.Entities.Category;
import com.amir.blog.Entities.Post;
import com.amir.blog.Entities.User;
import com.amir.blog.Exceptions.ResourceNotFoundException;
import com.amir.blog.Payloads.PostDTO;
import com.amir.blog.Payloads.PostResponse;
import com.amir.blog.Repositories.CategoryRepo;
import com.amir.blog.Repositories.PostRepo;
import com.amir.blog.Repositories.UserRepo;
import com.amir.blog.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else sort = Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> all = postRepo.findAll(pageable);// this all variable contain all the information
        List<Post> content = all.getContent(); // get post content

        PostResponse postResponse = new PostResponse();
        List<PostDTO> collect = content.stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());
        postResponse.setContent(collect);
        postResponse.setPageNumber(all.getNumber());
        postResponse.setIsLastPage(all.isLast());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalRecords(all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", id));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> allContent = all.getContent();

        PostResponse postResponse = new PostResponse();
        List<PostDTO> collect = allContent.stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());
        postResponse.setContent(collect);
        postResponse.setPageSize(all.getSize());
        postResponse.setPageNumber(all.getNumber());
        postResponse.setIsLastPage(all.isLast());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalRecords(all.getTotalElements());
        return postResponse;
    }

    @Override
    public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> byUser = postRepo.findByUser(user);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> allContent = all.getContent();


        PostResponse postResponse = new PostResponse();
        List<PostDTO> collect = allContent.stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());
        postResponse.setContent(collect);
        postResponse.setPageNumber(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setIsLastPage(all.isLast());
        postResponse.setTotalRecords(all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());
        return postResponse;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> byTitleContaining = postRepo.findByTitleContaining(keyword);
        return byTitleContaining.stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());
    }
}
