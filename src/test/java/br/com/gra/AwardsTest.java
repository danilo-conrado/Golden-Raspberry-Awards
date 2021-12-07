package br.com.gra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gra.query.Application;
import br.com.gra.query.goldenraspberryawards.entity.Movies;
import br.com.gra.query.goldenraspberryawards.repository.MoviesRepository;

 

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class AwardsTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Mock
    private ObjectMapper objectMapperMock;
    
    @Mock
    MoviesRepository repository;
    
    
    
    @Test
    void testRequestEndpoint() throws JsonProcessingException, Exception{
    	
        mockMvc.perform(get("/api/movies/MajorAndMinor")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString("")))
                .andExpect(status().isOk());
        
    }
    
    @Test
    void testDb() throws JsonProcessingException, Exception{
        Movies movieTest = new Movies();
        List<Movies> listMoviesTest = new ArrayList<Movies>();
        

        when(repository.save(any(Movies.class))).thenReturn(movieTest);
        when(repository.findFirstByOrderByIdDesc()).thenReturn(movieTest);
        when(repository.findAll()).thenReturn(listMoviesTest);
    }
    
    @Test
    void testGetAward() throws JsonProcessingException, Exception{
        mockMvc.perform(get("/api/movies/MajorAndMinor")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.max").exists());
    }
    	
}

    
