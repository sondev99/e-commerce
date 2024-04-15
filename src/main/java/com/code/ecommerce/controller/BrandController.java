package com.code.ecommerce.controller;

import com.code.ecommerce.dto.request.BrandRequest;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.service.BrandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/brands")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ResponseMessage> create(@ModelAttribute BrandRequest brandRequest) throws JsonProcessingException {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Create brand successful !!",
                brandService.create(brandRequest)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find brand successful !!",
                brandService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getBrand(@RequestParam String searchText,
                                                         @RequestParam Integer offset,
                                                         @RequestParam Integer pageSize,
                                                         @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find brand successful !!",
                brandService.getBrands(searchText, offset, pageSize, sortStr)));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> getAll() {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Find all brand successful !!",
                brandService.getAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Delete brand successful !!",
                brandService.deleteById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestBody Map<String, Object> fields, @PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update brand successful !!",
                brandService.update(fields, id)));
    }

}
