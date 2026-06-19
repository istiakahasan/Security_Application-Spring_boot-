package com.SecurityApp.SecurityApplication.services;

import com.SecurityApp.SecurityApplication.dto.LoginDto;
import com.SecurityApp.SecurityApplication.dto.SignUpDto;
import com.SecurityApp.SecurityApplication.dto.UserDto;
import com.SecurityApp.SecurityApplication.entities.User;
import com.SecurityApp.SecurityApplication.exception.ResourceNotFoundException;
import com.SecurityApp.SecurityApplication.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService implements UserDetailsService {

     private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user with email"+username+"not found"));
    }

    public  User getUserById(Long userID){
        return userRepository.findById(userID).orElseThrow(()->new ResourceNotFoundException("User with id"+userID+"not found"));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDto signUp(SignUpDto signUpDto) {
        //check whether is created or not
       Optional<User> user= userRepository.findByEmail(signUpDto.getEmail());
       if(user.isPresent()){
        throw new BadCredentialsException("user with email already exist"+signUpDto.getEmail());
       }
        //if user is not created then create the user
       User toBeCreated=mapper.map(signUpDto,User.class);
       toBeCreated.setPassword(passwordEncoder.encode(toBeCreated.getPassword()));//encode the pasword
       User savedUser=userRepository.save(toBeCreated);
       return mapper.map(savedUser,UserDto.class);


    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
