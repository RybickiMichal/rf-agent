package com.mprybicki.rfagent.service;

import com.mprybicki.rfagent.model.RFData;
import com.mprybicki.rfagent.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@Slf4j
public class SendGeneratedRFDataService {

    private RestTemplate restTemplate;
    private Random random;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${rf.agent.user}")
    private String user;

    @Value("${rf.agent.password}")
    private String password;

    private static String token;

    public SendGeneratedRFDataService(RestTemplate restTemplate, Random random) {
        this.restTemplate = restTemplate;
        this.random = random;
    }

    public void sendGeneratedRFData() {
        ResponseEntity<String> responseEntity = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RFData> request = new HttpEntity<>(generateRFData(), headers);
            log.info(gatewayUrl + "rf-data");
            responseEntity = restTemplate.exchange(gatewayUrl + "rf-data", HttpMethod.POST, request, String.class);
            log.info("generated rf data sent");
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            log.warn("user is not authorized or service is unavailable");
        }

        if (responseEntity == null || responseEntity.getStatusCode().value() == 401 || responseEntity.getStatusCode().value() == 500) {
            token = login();
        }
    }

    private RFData generateRFData() {
        return new RFData(generateRandomInteger(-90, 90), generateRandomDouble(100.0, 400.0), generateRandomInteger(90,440));
    }

    private Integer generateRandomInteger(Integer rangeMin, Integer rangeMax) {
        return random.nextInt(rangeMax - rangeMin) + rangeMax;
    }

    private Double generateRandomDouble(Double rangeMin, Double rangeMax) {
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }

    private String login() {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(gatewayUrl + "authenticate",
                    new User(user, password), String.class);
        } catch (HttpClientErrorException exception) {
            log.warn("user is not registered");
        }
        if (responseEntity == null || responseEntity.getStatusCode().value() == 401) {
            register();
            return null;
        }
        return responseEntity.getBody();
    }

    private void register() {
        restTemplate.postForEntity(gatewayUrl + "register", new User(user, password), String.class);
    }
}
