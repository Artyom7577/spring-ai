package com.polixis.springai.services;

import com.polixis.springai.model.Answer;
import com.polixis.springai.model.Question;

public interface OpenAiServiceReg {

    Answer getAnswer(Question question);
}
