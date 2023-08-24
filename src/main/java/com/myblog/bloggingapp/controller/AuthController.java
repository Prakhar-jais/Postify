package com.myblog.bloggingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblog.bloggingapp.Security.JwtTokenHelper;
import com.myblog.bloggingapp.payloads.JwtAuthRequest;
import com.myblog.bloggingapp.payloads.JwtAuthResponse;


@CrossOrigin(origins = "http://localhost:9090/api/auth/login")


@RestController
@RequestMapping("/api/auth/")

public class AuthController {
    
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager ;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request )throws Exception{
        
        
        this.doAuthenticate(request.getUsername(),request.getPassword());

        UserDetails  userDetail =this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetail);

        JwtAuthResponse res = new JwtAuthResponse();
        res.setToken(token);
        res.setUsername(userDetail.getUsername());
        

        return new ResponseEntity<JwtAuthResponse>(res, HttpStatus.OK);

    }

    private void doAuthenticate(String username,String password)throws Exception{


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
       
       try{

            this.authenticationManager.authenticate(authenticationToken);
    
        }
        catch(BadCredentialsException e){
            System.out.println("Invalid Username Or Password ");
            throw new Exception("Invalid UserDetails");
        }



        
    }
}
