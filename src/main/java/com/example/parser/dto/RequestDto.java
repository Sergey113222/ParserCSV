package com.example.parser.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonAutoDetect
public class RequestDto {
    private String url;
    @JsonProperty("max_depth")
    private Integer maxDepth;
    @JsonProperty("max_page")
    private Integer maxPage;
    @JsonProperty("limit_number")
    private Integer limitNumber;
}
