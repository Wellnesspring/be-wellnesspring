package com.bewellnesspring.dbapi.model.repository;

import org.apache.ibatis.annotations.Mapper;

import com.bewellnesspring.dbapi.model.vo.FoodCategory;

@Mapper
public interface FoodCategoryMapper {

    //api에서 받아온 db데이터 Food_category테이블에 insert하기
    void insertApitoFoodCategory(FoodCategory foodCategory);

    //Food_category테이블 비우기
    void truncateFoodCategory();

//<-어노테이션 내용 mapper.xml로 이동->
    //    @Insert("insert into Food_Category (id, food_name, kcal, na, protein, fiber, fat, cholesterol, carbohydrate)" +
    //    " values (#{id}, #{food_name}, #{kcal}, #{na}, #{protein}, #{fiber}, #{fat}, #{cholesterol}, #{carbohydrate})")
    //    @Delete("truncate table Food_category")



}
