package com.example.parser.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Statistic {
    private final String HTTP = "http";
    private final String TESLA = "Tesla";
    private final String MUSK = "Musk";
    private final String GIGAFACTORY = "Gigafactory";
    private final String ELON_MUSK = "Elon Musk";

    public Integer matcher(String documentBody, String wordNeeded) {
        int count = 0;
        Pattern pattern = Pattern.compile(wordNeeded);
        Matcher matcher = pattern.matcher(documentBody);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public Map<String, List<Integer>> getStatistic(Set<String> urls) {
        Map<String, List<Integer>> statisticMap = new HashMap<>();
        for (String url : urls) {

            Document doc = null;
            String s = null;
            try {
                if (url.startsWith(HTTP)) {
                    doc = Jsoup.connect(url).timeout(5000).get();
                    s = doc.body().text();

                    List<Integer> countList = new ArrayList<>();
                    int countTesla = matcher(s, TESLA);
                    countList.add(countTesla);
                    int countMusk = matcher(s, MUSK);
                    countList.add(countMusk);
                    int countGigafactory = matcher(s, GIGAFACTORY);
                    countList.add(countGigafactory);
                    int countElonMusk = matcher(s, ELON_MUSK);
                    countList.add(countElonMusk);
                    countList.add(countTesla + countMusk + countGigafactory + countElonMusk);

                    statisticMap.put(url, countList);
                }
            } catch (IOException e) {
                System.err.println(url + "This URL has exception: " + e.getMessage());
            }
        }
        return statisticMap;
    }

    public void printStatistic(Map<String, List<Integer>> statisticListMap) {
        for (Map.Entry<String, List<Integer>> entry : statisticListMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void getSortedMap(Map<String, List<Integer>> unsortedMap, int limitNumber) {
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> o2.get(4) - o1.get(4)))
                .limit(limitNumber)
                .forEach(System.out::println);
    }
}
