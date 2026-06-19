package com.SecurityApp.SecurityApplication.services;


import com.SecurityApp.SecurityApplication.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService{
    public  List<PostDto> getAllPosts();

    public PostDto getPostById(Long postId);

    public PostDto createNewPost(PostDto inputPost);

    public PostDto updatePost(PostDto inputPost, Long postId);
}
