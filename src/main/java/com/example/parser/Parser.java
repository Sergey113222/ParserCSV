package com.example.parser;


import com.example.parser.service.impl.Selector;
import com.example.parser.service.impl.Statistic;
import com.example.parser.service.impl.Writer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser {


    public static void main(String[] args) {

        Selector selector = new Selector();
        Statistic statistic = new Statistic();
        Writer writer = new Writer();

        /** Get links */
        Set<String> setOfLinksFirstPage = selector.getSetLinks("https://en.wikipedia.org/wiki/Elon_Musk");
        selector.getListLinksWithDepth(setOfLinksFirstPage, 8, 10000);
        System.out.println("Quantity all links: " + Selector.resultSet.size());

        /** Get statistic */
        Map<String, List<Integer>> statisticListMap = statistic.getStatistic(Selector.resultSet);
        statistic.printStatistic(statisticListMap);

        /** Get top 10 links and print them */
        statistic.getSortedMap(statisticListMap, 50);
        /** Write to CSV*/
        writer.saveRecordToCSV(statisticListMap, "src/main/resources/myFile.csv");
    }
}
