package ru.practicum.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.Pageable;
import ru.practicum.dto.ProductCategory;
import ru.practicum.dto.ProductDto;
import ru.practicum.dto.SetProductQuantityStateRequest;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {
    @GetMapping
    Page<ProductDto> getProducts(@RequestParam(name = "category") @NotNull ProductCategory category,
                                 Pageable pageable);

    @GetMapping("/{productId}")
    ProductDto getProduct(@PathVariable UUID productId);

    @GetMapping("/onlyIds")
    List<ProductDto> getProductByIds(@RequestParam Collection<UUID> ids);

    @PutMapping
    ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping
    ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    boolean removeProductFromStore(@RequestBody UUID productId);

    @PostMapping("/quantityState")
    boolean setProductQuantityState(SetProductQuantityStateRequest request);
}
