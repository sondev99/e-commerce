package com.code.ecommerce.service.impl;

import com.code.ecommerce.adapter.CloudinaryService;
import com.code.ecommerce.dto.request.ImageRequest;
import com.code.ecommerce.dto.response.CategoryDto;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.entity.Image;
import com.code.ecommerce.exceptions.MissingInputException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.ImageMapper;
import com.code.ecommerce.repository.ImageRepository;
import com.code.ecommerce.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;

    private final ImageMapper imageMapper;

    @Override
    public Image create(MultipartFile file, String data) throws JsonProcessingException {
        ImageRequest imageRequest = objectMapper.readValue(data, ImageRequest.class);
        Image entity = imageMapper.reqToEntity(imageRequest);
        return null;
    }

    @Override
    public CategoryDto findById(String id) {

        return null;
    }

    @Override
    public PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr) {
        return null;
    }

    @Override
    public CategoryDto update(MultipartFile file, String data, String id) {
        return null;
    }

    @Override
    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");

        }
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find gallery with id " + id));
        imageRepository.deleteById(id);
        cloudinaryService.deleteFile(image.getPublicId());

        return id;
    }

}
