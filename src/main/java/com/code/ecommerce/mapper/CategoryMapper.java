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

    @Named("mappingImageUrls")
    default List<ImageDto> mappingImageUrls(List<Image> imageList) {
        return imageList.stream().map(gallery -> ImageDto.builder()
                .id(gallery.getId())
                .imageUrl(gallery.getImageUrl())
                .build()).toList();
    }

    @Named("mappingIconUrl")
    default ImageDto mappingIconUrl(Image image) {
        return  ImageDto.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .build();
    }

    @Mapping(target = "imageUrls", source = "imageUrls", qualifiedByName = "mappingImageUrls")
    @Mapping(target = "iconUrl", source = "iconUrl", qualifiedByName = "mappingIconUrl")
    List<CategoryDto> toDto(List<Category> categoryList);

}