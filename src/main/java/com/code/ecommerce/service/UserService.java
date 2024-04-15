package com.code.ecommerce.service;

import com.code.ecommerce.dto.request.UserRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.UserDto;
import com.code.ecommerce.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(UserRequest userRequest);

    UserDto getCurrentUser(String token);

    List<UserDto> getAllUser();

    PagingData getUsers(String searchText, Integer offset, Integer pageSize, String sortStr);

    String updateAvatar(MultipartFile file, String id);

    UserDto getUserById(String id);

    UserDto updateUser(Map<String, Object> fields, String id);

    String deleteUser(String id);


}
