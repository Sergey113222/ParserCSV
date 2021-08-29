package com.example.parser.service.impl;

import com.example.parser.repository.ParserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ParserServiceImplTest {
    private ParserServiceImpl parserService;
    private Selector selector;
    private ParserRepository parserRepository;
    private Set<String> urls;

    private final String str = "Tesla Model 3 and Tesla Model S";
    private final String url = "https://en.wikipedia.org/wiki/Elon_Musk";

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