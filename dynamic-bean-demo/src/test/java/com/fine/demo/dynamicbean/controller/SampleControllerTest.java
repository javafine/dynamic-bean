package com.fine.demo.dynamicbean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SampleController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>10月14日, 2022</pre>
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: color(@PathVariable("c") String c)
     */
    @Test
    public void testColor() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/color/red", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(
                objectMapper.writeValueAsString(response.getBody()),
                objectMapper.writeValueAsString("I am red")
        );
    }

    /**
     * Method: colorAndShape(@PathVariable("c") String c, @PathVariable("s") String s)
     */
    @Test
    public void testColorAndShape() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/color/red/shape/rectangle", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(
                objectMapper.writeValueAsString(response.getBody()),
                objectMapper.writeValueAsString("I am a rectangle, I am red")
        );
    }


} 
