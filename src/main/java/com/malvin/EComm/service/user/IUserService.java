package com.malvin.EComm.service.user;

import com.malvin.EComm.dto.UserDto;
import com.malvin.EComm.model.User;
import com.malvin.EComm.request.CreateUserRequest;
import com.malvin.EComm.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request , Long userId);
    void deleteUser(Long userId);

    UserDto convertToDto(User user);

    User getAuthenticatedUser();
}
