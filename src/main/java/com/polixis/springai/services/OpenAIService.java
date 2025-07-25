package com.polixis.springai.services;

import com.polixis.springai.model.Answer;
import com.polixis.springai.model.GetCapitalRequest;
import com.polixis.springai.model.GetCapitalResponse;
import com.polixis.springai.model.Question;

public interface OpenAIService {
    String getAnswer(String question);
    Answer getAnswer(Question question);
    Answer getAnswerRag(Question question);

    Answer getCapital(GetCapitalRequest getCapitalRequest);

    GetCapitalResponse getCapitalWithScheme(GetCapitalRequest getCapitalRequest);

    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

}
