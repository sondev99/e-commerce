package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.request.ProductRequest;
import com.code.ecommerce.dto.response.ImageDto;
import com.code.ecommerce.dto.response.ProductDto;
import com.code.ecommerce.dto.response.ReviewDto;
import com.code.ecommerce.entity.Image;
import com.code.ecommerce.entity.Product;
import com.code.ecommerce.entity.Review;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
    @Named("mappingImageUrls")
    default List<ImageDto> mappingImageUrls(List<Image> imageList) {
        return imageList.stream().map(gallery -> ImageDto.builder()
                .id(gallery.getId())
                .imageUrl(gallery.getImageUrl())
                .build()).toList();
    }

    @Named("mappingReviews")
    default List<ReviewDto> mappingReviews(List<Review> reviews) {
        if (CollectionUtils.isNotEmpty(reviews)){
            return reviews.stream().map(review -> ReviewDto.builder()
                    .id(review.getId())
                    .rate(review.getRate())
                    .userId(review.getUserId())
                    .content(review.getContent())
                    .createDate(review.getCreateDate())
                    .build()).toList();
        }
        return null;
    }


    @Mapping(target = "imageUrls", source = "imageUrls", qualifiedByName = "mappingImageUrls")
    List<ProductDto> toDto(List<Product> productList);

    @Mapping(target = "imageUrls", source = "imageUrls", qualifiedByName = "mappingImageUrls")
    @Mapping(target = "reviews", source = "reviews", qualifiedByName = "mappingReviews")
    ProductDto toDto(Product product);

    Product reqToEntity(ProductRequest productRequest);

}