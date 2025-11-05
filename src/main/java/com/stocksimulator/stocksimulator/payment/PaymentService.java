package com.stocksimulator.stocksimulator.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentService {

    public PaymentService(@Value("${stripe.apiKey}") String apiKey) {
        Stripe.apiKey = apiKey;
    }

    @Value("${stripe.successUrl}")
    private String successUrl;

    @Value("${stripe.cancelUrl}")
    private String cancelUrl;

    public String checkout(PaymentRequest request) throws Exception {

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(cancelUrl)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("sek")
                                                    .setUnitAmount((long) Math.round(request.getAmount() * 100))
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(request.getDescription())
                                                                    .build())
                                                    .build())
                                    .build())
                    .build();

            Session session = Session.create(params);
            return session.getId();
        } catch (StripeException e) {

            e.printStackTrace();
            throw new RuntimeException("Stripe checkout failed" + e.getMessage());
        }
    }

    public void processWebhook(String payload, String signature) {
        System.out.println("Webhook payload: " + payload);
    }

}
