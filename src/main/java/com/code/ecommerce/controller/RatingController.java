package com.code.ecommerce.controller;

import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.request.RatingRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/evaluates")
@RestController
@Slf4j
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createCategory(@Valid @RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Create evaluate successful !!",
                ratingService.create(ratingRequest)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(@PathVariable String id,
                                                  @RequestBody RatingRequest ratingRequest,
                                                  @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Update evaluate successful !!",
                ratingService.update(id, ratingRequest, token)));
    }

    @GetMapping()
    public ResponseEntity<ResponseMessage> getAll(@RequestParam(name = "offset") Integer offset,
                                                  @RequestParam(name = "pageSize") Integer pageSize,
                                                  @RequestParam(name = "user_id", required = false) String userId,
                                                  @RequestParam(name = "product_id") String productId) {
        PagingData evaluatesResponse;

        if (userId == null) {
            evaluatesResponse = ratingService.getByProduct(offset, pageSize, productId);
        } else {
            evaluatesResponse = ratingService.getByProductAndUser(offset, pageSize, productId, userId);
        }
        return ResponseEntity.ok().body(new ResponseMessage(
                ResponseStatus.OK,
                "Get evaluates successful !!",
                evaluatesResponse));
    }

}
