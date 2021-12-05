package br.com.gra;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gra.query.goldenraspberryawards.entity.Award;

public class AwardValidator {
    private ObjectMapper objectMapper;

    public  AwardValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean AwardHasProducer(String jsonAwardAsString) throws IOException {
        Award award = objectMapper.readValue(jsonAwardAsString, Award.class);
        return award.getMax().size() > 0;
    }
}
