package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;


public interface ShoppingCartOperations {
    @GetMapping
    ShoppingCartDto getShoppingCart(@RequestParam String username);

    @PostMapping
    ShoppingCartDto addProductsToCart(@RequestParam String username,
                                      @RequestBody Map<UUID, Long> products);

    @DeleteMapping
    void deactivateShoppingCart(@RequestParam String username);

    @PutMapping("/remove")
    ShoppingCartDto replaceShoppingCartContents(@RequestParam String username,
                                                @RequestBody Map<UUID, Long> products);

    @PutMapping("/change-quantity")
    ShoppingCartDto changeProductQuantity(@RequestParam String username,
                                          @RequestBody @Valid ChangeProductQuantityRequest request);

    @PostMapping("/booking")
    BookedProductsDto bookProducts(@RequestParam String username);
}