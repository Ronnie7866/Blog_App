package com.amir.blog.Services;

import com.amir.blog.Payloads.CommentDTO;
import com.amir.blog.Payloads.UserDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId);

    void deleteComment(Integer commentId);
}
