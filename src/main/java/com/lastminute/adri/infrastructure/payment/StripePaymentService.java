package com.lastminute.adri.infrastructure.payment;

import com.github.tomakehurst.wiremock.http.RequestMethod;
import org.apache.commons.codec.Charsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class StripePaymentService implements PaymentService {

    private static final String CHARGES_SUFFIX = "/charges";
    private static final String TOKENS_SUFFIX = "/tokens";
    private static final String AUTHORIZATION = "Authorization";
    private static final String API_ID = "sk_test_2Ri4DVJZVCxsB59oKr8ZtBT7:";
    private static final String BEARER_API_ID = String.format("Bearer %s", API_ID);
    private static final String CREDIT_CARD_DATA = "data";
    private static final String PAYMENT_REQUEST_DATA = "{'amount': %d, 'source': %s}";
    private static final String PAYMENT_EXCEPTION = "Payment exception";

    private final String urlBase;

    public StripePaymentService(String urlBase) {
        this.urlBase = urlBase;
    }

    @Override
    public boolean makePayment(BigDecimal coursePrice) {
        String token = getToken();

        int result = sendStripePayment(token, coursePrice);

        return result == 200;
    }

    private int sendStripePayment(String token, BigDecimal amount) {
        String data = String.format(PAYMENT_REQUEST_DATA, amount.longValue(), token);
        HttpURLConnection connection = createConnection(CHARGES_SUFFIX, data);

        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new PaymentException(PAYMENT_EXCEPTION, e);
        }
    }

    private String getToken() {
        HttpURLConnection connection = createConnection(TOKENS_SUFFIX, CREDIT_CARD_DATA);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new PaymentException(PAYMENT_EXCEPTION, e);
        }
    }

    private HttpURLConnection createConnection(String suffix, String requestData) {
        try {
            URL obj = new URL(urlBase + suffix);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod(RequestMethod.POST.getName());

            connection.setRequestProperty(AUTHORIZATION, BEARER_API_ID);

            connection.setDoOutput(true);
            byte[] outputBytes = requestData.getBytes(Charsets.UTF_8);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(outputBytes);
            }

            return connection;
        } catch (Exception exc) {
            throw new PaymentException(PAYMENT_EXCEPTION, exc);
        }
    }
}
