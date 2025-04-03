package com.peter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peter.domain.TradeInfo;
import com.peter.service.TradeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testList() throws Exception {
        TradeInfo tradeInfo = new TradeInfo();
        List<TradeInfo> list = Arrays.asList(tradeInfo);
        when(tradeService.selectTradeInfoList(tradeInfo)).thenReturn(list);

        mockMvc.perform(get("/trade/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetInfo() throws Exception {
        String id = "1";
        TradeInfo tradeInfo = new TradeInfo();
        when(tradeService.selectTradeInfoById(id)).thenReturn(tradeInfo);

        mockMvc.perform(get("/trade/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}