package com.concrete.login;

import com.concrete.login.security.CScSecurityConstants;
import com.concrete.login.security.CSiJwtTokenResolver;
import com.concrete.login.to.signup.CScSignUpResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class CCAuthTest {

    @Autowired
    CSiJwtTokenResolver jwtTokeResolver;

    String signUpURI;
    String loginURI;
    String userProfileURI;
    RestTemplate restTemplate;
    HttpHeaders headers;
    ResponseEntity<String> response;
    CScSignUpResponseTO signedUpUser;

    @BeforeTest
    public void signUpTestSetup() {

        signUpURI = "http://localhost:8080/v1" + CScSecurityConstants.SIGN_UP_URL;
        loginURI = "http://localhost:8080/login";
        userProfileURI = "http://localhost:8080/v1/protected/users";

        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
    }

    @Test
    public void testSignUpNewUser() {

        String jsonBody = "{\n" +
                "   \"name\": \"joaosilva\",\n" +
                "   \"email\": \"joao@silva.org\",\n" +
                "   \"password\": \"hunter2\",\n" +
                "   \"phones\": [\n" +
                "        {\n" +
                "           \"number\": \"987654321\",\n" +
                "           \"ddd\": \"21\"\n" +
                "        }\n" +
                "   ]\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        signedUpUser = restTemplate.postForObject(signUpURI, entity, CScSignUpResponseTO.class);
        Assert.assertNotNull(signedUpUser);
    }

    @Test(dependsOnMethods = {"testSignUpNewUser"})
    public void testSignUpAlreadyExistingUser() {

        String jsonBody = "{\n" +
                "   \"name\": \"João da Silva Nogueira\",\n" +
                "   \"email\": \"joao@silva.org\",\n" +
                "   \"password\": \"hunter1\",\n" +
                "   \"phones\": [\n" +
                "        {\n" +
                "           \"number\": \"777754321\",\n" +
                "           \"ddd\": \"21\"\n" +
                "        }\n" +
                "   ]\n" +
                "}";

        HttpStatusCodeException ex = null;
        try {
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            response = restTemplate.postForEntity(signUpURI, entity, String.class);
        } catch (HttpStatusCodeException e) {
            ex = e;
        }

        Assert.assertNotNull(ex);
        String loc = ex.getLocalizedMessage();
        System.out.println(loc);
    }

    @Test(dependsOnMethods = {"testSignUpNewUser"})
    public void testValidLogin() {

        String jsonBody = "{\n" +
                "   \"email\": \"joao@silva.org\",\n" +
                "   \"password\": \"hunter2\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        signedUpUser = restTemplate.postForObject(loginURI, entity, CScSignUpResponseTO.class);
        Assert.assertNotNull(signedUpUser);
    }

    @Test
    public void testInvalidLogin() {

        String jsonBody = "{\n" +
                "   \"email\": \"maria@silva.org\",\n" +
                "   \"password\": \"hunter2\"\n" +
                "}";

        Exception ex = null;
        try {
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            signedUpUser = restTemplate.postForObject(loginURI, entity, CScSignUpResponseTO.class);
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull(ex);
    }

    @Test(dependsOnMethods = {"testSignUpNewUser"})
    public void testInvalidLoginPassword() {

        String jsonBody = "{\n" +
                "   \"email\": \"joao@silva.org\",\n" +
                "   \"password\": \"hunter\"\n" +
                "}";

        Exception ex = null;
        try {
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            signedUpUser = restTemplate.postForObject(loginURI, entity, CScSignUpResponseTO.class);
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull(ex);
    }

    @Test(dependsOnMethods = {"testSignUpNewUser"})
    public void testValidUserProfile() {

        userProfileURI = userProfileURI + "/" + signedUpUser.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", CScSecurityConstants.TOKEN_PREFIX + signedUpUser.getToken());

        ResponseEntity<CScSignUpResponseTO> registeredUser = restTemplate.exchange(userProfileURI,
                HttpMethod.GET,
                new HttpEntity<CScSignUpResponseTO>(headers),
                CScSignUpResponseTO.class);

        Assert.assertNotNull(registeredUser);
        Assert.assertEquals(signedUpUser.getToken(), Objects.requireNonNull(registeredUser.getBody()).getToken());
    }

    @Test(enabled = false, dependsOnMethods = {"testSignUpNewUser"})
    public void testExpiredSession() throws InterruptedException {

        Long jwtExpirationInMs = resolveJwtExpirationInMsProperty();
        Thread.sleep(jwtExpirationInMs +1);

        try {
            userProfileURI = userProfileURI + "/" + signedUpUser.getId();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + signedUpUser.getToken());

            ResponseEntity<CScSignUpResponseTO> registeredUser = restTemplate.exchange(userProfileURI,
                    HttpMethod.GET,
                    new HttpEntity<CScSignUpResponseTO>(headers),
                    CScSignUpResponseTO.class);

        } catch (Exception eX) {
            System.out.println(eX);
        }
    }

    private Long resolveJwtExpirationInMsProperty() {

        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("application.properties");

        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Long jwtExpirationInMs = Long.parseLong(properties.get("app.jwtExpirationInMs").toString());
        return jwtExpirationInMs;
    }
}
