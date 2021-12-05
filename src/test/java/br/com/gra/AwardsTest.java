package br.com.gra;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gra.query.Application;
import br.com.gra.query.goldenraspberryawards.entity.Award;
import br.com.gra.query.goldenraspberryawards.entity.Movies;
import br.com.gra.query.goldenraspberryawards.entity.Producer;

 

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class AwardsTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Mock
    private ObjectMapper objectMapperMock;
    
    AwardValidator awardValidator;
    
    @BeforeEach
    public void setUp() {
    	awardValidator = new AwardValidator(objectMapperMock);
    }
    @Test
    void testGet() throws JsonProcessingException, Exception{
    	Movies movie = new Movies(1998,"teste","teste2","teste3",true);
        mockMvc.perform(get("/api/movies/MajorAndMinor")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetAward() throws JsonProcessingException, Exception{
    	Producer producerMin = new Producer(Long.parseLong("1000"),"Teste");
    	Producer producerMax = new Producer(Long.parseLong("2000"),"Teste");
    	
        Award award = new Award(producerMin,producerMax);
        award.getMax().get(0).setFollowingWin(2000);
        award.getMax().get(0).setPreviousWin(1900);
        award.getMax().get(0).setInterval(100);
        
        award.getMin().get(0).setFollowingWin(2000);
        award.getMin().get(0).setPreviousWin(1999);
        award.getMin().get(0).setInterval(1);

        when(objectMapperMock.readValue(anyString(), eq(Award.class))).thenReturn(award);

        assertTrue(awardValidator.AwardHasProducer("Json Validado!"));

        verify(objectMapperMock, times(1)).readValue(anyString(), eq(Award.class));
    }
    	
}

    
