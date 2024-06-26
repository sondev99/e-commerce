package com.code.ecommerce.controller;

import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/galleries")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    public ResponseEntity<ResponseMessage> create(@RequestParam("image") MultipartFile file,
                                                  @RequestParam("data") String data) throws
            JsonProcessingException {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Insert gallery successful !!",
                imageService.create(file, data)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Get gallery successful !!",
                imageService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> findByCondition(@RequestParam String searchText,
                                                           @RequestParam Integer offset,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "get galleries successful !!",
                imageService.findByCondition(searchText, offset, pageSize, sortStr)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Delete gallery successful !!",
                imageService.deleteById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestParam(value = "image",
            required = false) MultipartFile file,
                                                  @RequestParam("data") String data,
                                                  @PathVariable(name = "id") String id)
            throws JsonProcessingException, IllegalAccessException {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update gallery successful !!",
                imageService.update(file, data, id)));
    }

}
