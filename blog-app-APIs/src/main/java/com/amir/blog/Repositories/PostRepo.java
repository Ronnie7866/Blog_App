package com.amir.blog.Repositories;

import com.amir.blog.Entities.Category;
import com.amir.blog.Entities.Post;
import com.amir.blog.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
}
