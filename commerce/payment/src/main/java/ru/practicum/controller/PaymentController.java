package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.client.PaymentClient;
import ru.practicum.dto.OrderDto;
import ru.practicum.dto.PaymentDto;
import ru.practicum.service.PaymentService;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentClient {
    private final PaymentService paymentService;

    @Override
    @PostMapping
    public PaymentDto createPayment(@RequestBody OrderDto order) {
        return paymentService.createPayment(order);
    }

    @Override
    @PostMapping("/totalCost")
    public Double getTotalCost(@RequestBody OrderDto order) {
        return paymentService.getTotalCost(order);
    }

    @Override
    @PostMapping("/refund")
    public void paymentSuccess(@RequestBody UUID orderId) {
        paymentService.paymentSuccess(orderId);
    }

    @Override
    @PostMapping("/productCost")
    public Double getProductCost(@RequestBody OrderDto order) {
        return paymentService.getProductCost(order);
    }

    @Override
    @PostMapping("/failed")
    public void paymentFailed(@RequestBody UUID orderId) {
        paymentService.paymentFailed(orderId);
    }
}
