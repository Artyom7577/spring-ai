package com.polixis.springai.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIServiceImpl genAIService;

    @Test
    void getAnswer() {
        String answer = genAIService.getAnswer("What is the meaning of life? very short 5 words");
        assertNotNull(answer);
    }
}