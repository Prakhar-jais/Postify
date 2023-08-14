package com.myblog.bloggingapp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myblog.bloggingapp.entities.User;
import com.myblog.bloggingapp.exceptions.ResourceNotFoundException;
import com.myblog.bloggingapp.repositories.UserRepo;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo ;


    // yaha UserDetail return krna hai and jo user class hai wo UserDetailService ki methods ko implement kr rha hai toh isliye i can return user

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load usr from db by username

        User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "Email : "+ username,0));
        return user;
    }
    
}
