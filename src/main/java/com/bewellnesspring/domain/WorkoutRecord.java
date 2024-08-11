package com.bewellnesspring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRecord {

    private Integer id; // auto-increment primary key
    private Integer sportRoutineId; // Sport_Routine 테이블의 id
    private Integer sportCategoryId ; // Sport_Category 테이블의 id
    private Integer setCount; // 운동 세트 수
    private Integer sportCount; // 운동 횟수
    private Integer volume; // 볼륨
                           //첨부파일
                          //칼로리 계산
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp routineStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp routineEnd;

    public void setCaloriesBurned(double caloriesBurned) {
    }

    public double getDuration() {
        return 0;
    }

    public long getSportCategoryId() {
        return 0;
    }
}
