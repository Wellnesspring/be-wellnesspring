package com.bewellnesspring.alert.model.repository;

import com.bewellnesspring.alert.model.vo.Subscribe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscribeMapper {


    void saveSubscribe(Subscribe subscribe);
}
