package com.malvin.EComm.service.user;

import com.malvin.EComm.dto.UserDto;
import com.malvin.EComm.exception.AlreadyExistsException;
import com.malvin.EComm.exception.ResourceNotFoundException;
import com.malvin.EComm.model.User;
import com.malvin.EComm.repository.UserRepository;
import com.malvin.EComm.request.CreateUserRequest;
import com.malvin.EComm.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User with "+ userId+ " Not Found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user->!userRepository.existsByEmail(request.getEmail()))
        .map(req ->{
            User user = new User();
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(user);
        }).orElseThrow(()-> new AlreadyExistsException("Oops! User with email " +request.getEmail() +" Already Exists"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return  userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return  userRepository.save(existingUser);
        }).orElseThrow(()->new ResourceNotFoundException("User with "+ userId+ " Not Found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository:: delete,()->{
            throw  new ResourceNotFoundException("User with "+ userId+ " Not Found");
        });
    }
    @Override
    public UserDto convertToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
