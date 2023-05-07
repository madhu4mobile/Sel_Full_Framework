package org.automation.pom.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.automation.pom.objects.BillingAddress;

import java.io.IOException;
import java.io.InputStream;

public class JacksonUtils {

    // method to return specific Object with filePath:
    public static BillingAddress deserializeJson(String filePath, BillingAddress billingAddress) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(filePath, billingAddress.getClass());

    }

    // method to return specific Object:
    public static BillingAddress deserializeJson(InputStream inputStream, BillingAddress billingAddress) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, billingAddress.getClass());

    }

}

