package com.polixis.springai.controllers;

import com.polixis.springai.model.Answer;
import com.polixis.springai.model.GetCapitalRequest;
import com.polixis.springai.model.GetCapitalResponse;
import com.polixis.springai.model.Question;
import com.polixis.springai.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/capital-with-scheme")
    public GetCapitalResponse getCapitalWithScheme(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapitalWithScheme(getCapitalRequest);
    }

    @PostMapping("/capitalWithInfo")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/ask-rag")
    public Answer askQuestionWithRag(@RequestBody Question question) {
        return openAIService.getAnswerRag(question);
    }
}
