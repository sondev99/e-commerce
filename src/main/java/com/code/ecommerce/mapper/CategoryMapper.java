package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.request.CategoryRequest;
import com.code.ecommerce.dto.response.CategoryDto;
import com.code.ecommerce.dto.response.ImageDto;
import com.code.ecommerce.entity.Category;
import com.code.ecommerce.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDto, Category>{

    Category reqToEntity(CategoryRequest categoryRequest);

    @Named("mappingImageUrl")
    default ImageDto mappingImageUrls(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .thumbnailUrl(image.getThumbnailUrl())
                .build();
    }

    @Named("mappingIconUrl")
    default ImageDto mappingIconUrl(Image image) {
        return  ImageDto.builder()
                .id(image.getId())
                .thumbnailUrl(image.getThumbnailUrl())
                .build();
    }

    @Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mappingImageUrl")
    @Mapping(target = "iconUrl", source = "iconUrl", qualifiedByName = "mappingIconUrl")
    List<CategoryDto> toDto(List<Category> categoryList);
}