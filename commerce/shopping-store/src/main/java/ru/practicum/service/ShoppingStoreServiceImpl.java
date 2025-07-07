package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.*;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.ProductMapper;
import ru.practicum.model.Product;
import ru.practicum.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingStoreServiceImpl implements ShoppingStoreService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        Sort sort = Optional.ofNullable(pageable.getSort())
                .filter(s -> !s.isEmpty())
                .map(s -> Sort.by(s.get(0)))
                .orElse(Sort.unsorted());

        PageRequest pageRequest = PageRequest.of(
                pageable.getPage(),
                pageable.getSize(),
                sort
        );

        return productRepository.findAllByProductCategory(category, pageRequest)
                .map(productMapper::map);
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        return productMapper.map(getProduct(productId));
    }

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.map(productDto);

        return productMapper.map(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = getProduct(productDto.getProductId());
        productMapper.update(productDto, product);
        return productMapper.map(productRepository.save(product));
    }

    @Override
    @Transactional
    public boolean setQuantityState(SetProductQuantityStateRequest stateRequest) {
        Product product = getProduct(stateRequest.getProductId());
        product.setQuantityState(stateRequest.getQuantityState());
        productRepository.save(product);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteProduct(UUID productId) {
        Product product = getProduct(productId);
        product.setProductState(ProductState.DEACTIVATE);
        productRepository.save(product);

        return true;
    }

    private Product getProduct(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException(
                String.format("Ошибка, товар по идентификатору %s в БД не найден", productId))
        );
    }
}