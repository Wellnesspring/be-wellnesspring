package com.bewellnesspring.sport.model.repository;

import com.bewellnesspring.sport.model.vo.SportItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SportItemMapper {

    //여러 운동 저장
    void savePlanItem(SportItem sportItem);
    void saveRecordItem(SportItem sportItem);

    //수정을 위해 해당 운동 모두 삭제
    void deletePlanItem(Long planId);
    void deleteRecordItem(Long recordId);

    List<SportItem> selectByPlanId(Long planId);



}
