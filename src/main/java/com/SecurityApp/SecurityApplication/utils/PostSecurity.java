package com.SecurityApp.SecurityApplication.utils;

import com.SecurityApp.SecurityApplication.dto.PostDto;
import com.SecurityApp.SecurityApplication.entities.PostEntity;
import com.SecurityApp.SecurityApplication.entities.User;
import com.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;
     public boolean isOwnerOfPost(Long postId){
         User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//it indicates current user details
         PostDto post=postService.getPostById(postId);
         return post.getAuthor().getId().equals(user.getId());//matching the used id with existing id

     };
}
