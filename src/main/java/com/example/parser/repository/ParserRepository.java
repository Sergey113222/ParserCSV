package com.example.parser.repository;


import com.example.parser.model.ParserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParserRepository extends JpaRepository<ParserModel, Long> {
}
