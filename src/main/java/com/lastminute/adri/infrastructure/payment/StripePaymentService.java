package com.lastminute.adri.infrastructure.payment;

import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Token;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StripePaymentService implements PaymentService {

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    public boolean makePayment(BigDecimal coursePrice) {
        int result = sendPost();

        if(result == 200) {
            return true;
        }
        return false;
    }

    public boolean makePaymentOld(BigDecimal coursePrice) {
        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_2Ri4DVJZVCxsB59oKr8ZtBT7";

        // Get the credit card details submitted by the form
        String token = getToken().getId();

        // Create a charge: this will charge the user's card
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", 1000); // Amount in cents
        chargeParams.put("currency", "eur");
        chargeParams.put("source", token);
        chargeParams.put("description", "Example charge");

        try {
            Charge.create(chargeParams);
        } catch (Exception e) {
            throw new PaymentException("Error with payment", e);
        }
        return true;
    }

    private Token getToken() {
        Stripe.apiKey = "sk_test_2Ri4DVJZVCxsB59oKr8ZtBT7";

        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", "4242424242424242");
        cardParams.put("exp_month", 11);
        cardParams.put("exp_year", 2017);
        cardParams.put("cvc", "314");
        tokenParams.put("card", cardParams);

        try {
            return Token.create(tokenParams);
        } catch (Exception e) {
            throw new PaymentException("Error getting token", e);
        }
    }

    private int sendPost() {
        try {
            String url = "http://localhost:8080";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(RequestMethod.POST.getName());

            int responseCode = con.getResponseCode();

            return responseCode;
        } catch (Exception exc) {
            throw new PaymentException("Payment exception", exc);
        }
    }
}
