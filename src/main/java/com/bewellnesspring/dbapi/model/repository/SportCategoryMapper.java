package com.bewellnesspring.dbapi.model.repository;

import com.bewellnesspring.dbapi.model.vo.FoodCategory;
import com.bewellnesspring.dbapi.model.vo.SportCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SportCategoryMapper {

    //api에서 받아온 db데이터 sport_category테이블에 insert하기
    void insertApitoSportCategory(SportCategory sportCategory);

    //sport_category테이블 비우기
    void truncateSportCategory();

    SportCategory findByName(String SportName);

}