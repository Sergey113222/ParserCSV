package com.example.parser.controller;

import com.example.parser.dto.RequestDto;
import com.example.parser.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final ParserService parserService;

    @PostMapping(value = "/statistics")
    public void getAllStatistic(@RequestBody RequestDto requestDto) {
        parserService.getAllStatistic(requestDto);
    }

    @PostMapping(value = "/top")
    public void getTopSortedStatistic(@RequestBody RequestDto requestDto) {
        parserService.getTopSortedStatistic(requestDto);
    }

    @GetMapping(value = "/exportCSV", produces = "text/csv")
    public void downloadCsv(HttpServletResponse response) {
        parserService.writeCsv(response);
    }

}
