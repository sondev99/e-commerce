package com.code.ecommerce.service.impl;

import com.code.ecommerce.adapter.CloudinaryService;
import com.code.ecommerce.dto.request.BannerRequest;
import com.code.ecommerce.dto.response.BannerDto;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.entity.Banner;
import com.code.ecommerce.exceptions.MissingInputException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.BannerMapper;
import com.code.ecommerce.repository.BannerRepository;
import com.code.ecommerce.service.BannerService;
import com.code.ecommerce.utils.PaginationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;
    private final BannerMapper bannerMapper;

    private Banner saveImageToCloud(MultipartFile file) {
        Map result = cloudinaryService.uploadFile(file);
        String imageUrl = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");
        return Banner.builder().publicId(publicId).imageUrl(imageUrl).build();
    }

    @Override
    public String create(BannerRequest bannerRequest) {
        Banner entity = saveImageToCloud(bannerRequest.getFile());
        entity.setName(bannerRequest.getName());
        return bannerMapper.toDto(bannerRepository.save(entity)).getId();
    }

    @Override
    public BannerDto findById(String id) {
        Banner currentBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find banner with id: " + id));
        return bannerMapper.toDto(currentBanner);
    }

    @Override
    public PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr) {
        Page<Banner> bannerPageEntities;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (searchText.isEmpty()) {
            bannerPageEntities = bannerRepository.findAll(pageable);
        } else {
            bannerPageEntities = bannerRepository.findByNameContainingIgnoreCase(searchText, pageable);
        }

        Page<BannerDto> bannerDtoPage = bannerPageEntities.map(bannerMapper::toDto);

        return PagingData.builder()
                .data(bannerDtoPage)
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(bannerDtoPage.getTotalElements())
                .build();
    }

    @Override
    public BannerDto update(MultipartFile file, String data, String id) throws JsonProcessingException {
        Banner currentBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find banner with id " + id));

        BannerRequest bannerRequest = objectMapper.readValue(data, BannerRequest.class);

        String imageUrl;
        String publicId;
        if (file != null) {
            String currentPublicId = currentBanner.getPublicId();
            cloudinaryService.deleteFile(currentPublicId);
            Map result = cloudinaryService.uploadFile(file);
            imageUrl = (String) result.get("secure_url");
            publicId = (String) result.get("public_id");
        } else {
            imageUrl = currentBanner.getImageUrl();
            publicId = currentBanner.getPublicId();
        }

        Banner newBanner = Banner.builder()
                .name(bannerRequest.getName())
                .id(currentBanner.getId())
                .publicId(publicId)
                .imageUrl(imageUrl)
                .build();
        return bannerMapper.toDto(bannerRepository.save(newBanner));
    }

    @Override
    @Transactional
    public String deleteById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");

        }
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find banner with id " + id));
        cloudinaryService.deleteFile(banner.getPublicId());

        bannerRepository.deleteById(id);

        return id;
    }

}
