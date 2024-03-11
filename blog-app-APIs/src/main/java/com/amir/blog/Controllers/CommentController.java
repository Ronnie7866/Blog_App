package com.amir.blog.Controllers;

import com.amir.blog.Entities.Comment;
import com.amir.blog.Payloads.ApiResponse;
import com.amir.blog.Payloads.CommentDTO;
import com.amir.blog.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable Integer postId) {
        CommentDTO comment = commentService.createComment(commentDTO, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
    }
}
