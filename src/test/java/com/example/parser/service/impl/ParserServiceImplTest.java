package com.example.parser.service.impl;

import com.example.parser.dto.RequestDto;
import com.example.parser.model.ParserModel;
import com.example.parser.repository.ParserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParserServiceImplTest {
    private ParserServiceImpl parserService;
    private Selector selector;
    private ParserRepository parserRepository;
    private ParserModel parserModel;
    private Set<String> urls;
    private List<ParserModel> parserModelList;
    private RequestDto requestDto;

    private final String str = "Tesla Model 3 and Tesla Model S";
    private final String url = "https://en.wikipedia.org/wiki/Elon_Musk";

    {
        requestDto = new RequestDto();
        requestDto.setUrl(url);
    }

    {
        parserModel = new ParserModel();

        parserModel.setUrl("https://en.wikipedia.org/wiki/Elon_Musk");
        parserModel.setTesla(1);
        parserModel.setMusk(2);
        parserModel.setGigafactory(3);
        parserModel.setElonMusk(4);
        parserModel.setTotal(10);
        parserModelList = new ArrayList<>();
        parserModelList.add(parserModel);
    }

    {
        urls = new HashSet<>();
        urls.add(url);
    }

    @BeforeEach
    void setUp() {
        selector = mock(Selector.class);
        parserRepository = mock(ParserRepository.class);
        parserService = new ParserServiceImpl(selector, parserRepository);
    }

    @Test
    void matcher() {
        Assertions.assertEquals(2, parserService.matcher(str, "Tesla"));
    }

    @Test
    void saveAllStatistic() {
        parserService.saveAllStatistic(urls);
        verify(parserRepository).saveAll(any());
    }

    @Test
    void saveSortedLimitedList() {
        parserService.saveSortedLimitedList(urls, 1);
        verify(parserRepository).saveAll(any());
    }
}