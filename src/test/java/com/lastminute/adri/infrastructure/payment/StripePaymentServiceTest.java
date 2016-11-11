package com.lastminute.adri.infrastructure.payment;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

public class StripePaymentServiceTest {

    private static final BigDecimal AMOUNT = BigDecimal.TEN;
    private static final String URL = "http://localhost:8080";
    private static final String CHARGES_SUFFIX = "/charges";
    private static final String TOKENS_SUFFIX = "/tokens";
    private static final String AUTHORIZATION = "Authorization";
    private static final String API_ID = "sk_test_2Ri4DVJZVCxsB59oKr8ZtBT7:";
    private static final String BEARER_API_ID = String.format("Bearer %s", API_ID);
    private static final String TOKEN_ID = "tokenId";
    private static final String CREDIT_CARD_DATA = "data";
    private static final String CHARGE_DATA = String.format("{'amount': %d, 'source': %s}", AMOUNT.longValue(), TOKEN_ID);

    private PaymentService stripePaymentService = new StripePaymentService(URL);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void makePayment() {
        mockStripeTokenCallResult(200);
        mockStripeChargeCallResult(200);

        boolean result = stripePaymentService.makePayment(AMOUNT);

        Assert.assertTrue(result);
        verifyTokenRequestIsMocked();
        verifyChargeRequestIsMocked();
    }

    private void mockStripeChargeCallResult(int expectedResult) {
        WireMock.givenThat(WireMock.post(WireMock.urlEqualTo(CHARGES_SUFFIX))
                .withHeader(AUTHORIZATION, WireMock.equalTo(BEARER_API_ID))
                .withRequestBody(WireMock.equalTo(CHARGE_DATA))
                .willReturn(WireMock.aResponse()
                        .withStatus(expectedResult)));
    }

    private void mockStripeTokenCallResult(int expectedResult) {
        WireMock.givenThat(WireMock.post(WireMock.urlEqualTo(TOKENS_SUFFIX))
                .withHeader(AUTHORIZATION, WireMock.equalTo(BEARER_API_ID))
                .withRequestBody(WireMock.equalTo(CREDIT_CARD_DATA))
                .willReturn(WireMock.aResponse()
                        .withStatus(expectedResult)
                        .withBody(TOKEN_ID)));
    }

    private void verifyChargeRequestIsMocked() {
        WireMock.verify(WireMock.postRequestedFor(WireMock.urlMatching(CHARGES_SUFFIX))
                .withHeader(AUTHORIZATION, WireMock.equalTo(BEARER_API_ID))
                .withRequestBody(WireMock.equalTo(CHARGE_DATA)));
    }

    private void verifyTokenRequestIsMocked() {
        WireMock.verify(WireMock.postRequestedFor(WireMock.urlMatching(TOKENS_SUFFIX))
                .withHeader(AUTHORIZATION, WireMock.equalTo(BEARER_API_ID))
                .withRequestBody(WireMock.equalTo(CREDIT_CARD_DATA)));
    }
}