package com.mprybicki.rfagent.service;

import com.mprybicki.rfagent.model.RFData;
import com.mprybicki.rfagent.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

@AllArgsConstructor
@Service
@Slf4j
public class SendGeneratedRFDataService {

    private RestTemplate restTemplate;
    private Random random;

    private static String token;

    public void sendGeneratedRFData() {
        //TODO move URL to properties
        ResponseEntity<String> responseEntity = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RFData> request = new HttpEntity<>(generateRFData(), headers);
            responseEntity = restTemplate.exchange("http://localhost:8004/rf-data", HttpMethod.POST, request, String.class);
            log.info("generated rf data sent");
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            log.warn("user is not authorized or service is unavailable");
        }

        if (responseEntity == null || responseEntity.getStatusCode().value() == 401 || responseEntity.getStatusCode().value() == 500) {
            token = login();
        }
    }

    private RFData generateRFData() {
        return new RFData(random.nextInt(10), generateRandomDouble(0.0, 10.0), random.nextInt(10));
    }

    private Double generateRandomDouble(Double rangeMin, Double rangeMax) {
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }

    //TODO move values to properties
    private String login() {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity("http://localhost:8004/authenticate",
                    new User("rf-agent", "p@$sW0rD"), String.class);
        } catch (HttpClientErrorException exception) {
            log.warn("user is not registered");
        }
        if (responseEntity == null || responseEntity.getStatusCode().value() == 401) {
            register();
            return null;
        }
        return responseEntity.getBody();
    }

    //TODO move values to properties
    private void register() {
        restTemplate.postForEntity("http://localhost:8004/register", new User("rf-agent", "p@$sW0rD"), String.class);
    }
}
