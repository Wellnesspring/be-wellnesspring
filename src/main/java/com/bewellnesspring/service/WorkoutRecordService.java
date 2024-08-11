package com.bewellnesspring.service;

import com.bewellnesspring.dbapi.SportCategory;
import com.bewellnesspring.domain.WorkoutRecord;

import java.util.List;

public interface WorkoutRecordService {
    // 운동 기록 생성
    void createWorkoutRecord(WorkoutRecord workoutRecord);

    // ID로 운동 기록 조회
    WorkoutRecord getWorkoutRecord(Long id);

    // 모든 운동 기록 조회
    List<WorkoutRecord> getAllWorkoutRecords();

    // 운동 기록 업데이트
    void updateWorkoutRecord(WorkoutRecord workoutRecord);

    // ID로 운동 기록 삭제
    void deleteWorkoutRecord(Long id);

    // 모든 운동 종목 조회
    List<SportCategory> getAllSportCategories();
}
