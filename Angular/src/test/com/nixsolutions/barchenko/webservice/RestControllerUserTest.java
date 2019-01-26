package com.nixsolutions.barchenko.webservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.webservice.rest.UserWebServiceRest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.hamcrest.CoreMatchers.equalTo;

class RestControllerUserTest {
    UserWebServiceRest rest;

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://10.10.103.151";
        RestAssured.port = 8080;
    }

    @BeforeEach
    void setUp() {
        rest = new UserWebServiceRest();
    }

    @Test
    void getUser() {
        RestAssured.get("/rest/users/reg")
                .then()
                .body("login", equalTo("reg"))
                .body("password", equalTo("reg"))
                .and().statusCode(200);
    }

    @Test
    void getUserFail() {
        RestAssured.get("/rest/users/wrongUser")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void createUser() throws JsonProcessingException {
        User user = new User();
        user.setLogin("restTest");
        user.setPassword("1");
        user.setFirstName("restTest");
        user.setLastName("restTest");
        user.setEmail("restTest@mail.ru");
        user.setBirthday(Date.valueOf("2000-11-11"));
        Role role = new Role();
        role.setRoleId(2L);
        role.setName("USER");
        user.setRole(role);
        String requestBody = new ObjectMapper().writeValueAsString(user);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody);

        Response response = request.post("http://10.10.103.151:8080/rest/users/create");
        Assert.assertEquals(201, response.getStatusCode());
    }

    @Test
    public void createUserFail() throws JsonProcessingException {
        User user = null;
        String requestBody = new ObjectMapper().writeValueAsString(user);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody);

        Response response = request.post("http://10.10.103.151:8080/rest/users/create");
        Assert.assertEquals(400, response.getStatusCode());
    }

    @Test
    public void editUser() throws JsonProcessingException {
        User user = new User();
        user.setLogin("editTestRest");
        user.setFirstName("1");
        user.setLastName("1");
        user.setPassword("1");
        user.setEmail("1@mail.ru");
        user.setBirthday(Date.valueOf("2000-11-11"));
        Role role = new Role();
        role.setRoleId(2L);
        role.setName("USER");
        user.setRole(role);
        String requestBody = new ObjectMapper().writeValueAsString(user);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody);

        Response response = request.put("http://10.10.103.151:8080/rest/users/edit");
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void editUserFail() throws JsonProcessingException {
        User user = new User();
        user.setLogin("wrongUser");
        user.setFirstName("1");
        user.setLastName("1");
        user.setPassword("1");
        user.setEmail("1@mail.ru");
        user.setBirthday(Date.valueOf("2000-11-11"));
        Role role = new Role();
        role.setRoleId(2L);
        role.setName("USER");
        user.setRole(role);
        String requestBody = new ObjectMapper().writeValueAsString(user);

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody);

        Response response = request.put("http://10.10.103.151:8080/rest/users/edit");
        Assert.assertEquals(400, response.getStatusCode());
    }

    @Test
    void deleteUser() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.delete("http://10.10.103.151:8080/rest/users/delete/restTest");
        request.then().statusCode(200);
    }

    @Test
    void deleteUserFail() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.delete("http://10.10.103.151:8080/rest/users/delete/thisUserIsNotExist");
        request.then().statusCode(400);
    }

}