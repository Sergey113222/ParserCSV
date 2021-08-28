package com.example.parser.service.impl;

import com.example.parser.dto.RequestDto;
import com.example.parser.model.ParserModel;
import com.example.parser.repository.ParserRepository;
import com.example.parser.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

    private final Selector selector;
    private final ParserRepository parserRepository;

    private Integer count;

    @Override
    public void getAllStatistic(RequestDto requestDto) {
        parserRepository.deleteAll();
        Set<String> setOfLinksFirstPage = selector.getSetLinks(requestDto.getUrl());
        selector.getListLinksWithDepth(setOfLinksFirstPage, requestDto.getMaxDepth(), requestDto.getMaxPage());
        this.saveAllStatistic(Selector.resultSet);
    }

    @Override
    public void getTopSortedStatistic(RequestDto requestDto) {
        parserRepository.deleteAll();
        Set<String> setOfLinksFirstPage = selector.getSetLinks(requestDto.getUrl());
        selector.getListLinksWithDepth(setOfLinksFirstPage, requestDto.getMaxDepth(), requestDto.getMaxPage());
        this.saveSortedLimitedList(Selector.resultSet, requestDto.getLimitNumber());
    }

    @Override
    public void writeCsv(HttpServletResponse response) {
        List<ParserModel> listStatistic = parserRepository.findAll();
        response.setContentType("text/csv");

        String fileName = "AllParserResult.csv";
        String headerKey = "Content - Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey, headerValue);
        ICsvBeanWriter csvBeanWriter = null;
        try {
            csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Url", "Tesla", "Musk", "Gigafactory", "Elon Musk", "Total"};
            String[] nameMapping = {"url", "tesla", "musk", "gigafactory", "elonMusk", "total"};
            csvBeanWriter.writeHeader(csvHeader);
            for (ParserModel p : listStatistic) {
                csvBeanWriter.write(p, nameMapping);
            }
            csvBeanWriter.close();
        } catch (IOException e) {
            System.err.println("Can not write: " + e.getMessage());
        }
    }

    public Integer matcher(String documentBody, String wordNeeded) {
        count = 0;
        Pattern pattern = Pattern.compile(wordNeeded);
        Matcher matcher = pattern.matcher(documentBody);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public void saveAllStatistic(Set<String> urls) {
        List<ParserModel> parserModelList = this.getStatisticList(urls);
            parserRepository.saveAll(parserModelList);
    }

    public void saveSortedLimitedList(Set<String> urls, int limitNumber) {
        List<ParserModel> parserModelList = this.getStatisticList(urls);
        parserModelList.sort(new SortedByTotal());
        List<ParserModel> sortedList = new ArrayList<>();
        for (int i = 0; i < limitNumber; i++) {
            sortedList.add(parserModelList.get(i));
        }
            parserRepository.saveAll(sortedList);
    }

    public List<ParserModel> getStatisticList(Set<String> urls) {
        List<ParserModel> parserModelList = new ArrayList<>();
        for (String url : urls) {
            ParserModel parserModel = new ParserModel();
            Document doc = null;
            String s = null;
            try {
                if (url.startsWith("http")) {
                    doc = Jsoup.connect(url).timeout(5000).get();
                    s = doc.body().text();
                    parserModel.setUrl(url);
                    int teslaCount = matcher(s, "Tesla");
                    parserModel.setTesla(teslaCount);
                    int muskCount = matcher(s, "Musk");
                    parserModel.setMusk(muskCount);
                    int gigafactoryCount = matcher(s, "Gigafactory");
                    parserModel.setGigafactory(gigafactoryCount);
                    int elonMuskCount = matcher(s, "Elon Musk");
                    parserModel.setElonMusk(elonMuskCount);
                    parserModel.setTotal(teslaCount + muskCount + gigafactoryCount + elonMuskCount);
                    parserModelList.add(parserModel);
                }
            } catch (IOException e) {
                System.err.println(url + "This URL has exception: " + e.getMessage());
            }
        }
        return parserModelList;
    }
}
