package com.bewellnesspring.dbapi;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodCategoryMapper {

    //api에서 받아온 db데이터 Food_category테이블에 insert하기
    @Insert("insert into Food_Category (id, food_name, kcal, na, protein, fiber, fat, cholesterol, carbohydrate)" +
            " values (#{id}, #{food_name}, #{kcal}, #{na}, #{protein}, #{fiber}, #{fat}, #{cholesterol}, #{carbohydrate})")
    void insertApitoFoodCategory(FoodCategory foodCategory);

    //Food_category테이블 비우기
    @Delete("truncate table Food_category")
    void truncateFoodCategory();

}
