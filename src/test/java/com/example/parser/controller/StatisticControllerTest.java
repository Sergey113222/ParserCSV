package com.example.parser.controller;

import com.example.parser.dto.RequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StatisticController statisticController;
    @Autowired
    private ObjectMapper objectMapper;
    private RequestDto requestDto;

    {
        requestDto = new RequestDto();
        requestDto.setUrl("https://en.wikipedia.org/wiki/Elon_Musk");
        requestDto.setMaxDepth(1);
        requestDto.setMaxPage(10);
        requestDto.setLimitNumber(5);
    }

    @Test
    void getAllStatistic() throws Exception {

        String responseAsString = mockMvc
                .perform(post("/statistics")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void getTopSortedStatistic() throws Exception {

        String responseAsString = mockMvc
                .perform(post("/top")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}