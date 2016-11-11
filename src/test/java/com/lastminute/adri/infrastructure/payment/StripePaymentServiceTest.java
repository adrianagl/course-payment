package com.lastminute.adri.infrastructure.payment;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

public class StripePaymentServiceTest {

    private static final BigDecimal AMOUNT = BigDecimal.TEN;

    private PaymentService stripePaymentService = new StripePaymentService();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void makePayment() {
        mockResult(200);

        boolean result = stripePaymentService.makePayment(AMOUNT);

        Assert.assertTrue(result);
        verifyRequestIsMocked();
    }

    private void mockResult(int expectedResult) {
        WireMock.givenThat(WireMock.post(WireMock.urlEqualTo("/"))
                .willReturn(WireMock.aResponse()
                        .withStatus(expectedResult)));
    }

    private void verifyRequestIsMocked() {
        WireMock.verify(WireMock.postRequestedFor(WireMock.urlMatching("/")));
    }
}