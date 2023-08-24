package com.myblog.bloggingapp.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myblog.bloggingapp.exceptions.*;
import com.myblog.bloggingapp.config.AppConstants;
import com.myblog.bloggingapp.entities.Role;
import com.myblog.bloggingapp.entities.User;
import com.myblog.bloggingapp.payloads.UserDto;
import com.myblog.bloggingapp.repositories.RoleRepo;
import com.myblog.bloggingapp.repositories.UserRepo;
import com.myblog.bloggingapp.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user  = this.modelMapper.map(userDto,User.class);
        // password encoded for new user 
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        // let's define its roles
        
        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);
        return this.modelMapper.map(newUser,UserDto.class);
    }
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        // TODO Auto-generated method stub
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        // TODO Auto-generated method stub
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        this.userRepo.delete(user);

        
    }

    @Override
    public List<UserDto> getAllUsers() {
        // TODO Auto-generated method stub
        List<UserDto> userDtos = this.userRepo.findAll().stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        // TODO Auto-generated method stub
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        return this.userToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        // TODO Auto-generated method stub
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1 ; 
    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);


        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setAbout(userDto.getAbout());
        // user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setAbout(user.getAbout());
        // userDto.setPassword(user.getPassword());
        return userDto;

    }
    
}
