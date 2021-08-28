package com.example.parser.service;

import com.example.parser.dto.RequestDto;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;

@Service
public interface ParserService {
    void getAllStatistic(RequestDto requestDto);

    void getTopSortedStatistic(RequestDto requestDto);

    void writeCsv(HttpServletResponse response);
}
