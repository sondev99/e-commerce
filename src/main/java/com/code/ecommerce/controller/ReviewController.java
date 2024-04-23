package com.code.ecommerce.controller;

import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.request.ReviewRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reviews")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createCategory(@Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Create evaluate successful !!",
            reviewService.create(reviewRequest)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@PathVariable String id,
                                                  @RequestBody ReviewRequest reviewRequest,
                                                  @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update evaluate successful !!",
            reviewService.update(id, reviewRequest, token)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getAll(@RequestParam(name = "offset") Integer offset,
                                                  @RequestParam(name = "page_size") Integer pageSize,
                                                  @RequestParam(name = "user_id", required = false) String userId,
                                                  @RequestParam(name = "product_id") String productId) {
        PagingData evaluatesResponse;

        if (userId == null) {
            evaluatesResponse = reviewService.getByProduct(offset, pageSize, productId);
        } else {
            evaluatesResponse = reviewService.getByProductAndUser(offset, pageSize, productId, userId);
        }
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Get evaluates successful !!",
                evaluatesResponse));
    }

}
