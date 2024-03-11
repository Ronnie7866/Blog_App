package com.amir.blog.Services;

import com.amir.blog.Payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId);

    void deleteComment(Integer commentId);
}
