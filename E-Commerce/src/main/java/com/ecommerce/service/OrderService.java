package com.ecommerce.service;


import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    @Value("${BASE_URL}")
    private String baseUrl;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    @Autowired
    private CartRepo cartRepo;

    public Session createSession(Integer userId) throws StripeException {

        Stripe.apiKey=apiKey;

        List<Cart> userCartItems = cartRepo.findByUser_Id(userId);

        List<SessionCreateParams.LineItem> sessionItemList=new ArrayList<>();

        for(Cart cart : userCartItems)
        {
            sessionItemList.add(createSessionLineItem(cart));
        }

        SessionCreateParams params=SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(baseUrl + "payment/failed")
                .setSuccessUrl(baseUrl + "payment/success")
                .addAllLineItem(sessionItemList)
                .build();

        return Session.create(params);

    }

    private SessionCreateParams.LineItem createSessionLineItem(Cart cart) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(cart))
                .setQuantity((long)cart.getQuantity())
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(Cart cart) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)cart.getProduct().getPrice()*100)//Stripe expects cents
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(cart.getProduct().getName())
                        .build()
                ).build();
    }
}
