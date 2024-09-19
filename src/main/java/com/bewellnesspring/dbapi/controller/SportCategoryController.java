package com.bewellnesspring.dbapi.controller;

import com.bewellnesspring.dbapi.model.repository.SportCategoryMapper;
import com.bewellnesspring.dbapi.model.vo.SportCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SportCategoryController {

    private final SportCategoryMapper sportCategoryMapper;


    @GetMapping("/sport/category")
    public List<SportCategory> findAll() {
        return sportCategoryMapper.findAll();
    }
}
