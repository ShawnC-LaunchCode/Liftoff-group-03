package org.launchcode.tutorconnector.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.launchcode.tutorconnector.models.dto.StripeRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/student/payment")
public class StripePaymentController {

    @Value("${stripe.api.secretKey}")
    private String secretKey;

    //User initiates the payment process & fills out the form
    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        model.addAttribute("stripeRequest", new StripeRequestDTO());
        return "checkout";
    }

    //When form is submitted
    @PostMapping("/processPayment")
    public String processPayment(@ModelAttribute StripeRequestDTO stripeRequestDTO, Model model) {
        try {
            PaymentIntent intent = createPaymentIntent(stripeRequestDTO, model);

            // Add PaymentIntent information to the model
            model.addAttribute("clientSecret", intent.getClientSecret());
            model.addAttribute("amount", StripeRequestDTO.getAmount());
            model.addAttribute("email", stripeRequestDTO.getEmail());
            model.addAttribute("tutorSession", StripeRequestDTO.getTutorSession());

            return "paymentConfirmation";
        } catch (StripeException e) {
            // Handle exception
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        }

        //payment intent is for the server side, do I need post mapping request?
        private PaymentIntent createPaymentIntent(@ModelAttribute StripeRequestDTO stripeRequestDTO, Model model)
                throws StripeException {
        Stripe.apiKey = secretKey;
            // enforced by Stripe library to throw an exception in case of errors during interaction with API
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            //Stripe uses cents
                            .setAmount(StripeRequestDTO.getAmount() * 100L)
                            .putMetadata("tutorSession", StripeRequestDTO.getTutorSession())
                            .setCurrency("usd")
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();
// once intent is created, sends request to Stripe API to create payment based on parameters, client secret used to confirm the payment on client side
            PaymentIntent intent = PaymentIntent.create(params);
            model.addAttribute("intentID", intent.getId());
            model.addAttribute("clientSecret", intent.getClientSecret());

            return intent;
        }
// create checkout.html, paymentConfirmation.html, error.html

    }
