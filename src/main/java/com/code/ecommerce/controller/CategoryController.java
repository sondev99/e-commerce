package com.code.ecommerce.controller;

import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.request.CategoryRequest;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/categories")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createCategory(@ModelAttribute CategoryRequest categoryRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Create category successful !!",
                categoryService.createCategory(categoryRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find category successful !!",
                categoryService.findCategoryById(id)));
    }

    @GetMapping("/base-categories")
    public ResponseEntity<ResponseMessage> getSubCategory() {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Get base categories successful !!",
                categoryService.getBaseCategories()));
    }

    @GetMapping
    public ResponseEntity<ResponseMessage> getCategories(@RequestParam(name = "searchText") String searchText,
                                                         @RequestParam(name = "offset") Integer offset,
                                                         @RequestParam(name = "pageSize") Integer pageSize,
                                                         @RequestParam(name = "sortStr") String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find category successful !!",
                categoryService.getCategories(searchText, offset, pageSize, sortStr)));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> getAll() {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find all category successful !!",
                categoryService.getAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Delete category successful !!",
                categoryService.deleteCategoryById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestParam(value = "image",
            required = false) MultipartFile file,
                                                  @RequestParam("icon") MultipartFile iconFile,
                                                  @RequestParam("data") String data,
                                                  @PathVariable(name = "id") String id)
            throws JsonProcessingException, IllegalAccessException {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update category successful !!",
                categoryService.updateCategory(file, iconFile, data, id)));
    }

}
