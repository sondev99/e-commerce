package com.code.ecommerce.controller;

import com.code.ecommerce.dto.request.BannerRequest;
import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.repository.BannerRepository;
import com.code.ecommerce.service.BannerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/banners")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;
    private final BannerRepository bannerRepository;

    @PostMapping
    public ResponseEntity<ResponseMessage> create(@ModelAttribute BannerRequest bannerRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Insert banner successful !!",
                bannerService.create(bannerRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> findById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Get banner successful !!",
                bannerService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> findByCondition(@RequestParam String searchText,
                                                           @RequestParam Integer offset,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam String sortStr) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "get banner successful !!",
                bannerService.findByCondition(searchText, offset, pageSize, sortStr)));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseMessage> findAll() {
        return ResponseEntity.ok().body(new ResponseMessage(
            ResponseStatus.OK,
            "get all banner successful !!",
            bannerRepository.findAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Delete banner successful !!",
                bannerService.deleteById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@RequestParam(value = "image",
            required = false) MultipartFile file,
                                                  @RequestParam("data") String data,
                                                  @PathVariable(name = "id") String id)
            throws JsonProcessingException, IllegalAccessException {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update banner successful !!",
                bannerService.update(file, data, id)));
    }

}
