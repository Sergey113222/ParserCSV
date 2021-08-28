package com.example.parser.service.impl;

import com.example.parser.model.ParserModel;
import java.util.Comparator;

public class SortedByTotal implements Comparator<ParserModel> {
    @Override
    public int compare(ParserModel o1, ParserModel o2) {
        return o2.getTotal().compareTo(o1.getTotal());
    }
}
