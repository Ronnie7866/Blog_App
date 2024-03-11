package com.amir.blog.Implementation;

import com.amir.blog.Entities.Comment;
import com.amir.blog.Entities.Post;
import com.amir.blog.Exceptions.ResourceNotFoundException;
import com.amir.blog.Payloads.CommentDTO;
import com.amir.blog.Payloads.PostDTO;
import com.amir.blog.Repositories.CommentRepo;
import com.amir.blog.Repositories.PostRepo;
import com.amir.blog.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService {
    
    @Autowired
    private PostRepo postRepo;
    
    @Autowired
    private CommentRepo commentRepo;
    
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
        commentRepo.delete(comment);
    }
}
