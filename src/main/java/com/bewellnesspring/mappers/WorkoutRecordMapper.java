package com.bewellnesspring.mappers;

import com.bewellnesspring.domain.WorkoutRecord;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WorkoutRecordMapper {
    // 운동 기록 삽입
    void insertWorkoutRecord(WorkoutRecord workoutRecord);

    // ID로 운동 기록 조회
    WorkoutRecord selectWorkoutRecordById(Long id);

    // 모든 운동 기록 조회
    List<WorkoutRecord> selectAllWorkoutRecords();

    // 운동 기록 업데이트
    void updateWorkoutRecord(WorkoutRecord workoutRecord);

    // ID로 운동 기록 삭제
    void deleteWorkoutRecord(Long id);
}
