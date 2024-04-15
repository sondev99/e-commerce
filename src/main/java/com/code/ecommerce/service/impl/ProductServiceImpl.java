package com.code.ecommerce.service.impl;

import com.code.ecommerce.adapter.CloudinaryService;
import com.code.ecommerce.dto.request.ProductRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.ProductDto;
import com.code.ecommerce.entity.Brand;
import com.code.ecommerce.entity.Category;
import com.code.ecommerce.entity.Image;
import com.code.ecommerce.entity.Product;
import com.code.ecommerce.exceptions.APIException;
import com.code.ecommerce.exceptions.MissingInputException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.BrandMapper;
import com.code.ecommerce.mapper.CategoryMapper;
import com.code.ecommerce.mapper.ProductMapper;
import com.code.ecommerce.repository.BrandRepository;
import com.code.ecommerce.repository.CategoryRepository;
import com.code.ecommerce.repository.ProductRepository;
import com.code.ecommerce.service.ImageService;
import com.code.ecommerce.service.ProductService;
import com.code.ecommerce.utils.PaginationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;
    private final ImageService imageService;
    private final ObjectMapper objectMapper;
    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;

    private Image saveImageToCloud(MultipartFile file) {
        Map result = cloudinaryService.uploadFile(file);
        String imageUrl = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");
        return Image.builder().publicId(publicId).thumbnailUrl(imageUrl).build();
    }

    @Transactional
    public String createProduct(ProductRequest productRequest) {
        log.info("ProductServiceImpl | createProduct is called");

        Product entity = productMapper.reqToEntity(productRequest);

        List<Image> galleries = productRequest.getFiles().stream().map(this::saveImageToCloud).toList();
        entity.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Can't find category with id" + productRequest.getCategoryId())));
        entity.setBrand(brandRepository.findById(productRequest.getBrandId()).orElseThrow(
                () -> new NotFoundException("Can't find brand with id" + productRequest.getBrandId())));
        entity.setThumbnailUrls(galleries);
        return productMapper.toDto(productRepository.save(entity)).getId();
    }

    public ProductDto findProductById(String productId) {
        log.info("ProductServiceImpl | findProductById is called");
        log.info("ProductServiceImpl | findProductById | Get the product for productId: {}", productId);
        if (productId == null) {
            throw new MissingInputException("Missing input productId");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Can't find product with id " + productId));

        ProductDto productDto = productMapper.toDto(product);

        productDto.setBrandDTO(brandMapper.toDto(product.getBrand()));
        productDto.setCategoryDTO(categoryMapper.toDto(product.getCategory()));

        log.info("ProductServiceImpl | findProductById | productDto :" + productDto.toString());

        return productDto;
    }

    public PagingData getProducts(String searchText, Integer offset, Integer pageSize, String sortStr) {
        log.info("ProductServiceImpl | findProductById is called");
        log.info("ProductServiceImpl | findProductById | offset {}, pageSize {}, sortStr {}   : ", offset, pageSize,
                sortStr);

        Page<Product> productPage;
        Sort sort = PaginationUtils.buildSort(sortStr);
        Pageable pageable = PageRequest.of(offset, pageSize, sort);

        if (StringUtils.isNotEmpty(searchText)) {
            productPage = productRepository.findAll(pageable);
        } else {
            productPage = productRepository.findByNameContainingIgnoreCase(searchText, pageable);
        }

        return PagingData.builder()
                .data(productPage.getContent())
                .searchText(searchText)
                .offset(offset)
                .pageSize(pageSize)
                .sort(sortStr)
                .totalRecord(productPage.getTotalElements())
                .build();
    }

    public PagingData findProductsByCategoryAndBrand(Integer offset, Integer pageSize, String categoryId, String brandId) {
        log.info("ProductServiceImpl | findProductsByCategoryAndBrand is called");
        log.info("ProductServiceImpl | findProductsByCategoryAndBrand | offset {}, pageSize {} : ",
                offset, pageSize
        );

        Pageable pageable = PageRequest.of(offset, pageSize);
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Can't find category with id" + categoryId));
        Brand brand = brandRepository.findById(brandId).orElseThrow(
                () -> new NotFoundException("Can't find brand with id" + brandId));

        Page<Product> productPage = productRepository.findByCategoryAndBrand(category, brand, pageable);


        return PagingData.builder()
                .data(productPage.getContent())
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(productPage.getTotalElements())
                .build();
    }

    public ProductDto updateProduct(List<MultipartFile> files, String data, String id) throws JsonProcessingException {
        log.info("ProductServiceImpl | updateProduct is called");
        log.info("ProductServiceImpl | updateProduct | Update the product for productId: {}", id);

        Product currentProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product with id " + id));

        ProductRequest productRequest = objectMapper.readValue(data, ProductRequest.class);
        List<Image> galleries;
        if (files != null) {
            List<Image> imageList = currentProduct.getThumbnailUrls();
            imageList.forEach(image -> imageService.deleteById(image.getId()));
            galleries = files.stream().map(this::saveImageToCloud).toList();
        } else {
            galleries = currentProduct.getThumbnailUrls();
        }

        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new NotFoundException("Can't find brand with id: " + id));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Can't find category with id: " + id));

        Product newProduct = Product.builder()
                .id(currentProduct.getId())
                .name(productRequest.getName())
                .thumbnailUrls(galleries)
                .brand(brand)
                .category(category)
                .description(productRequest.getDescription())
                .sku(productRequest.getSku())
                .discount(productRequest.getDiscount())
                .quantity(productRequest.getQuantity())
                .priceUnit(productRequest.getPriceUnit())
                .ratings(currentProduct.getRatings())
                .build();

        return productMapper.toDto(productRepository.save(newProduct));
    }

    public String deleteProductById(String id) {
        if (id == null) {
            throw new MissingInputException("Missing input id");
        }
        productRepository.deleteById(id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find product with id " + id));
        product.getThumbnailUrls().forEach(image ->
                cloudinaryService.deleteFile(image.getPublicId()));
        return id;
    }

    @Override
    public void reduceQuantity(String productId, Integer quantity) {
        log.info("Reduce Quantity {} for Id: {}", quantity, productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "Can't find product with id" + productId
                ));

        if (product.getQuantity() < quantity) {
            throw new APIException(
                    "Product does not have sufficient Quantity"
            );
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully");
    }

}
