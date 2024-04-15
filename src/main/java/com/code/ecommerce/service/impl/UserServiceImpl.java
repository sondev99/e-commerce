package com.code.ecommerce.service.impl;

import com.code.ecommerce.adapter.CloudinaryService;
import com.code.ecommerce.dto.request.UserRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.UserDto;
import com.code.ecommerce.entity.Role;
import com.code.ecommerce.entity.User;
import com.code.ecommerce.exceptions.MissingInputException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.UserMapper;
import com.code.ecommerce.repository.UserRepository;
import com.code.ecommerce.service.UserService;
import com.code.ecommerce.utils.JwtUtils;
import com.code.ecommerce.utils.PaginationUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    @Value("${secretPsw}")
    String secretPsw;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    @Override
    public User createUser(UserRequest userRequest) {
        User user = userMapper.reqUserToEntity(userRequest);
        user.setRole(Role.USER);
        user.setEnabled(Boolean.TRUE);
        user.setPassword(passwordEncoder.encode(secretPsw));
        return userRepository.save(user);
    }

    @Override
    public UserDto getCurrentUser(String token) {
        Claims claims = JwtUtils.parseClaims(JwtUtils.getTokenFromBearer(token));
        String userId = (String) claims.get("userId");
        if (userId.isEmpty()) {
            throw new MissingInputException("require userId to get current user");
        }
        return userMapper.toDto(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user with id" + userId)));
    }

    @Override
    public List<UserDto> getAllUser() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    public PagingData getUsers(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<User> userPageEntities;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (searchText.isEmpty()) {
            userPageEntities = userRepository.findAll(pageable);
        } else {
            userPageEntities = userRepository.findByEmailContainingIgnoreCase(searchText, pageable);
        }

        Page<UserDto> userPageDto = userPageEntities.map(userMapper::toDto);

        return PagingData.builder()
                .data(userPageDto)
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(userPageDto.getTotalElements())
                .build();
    }

    @Override
    public String updateAvatar(MultipartFile file, String id) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find user with id" + id));

        if (StringUtils.isNotEmpty(currentUser.getAvatarUrl())) {
            cloudinaryService.deleteFile(currentUser.getPublicId());
        }

        Map result = cloudinaryService.uploadFile(file);
        String avatarUrl = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");

        currentUser.setAvatarUrl(avatarUrl);
        currentUser.setPublicId(publicId);

        userRepository.save(currentUser);

        return currentUser.getAvatarUrl();
    }

    @Override
    public UserDto getUserById(String id) {
        return userMapper.toDto(
                userRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find user with id" + id)));
    }

    @Override
    public UserDto updateUser(Map<String, Object> fields, String id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Can't find user with id" + id));

        fields.forEach((key, value) -> {
            // Tìm tên của trường dựa vào "key"
            Field field = ReflectionUtils.findField(User.class, key);
            if (field == null) {
                throw new NullPointerException("Can't find any field");
            }
            // Set quyền truy cập vào biến kể cả nó là private
            field.setAccessible(true);
            // đặt giá trị cho một field cụ thể trong một đối tượng dựa trên tên của field đó
            ReflectionUtils.setField(field, existingUser, value);
        });
        userRepository.save(existingUser);
        return userMapper.toDto(existingUser);
    }

    @Override
    public String deleteUser(String id) {
        return null;
    }



}
