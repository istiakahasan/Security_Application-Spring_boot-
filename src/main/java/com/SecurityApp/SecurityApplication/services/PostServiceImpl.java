package com.SecurityApp.SecurityApplication.services;

import com.SecurityApp.SecurityApplication.dto.PostDto;
import com.SecurityApp.SecurityApplication.entities.PostEntity;
import com.SecurityApp.SecurityApplication.entities.User;
import com.SecurityApp.SecurityApplication.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public  class PostServiceImpl {

        private final ModelMapper mapper;
        private final PostRepository postRepository;



        public List<PostDto> getAllPosts() {

            return postRepository.findAll().stream().map(postEntity -> mapper.map(postEntity,PostDto.class)).collect(Collectors.toList());
        }



        public PostDto createNewPost(PostDto inputPost){

            User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            PostEntity postEntity=mapper.map(inputPost, PostEntity.class);
            postEntity.setAuthor(user);
            return mapper.map(postRepository.save(postEntity),PostDto.class);
        }



    public PostDto getPostById(Long postId){
//      User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//      log.info("user{}",user);

    PostEntity postEntity=postRepository.findById(postId).orElseThrow(()->new ResolutionException("Post not found with id"+postId));
    return mapper.map(postEntity,PostDto.class);
    }

    public PostDto updatePost(PostDto inputPost,Long postId){
        PostEntity oldPost=postRepository.findById(postId).orElseThrow(()->new ResolutionException(String.valueOf(postId)));
        inputPost.setId(postId);
        mapper.map(inputPost,oldPost);
        return mapper.map(postRepository.save(oldPost),PostDto.class);
    }


}
