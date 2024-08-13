package com.bewellnesspring.dbapi;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SportCategoryMapper {

    //api에서 받아온 db데이터 sport_category테이블에 insert하기
    @Insert("insert into Sport_Category (id, sport_name, kcal) values (#{id}, #{sportName}, #{kcal})")
    void insertApitoSportCategory(SportCategory sportCategory);

    //sport_category테이블 비우기
    @Delete("truncate table sport_category")
    void truncateSportCategory();
}