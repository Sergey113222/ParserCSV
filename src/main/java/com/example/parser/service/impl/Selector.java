package com.example.parser.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class Selector {

    public static int depth = 2;
    public static Set<String> resultSet = new HashSet<>();

    public Set<String> getSetLinks(String url) {
        /**Quantity links on first(default) page*/
        Set<String> setOfLinks = new HashSet<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

            /** throw null url  */

            Elements elements = doc.select("a");
            for (Element e : elements) {
                if (!(resultSet.size() < 100)) {              //constraint 100 psc
                    return setOfLinks;
                }

                String href = e.attr("abs:href");
                if (href.startsWith("http"))
                    setOfLinks.add(href);
                resultSet.add(href);
                System.out.println("Depth link: " + depth + " name links " + href);
            }
        } catch (IOException e) {
            System.err.println(url + "This URL has exception: " + e.getMessage());
        }
        System.out.println("Quantity links on page: " + setOfLinks.size());
        return setOfLinks;
    }

    public Set<String> getListLinksWithDepth(Set<String> urls, int maxDepth, int maxPage) {
        /**Quantity links on second and next pages */
        Set<String> mainSet = new HashSet<>();
        if ((depth <= maxDepth) && (resultSet.size() <= maxPage)) {
            for (String url : urls) {

                /**get from every link sublinks and put them into result collection   */

                Set<String> nextPageUrls = this.getSetLinks(url);
                if (!(resultSet.size() < maxPage))
                    return mainSet;
                {
                    for (String nextPageUrl : nextPageUrls) {
                        mainSet.add(nextPageUrl);
                    }
                }
            }
            System.out.println("Depth link :" + depth + " quantity pages equals: " + mainSet.size());

            /*** Condition recursion and start recursion */
            depth++;
            getListLinksWithDepth(mainSet, maxDepth, maxPage);
        }
        return mainSet;
    }
}
