package com.code.ecommerce.service.impl;

import com.code.ecommerce.adapter.CloudinaryService;
import com.code.ecommerce.dto.request.BrandRequest;
import com.code.ecommerce.dto.response.BrandDto;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.entity.Brand;
import com.code.ecommerce.entity.Category;
import com.code.ecommerce.entity.Image;
import com.code.ecommerce.exceptions.MissingInputException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.BrandMapper;
import com.code.ecommerce.repository.BrandRepository;
import com.code.ecommerce.service.BrandService;
import com.code.ecommerce.utils.PaginationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;

    private final ObjectMapper objectMapper;

    private final BrandMapper brandMapper;

    @Override
    public String create(BrandRequest brandRequest) {

        Brand entity = brandMapper.reqToEntity(brandRequest);

        List<Image> images = brandRequest.getFiles().stream().map(file -> {
            Map result = cloudinaryService.uploadFile(file);
            String imageUrl = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");
            return Image.builder().publicId(publicId).thumbnailUrl(imageUrl).build();
        }).toList();
        entity.setImageUrls(images);

        return brandMapper.toDto(brandRepository.save(entity)).getId();
    }

    @Override
    public PagingData getBrands(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Brand> brandPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (StringUtils.isNotEmpty(searchText)) {
            brandPage = brandRepository.findAll(pageable);
        } else {
            brandPage = brandRepository.findByNameContainingIgnoreCase(searchText, pageable);
        }

        return PagingData.builder()
                .data(brandPage)
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(brandPage.getTotalElements())
                .build();
    }

    @Override
    public BrandDto findById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        return brandMapper.toDto(brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find brand with id " + id)));
    }

    @Override
    public BrandDto update(Map<String, Object> fields, String id) {
        Brand currentBrand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find brand with id" + id));

        fields.forEach((key, value) -> {
            // Tìm tên của trường dựa vào "key"
            Field field = ReflectionUtils.findField(Category.class, key);
            if (field == null) {
                throw new NullPointerException("Can't find any field");
            }
            // Set quyền truy cập vào biến kể cả nó là private
            field.setAccessible(true);
            // đặt giá trị cho một field cụ thể trong một đối tượng dựa trên tên của field đó
            ReflectionUtils.setField(field, currentBrand, value);
        });

        return brandMapper.toDto(brandRepository.save(currentBrand));
    }

    @Override
    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        brandRepository.deleteById(id);

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find brand with id " + id));
        brand.getImageUrls().forEach(image ->
                cloudinaryService.deleteFile(image.getPublicId())
        );
        return id;
    }

    @Override
    public List<BrandDto> getAll() {
        return brandMapper.toDto(brandRepository.findAll());
    }

}
