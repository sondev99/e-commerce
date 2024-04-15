package com.code.ecommerce.service.impl;

import com.code.ecommerce.adapter.CloudinaryService;
import com.code.ecommerce.dto.request.CategoryRequest;
import com.code.ecommerce.dto.response.CategoryDto;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.entity.Category;
import com.code.ecommerce.entity.Image;
import com.code.ecommerce.exceptions.MissingInputException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.CategoryMapper;
import com.code.ecommerce.repository.CategoryRepository;
import com.code.ecommerce.service.CategoryService;
import com.code.ecommerce.service.ImageService;
import com.code.ecommerce.utils.PaginationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final ImageService imageService;
    private final ObjectMapper objectMapper;

    private final CategoryMapper categoryMapper;

    public String createCategory(CategoryRequest categoryRequest) {
        Category entity = categoryMapper.reqToEntity(categoryRequest);
        if (!categoryRequest.getParentCatId().isEmpty()) {
            Optional<Category> parentCategoryOptional = categoryRepository.findById(categoryRequest.getParentCatId());

            entity.setParent(parentCategoryOptional.get());

        }

        Image imageUrl = saveImageToCloud(categoryRequest.getImageFile());
        entity.setImageUrl(imageUrl);

        Image iconUrl = saveImageToCloud(categoryRequest.getIconFile());
        entity.setIconUrl(iconUrl);
        return categoryRepository.save(entity).getId();
    }

    private Image saveImageToCloud(MultipartFile file) {
        Map result = cloudinaryService.uploadFile(file);
        String imageUrl = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");
        return Image.builder().publicId(publicId).thumbnailUrl(imageUrl).build();
    }

    public PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Category> categoryPageEntity;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (searchText.isEmpty()) {
            categoryPageEntity = categoryRepository.findAll(pageable);
        } else {
            categoryPageEntity = categoryRepository.findByNameContainingIgnoreCase(searchText, pageable);
        }

        Page<CategoryDto> categoryDTOPage = categoryPageEntity.map(categoryMapper::toDto);

        return PagingData.builder()
                .data(categoryDTOPage)
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(categoryDTOPage.getTotalElements())
                .build();
    }

    public CategoryDto findCategoryById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find category with id " + id)));
    }

    @Override
    public List<CategoryDto> getBaseCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<Category> baseCategoryList = categoryList.stream().filter(category -> category.getParent() == null)
                .toList();
        return categoryMapper.toDto(baseCategoryList);
    }

    public String deleteCategoryById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");

        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find category with id " + id));

        categoryRepository.deleteById(id);


        cloudinaryService.deleteFile(category.getImageUrl().getPublicId());


        cloudinaryService.deleteFile((category.getIconUrl().getPublicId()));
        return id;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryMapper.toDto(categoryList);
    }

    @Transactional
    public CategoryDto updateCategory(MultipartFile file, MultipartFile iconFile, String data, String id)
            throws JsonProcessingException {

        Category currentCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find category with id " + id));

        CategoryRequest categoryRequest = objectMapper.readValue(data, CategoryRequest.class);

        Category currentCategoryParent = currentCategory.getParent();
        if (!StringUtils.equals(categoryRequest.getParentCatId(), currentCategoryParent.getId())) {
            currentCategoryParent = categoryRepository.findById(categoryRequest.getParentCatId()).orElseThrow(
                    () -> new NotFoundException("Can't find category with id" + categoryRequest.getParentCatId()));
        }

        Image image;
        Image icon;
        if (file != null) {
            Image currentImage = currentCategory.getImageUrl();
            imageService.deleteById(currentImage.getId());
            image = saveImageToCloud(file);
        } else {
            image = currentCategory.getImageUrl();
        }

        if (iconFile != null) {
            Image currentIcon = currentCategory.getIconUrl();
            imageService.deleteById(currentIcon.getId());
            icon = saveImageToCloud(iconFile);
        } else {
            icon = currentCategory.getIconUrl();
        }

        Category newCategory = Category.builder()
                .id(currentCategory.getId())
                .name(categoryRequest.getName())
                .imageUrl(image)
                .parent(currentCategoryParent)
                .iconUrl(icon)
                .build();

        return categoryMapper.toDto(categoryRepository.save(newCategory));

    }

}
