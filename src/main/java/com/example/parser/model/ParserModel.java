package com.example.parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "parser")
public class ParserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "url")
    private String url;
    @Column(name = "tesla")
    private Integer tesla;
    @Column(name = "musk")
    private Integer musk;
    @Column(name = "gigafactory")
    private Integer gigafactory;
    @Column(name = "elon_musk")
    private Integer elonMusk;
    @Column(name = "total")
    private Integer total;
}
