package org.launchcode.tutorconnector.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
//initialize the Stripe API key at the application startup
    //reads the secret key from applications startup
    @Value("${stripe.api.secretKey}")
    private String secretKey;

    @PostConstruct
    public void initSecretKey(){
        Stripe.apiKey = secretKey;
    }
}
