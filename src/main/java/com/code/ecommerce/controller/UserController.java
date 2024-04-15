package com.code.ecommerce.controller;

import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/users")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/current")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseMessage> getCurrentUser(@RequestHeader("Authorization") String token){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Get current user successful !!!", userService.getCurrentUser(token)) );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseMessage> getById(@PathVariable String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Get user successful !!!", userService.getUserById(id)) );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseMessage> updateUserInfo(@RequestBody Map<String, Object> fields, @PathVariable String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Update user information successful !!!", userService.updateUser(fields,id)) );
    }

    @PutMapping("/avatar/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<ResponseMessage> updateUserAvatar(@RequestParam MultipartFile file, @PathVariable String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Update user avatar successful !!!", userService.updateAvatar(file,id)) );
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage> getUserWithCondition(@RequestParam(name = "searchText") String searchText,
                                                                @RequestParam(name = "offset") Integer offset,
                                                                @RequestParam(name = "pageSize") Integer pageSize,
                                                                @RequestParam(name = "sortStr") String sortStr){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Get users successful !!!", userService.getUsers(searchText, offset, pageSize, sortStr)) );
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage> getAllUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Get all users successful !!!", userService.getAllUser()) );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Delete user successful !!!", userService.deleteUser(id)) );
    }
}
