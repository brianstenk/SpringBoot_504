package com.example.springboot_503.integration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieActorRestfulWebServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetMovieActor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add"))
                .andDo(MockMvcResultHandlers.log())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldGetMovieActors() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/actor/list"))
                .andDo(MockMvcResultHandlers.log())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        String htmlStr = mvcResult.getResponse().getContentAsString();
        Assertions.assertNotNull(htmlStr);
        Assertions.assertTrue(htmlStr.contains("real name"));
    }
}
